(ns centrality-service.handler
  (:use compojure.core
        ring.middleware.json)
  (:require [compojure.handler :as handler]
            [ring.util.response :refer [response]]
            [compojure.route :as route]
            [centrality-service.core]))

(defn- str-to [num]
  (apply str (interpose ", " (range 1 (inc num)))))

(defn add-edge [v1 v2]
  (let [edge (list (read-string v1) (read-string v2))]
    (centrality-service.core/add-another-edge edge)))

(defroutes app-routes
  (GET "/count-up/:to" [to] (str-to (Integer. to)))
  (GET "/ranking" [] (response (centrality-service.core/rank-the-clients)))
  (PUT "/add-edge/:v1/to/:v2" [v1 v2] (response (add-edge v1 v2)))
  (route/not-found (response {:message "Page not found"})))

(def app
  (-> app-routes
    wrap-json-response
    wrap-json-body))



