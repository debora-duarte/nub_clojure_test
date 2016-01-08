(ns centrality-service.bfs-shortest-path-test
  (:require [clojure.test :refer :all]
            [centrality-service.bfs-shortest-path :refer :all]
            [centrality-service.graph :refer :all]))

(deftest bfs-shortest-path
  (def edges ['(6 4) '(4 3) '(4 5) '(3 2) '(5 2) '(5 1) '(2 1) '(1 0)])

  (testing "shortest-path"
    (let [expected-shotest-path-to-0 [0 1 2 3 3 2 4]
          graph (build-graph {} edges)
          shortest-path-to-0 (shortest-path graph 0)]
      (is (= 0 (compare expected-shotest-path-to-0 shortest-path-to-0))
        (str  "Does not calculate the shortes-path correctly:" shortest-path-to-0 "\n"
              "Expected: " expected-shotest-path-to-0)))))




