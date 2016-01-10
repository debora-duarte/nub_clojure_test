(ns centrality-service.handler-test
  (:use clojure.test
        ring.mock.request  
        centrality-service.handler
        centrality-service.core))

(defn initialize-data [my-test] 
  (initialize)
  (my-test))

(use-fixtures :once initialize-data)

(deftest test-app
  (testing "ranking endpoint"
    (let [response (app (request :get "/ranking"))]
      (is (= (:status response) 200))
      (is (= (get-in response [:headers "Content-Type"]) "application/json; charset=utf-8"))))

  (testing "add edge endpoint"
    (let [response (app (request :put "/add-edge/0/to/1"))]
      (is (= (:status response) 200))))

  ; (testing "mark client as fraudulent endpoint"
  ;   (let [response (app (request :put "/mark-as-fraudulent/44"))]
  ;     (is (= (:status response) 200))))

  (testing "not-found route"
    (let [response (app (request :get "/something-inexistent"))]
      (is (= (:status response) 404)))))