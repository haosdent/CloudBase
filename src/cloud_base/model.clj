(ns cloud-base.model)

(defn get [table row version]
  (. DB get row version)) 

(defn put [table obj]
  (. DB put table obj)) 

(defn create [table]
  (. DB create table))
