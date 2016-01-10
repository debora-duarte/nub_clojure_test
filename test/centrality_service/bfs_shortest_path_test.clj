(ns centrality-service.bfs-shortest-path-test
  (:require [clojure.test :refer :all]
            [centrality-service.bfs-shortest-path :refer :all]
            [centrality-service.graph :refer :all]))

(deftest bfs-shortest-path
  (def edges ['(6 4) '(4 3) '(4 5) '(3 2) '(5 2) '(5 1) '(2 1) '(1 0)])
  (def graph (build-graph {} edges))

  (testing "single-source-shortest-path"
    (let [expected-shotest-path-to-0 [0 1 2 3 3 2 4]
          shortest-path-to-0 (single-source-shortest-path graph 0)]
      (is (= expected-shotest-path-to-0 shortest-path-to-0))))

  (testing "all-pairs-shortest-path"
    (let [expected-all-pairs-shortest-path
      [[0 1 2 3 3 2 4]
       [1 0 1 2 2 1 3]
       [2 1 0 1 2 1 3]
       [3 2 1 0 1 2 2]
       [3 2 2 1 0 1 1]
       [2 1 1 2 1 0 2]
       [4 3 3 2 1 2 0]]
      all-pairs-shortest-path (all-pairs-shortest-path graph)]
      
      (is (= expected-all-pairs-shortest-path all-pairs-shortest-path)
        (str  "Does not calculate the all-pairs-shortes-path correctly: " \newline 
              "Actual: " all-pairs-shortest-path \newline
              "Expected: " expected-all-pairs-shortest-path)))))




