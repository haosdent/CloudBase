(ns cloud-base.controller
  (:refer-clojure :exclude [get])
  (:use cheshire.core)
  (:import [cloud_base.model DB])) 

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
  (DB/create (req :app)))

(defcontrol get [req]
  (merge {:cmd "update"}
         (DB/get (req :app)
                 (req :id)
                 (req :version))))

(defcontrol put [req]
  (DB/put (req :app)
            (req :id)
            (req :version)
            (req :data)))
