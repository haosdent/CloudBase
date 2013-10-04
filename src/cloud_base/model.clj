(ns cloud-base.model
  (:require [cheshire.core :refer :all])  
  (:refer-clojure :exclude [get])
  (:import [cloud_base DB]))

(defmacro to-json [map]
  `{:status 200
    :headers {"Content-Type" "application/json; charset=utf-8"}
    :body (generate-string ~map)}) 

(defn get [table row version]
  (to-json (DB/get table row version)))  

(defn put [table row version data]
  (DB/put table row version data)) 

(defn create [table]
  (DB/create table))


