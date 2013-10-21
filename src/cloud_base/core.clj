(ns cloud-base.core
  (:require [org.httpkit.server :refer :all]
            [cloud-base.route :refer [route]]))  

(defn -main [& args]
  (run-server route {:port 8080}))


