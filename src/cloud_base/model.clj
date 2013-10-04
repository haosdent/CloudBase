(ns cloud-base.model
  (:refer-clojure :exclude [get])
  (:import [cloud_base DB])) 

(defn get [table row version]
  (DB/get table row version))  

(defn put [table row version data]
  (DB/put table row version data)) 

(defn create [table]
  (DB/create table))


