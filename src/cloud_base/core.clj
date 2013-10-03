(ns cloud-base.core
  (:use org.httpkit.server
        cloud-base.route
        [compojure.handler :only [site]])) 

(defn -main [& args]
  (run-server (site #'all-routes) {:port 8080}))



