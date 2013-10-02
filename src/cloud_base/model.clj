(ns cloud-base.model
  (:import [cloud-base.model DB])) 

(defn get [table row version]
  (DB/get row version)) 

(defn put [table row version data]
  (DB/put table data)) 

(defn create [table]
  (DB/create table))
