(ns cloud-base.route
  (:refer-clojure :exclude [get])
  (:require [org.httpkit.server :refer :all]
            [cheshire.core :refer [parse-string]]
            [cloud-base.controller :refer [create get put]]))    

(defn route [request]
  (with-channel request channel
    (on-close channel (fn [status] (println "Channel close: " status)))
    (on-receive channel 
                (fn [msg]
                  (let [req (parse-string msg true)
                        act (req :act)]
                    (send! channel 
                           (cond
                            (= act "create") (create req)
                            (= act "get")    (get    req)
                            (= act "put")    (put    req))))))))
