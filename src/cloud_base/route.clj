(ns cloud-base.route
  (:use [compojure.route :only [files not-found]]
        [compojure.handler :only [site]]
        [compojure.core :only [defroutes GET POST DELETE PUT ANY context]]
        org.httpkit.server)
  (:require [cloud-base.model :as model])) 

(defroutes all-routes
  (GET  "/:table/:row/:version" [] 
        #(model/get 
          (-> % :params :table)
          (-> % :params :row)
          (-> % :params :version))) 

  (PUT  "/:table/:row/:version" []
        #(model/put
          (-> % :params :table)
          (-> % :params :row)
          (-> % :params :version)
          (-> % :params :data)))  

  (POST "/:table"               []
        #(model/create 
          (-> % :params :table))))   


