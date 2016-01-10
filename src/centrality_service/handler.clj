(ns centrality-service.handler
  (:use compojure.core
        ring.middleware.json)
  (:require [compojure.handler :as handler]
            [ring.util.response :refer [response]]
            [compojure.route :as route]
            [centrality-service.core]))

(defn- str-to [num]
  (apply str (interpose ", " (range 1 (inc num)))))

(defroutes app-routes
  (GET "/count-up/:to" [to] (str-to (Integer. to)))
  (GET "/ranking" [] (response (centrality-service.core/rank-the-clients))) 
  (route/not-found (response {:message "Page not found"})))

(def app
  (-> app-routes
    wrap-json-response
    wrap-json-body))



