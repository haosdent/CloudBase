(ns cloud-base.route
  (:use [compojure.route :only [files not-found]]
        [compojure.handler :only [site]]
        [compojure.core :only [defroutes GET POST DELETE ANY context]]
        org.httpkit.server)
  (:require [cloud-base.model :as model])) 

(defroutes all-routes
  (GET  "/:table/:row/:version" [] model/get)
  (PUT  "/:table"               [])
  (POST "/:table"               [] model/create)) 


