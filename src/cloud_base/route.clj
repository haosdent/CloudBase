(ns cloud-base.route
  (:use [compojure.route :only [files not-found]]
        [compojure.handler :only [site]]
        [compojure.response :only [Renderable]]
        [compojure.core :only [defroutes GET POST DELETE PUT ANY context]]
        [ring.util.response :only (response content-type header)]
        cheshire.core
        org.httpkit.server)
  (:require [cloud-base.model :as model])
  (:import [java.util HashMap]))  

(extend-protocol Renderable
  Boolean
  (render [_ _]
    (-> (response nil)
        (header "Access-Control-Allow-Origin" "*")))  
  HashMap
  (render [map _]
    (-> (response (generate-string map))
        (content-type "application/json; charset=utf-8")
        (header "Access-Control-Allow-Origin" "*")))) 

(defroutes all-routes
  (files "/" {:root "sdk/web"})
  
  (GET  "/:table/:row/:version" [] 
        #(model/get 
          (-> % :params :table)
          (-> % :params :row)
          (read-string (-> % :params :version)))) 

  (POST "/:table/:row/:version" []
        #(model/put
          (-> % :params :table)
          (-> % :params :row)
          (read-string (-> % :params :version))
          (-> % :params :data)))  

  (POST "/:table"               []
        #(model/create 
          (-> % :params :table)))

  (not-found "404"))     


