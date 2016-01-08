(ns centrality-service.graph-test
  (:require [clojure.test :refer :all]
            [centrality-service.graph :refer :all]))

(deftest graph-construction
  (testing "has-d-egdge?"
    (let [graph {0 #{1} 1 #{2, 3}}]
      (is (true? (has-d-edge? graph 0 1)) "Does not assert the presence of the 0->1 edge properly")))

  (testing "has-egdge?"
    (let [graph {0 #{1} 1 #{0, 2}}]
      (is (true? (has-edge? graph 0 1)) "Does not assert the presence of the 0-1 edge properly")
      (is (true? (has-edge? graph 1 0)) "Does not assert the presence of the 0-1 edge properly")))

  (testing "add-d-edge"
    (let [graph (add-d-edge {} '(0 1))]
      (is (true? (has-d-edge? graph 0 1)) "Does not add the 0->1 add to the graph")))

  (testing "add-edge"
    (let [graph (add-edge {} '(0 1))]
      (is (true? (has-edge? graph 0 1)) "Does not add the 0-1 add to the graph")
      (is (true? (has-edge? graph 1 0)) "Does not add the 0-1 add to the graph")))

  (def edges ['(6 4) '(4 3) '(4 5) '(3 2) '(5 2) '(5 1) '(2 1) '(1 0)])

  (testing "build-graph"
    (let [graph (build-graph {} edges)]
      (is (true? (every? (fn [[v1 v2]] (has-edge? graph v1 v2)) edges)) 
        (str "Does not add all the edges to the graph:" graph))))

  (testing "shortest-path"
    (let [expected-shotest-path-to-0 [0 1 2 3 3 2 4]
          graph (build-graph {} edges)
          shortest-path-to-0 (shortest-path graph 0)]
      (is (= 0 (compare expected-shotest-path-to-0 shortest-path-to-0))
        (str  "Does not calculate the shortes-path correctly:" shortest-path-to-0 "\n"
              "Expected: " expected-shotest-path-to-0)))))




