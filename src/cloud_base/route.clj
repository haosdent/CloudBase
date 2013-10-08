(ns cloud-base.route
  (:use cheshire.core
        org.httpkit.server)
  (:require [cloud-base.controller :as controller]))   

(defn route [request]
  (with-channel request channel
    (on-close channel (fn [status] (println "Channel close: " status)))
    (on-receive channel 
                (fn [msg]
                  (let [req (parse-string msg true)
                        act (req :act)]
                    (send! channel 
                           (cond
                            (= act "create") (controller/create req)
                            (= act "get")    (controller/get    req)
                            (= act "put")    (controller/put    req))))))))
