(ns cloud-base.model
  (:import java.io.IOException
           java.net.UnknownHostException
           [java.util HashMap Map]
           org.apache.hadoop.conf.Configuration
           [org.apache.hadoop.hbase HBaseConfiguration HColumnDescriptor HTableDescriptor MasterNotRunningException ZooKeeperConnectionException]
           [org.apache.hadoop.hbase.client Get HBaseAdmin HTableInterface HTablePool Put Result]
           [org.apache.hadoop.hbase.util PoolMap$PoolType Bytes])) 

(def conf 
  (doto (. HBaseConfiguration create)
    (.setInt "hbase.zookeeper.property.clientPort" 40060)
    (.set "hbase.zookeeper.quorum" "10.232.98.94,10.232.98.72,10.232.98.40")
    (.set "zookeeper.znode.parent" "/hbase-cdh4")))

(def admin
  (HBaseAdmin. conf))

(def put-pool 
  (HTablePool. conf 50 PoolMap$PoolType/ThreadLocal))
(def get-pool 
  (HTablePool. conf 50 PoolMap$PoolType/ThreadLocal))

(defn to-bytes [val]
  (Bytes/toBytes val)) 

(def family "cf")
(def family-bytes (to-bytes family))
(def key-data "data")
(def key-data-bytes (to-bytes key-data))

(defn create-act [table] 
  (when-not (try
              (.tableExists admin table)
              (catch Exception e "Exception")) 
    (.createTable admin 
                  (doto (HTableDescriptor. table)
                    (.addFamily 
                     (HColumnDescriptor. family)))))) 

(defn put-act [table row version data]
  (let [htable (.getTable put-pool table)]
    (.put htable
          (.add (Put. (to-bytes row))
                family-bytes
                key-data-bytes
                version
                (to-bytes data)))
    (.close htable)))

(defn get-act [table row version]
  (let [htable (.getTable get-pool table)
        result (.get htable
                     (doto (Get. (to-bytes row))
                       (.setTimeRange (inc version) Long/MAX_VALUE)
                       (.setMaxVersions 1)))
        data-bytes (.getValue result
                              family-bytes key-data-bytes)]
    (.close htable)
    (if data-bytes
      {:version (.getTimestamp 
                 (aget (.raw result) 0))
       :data (Bytes/toString data-bytes)}
      (if (= version 0)
        (put-act
         table row (System/currentTimeMillis) "{}"))))) 
