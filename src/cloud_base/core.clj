(ns cloud-base.core
  (:use org.httpkit.server
        cloud-base.route))  

(defn -main [& args]
  (run-server route {:port 8080}))


