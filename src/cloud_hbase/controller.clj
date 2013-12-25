(ns cloud-hbase.controller
  (:refer-clojure :exclude [get])
  (:require [cheshire.core :refer [generate-string]]
            [cloud-hbase.model :refer [create-act get-act put-act]]))   

(defmacro defcontrol
  [name params & forms]
  `(defn ~name ~params
     (let [req# (first ~params)]
       (generate-string
        (merge
         {:rid (req# :rid) :gid (req# :gid) :act (req# :act) :cmd "success"}
         (try
           (do ~@forms)
           (catch Exception e#
             {:cmd "error" :data (.getMessage e#)}))))))) 

(defcontrol create [req]
  (create-act (req :app)))

(defcontrol get [req]
  (merge {:cmd "update"}
         (get-act (req :app)
                  (req :id)
                  (req :version))))

(defcontrol put [req]
  (put-act (req :app)
           (req :id)
           (req :version)
           (req :data)))
