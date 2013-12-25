(ns cloud-hbase.core
  (:require [org.httpkit.server :refer :all]
            [cloud-hbase.route :refer [route]]))  

(defn -main [& args]
  (run-server route {:port 8080}))


