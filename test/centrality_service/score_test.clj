(ns centrality-service.score-test
  (:require [clojure.test :refer :all]
            [centrality-service.score :refer :all]
            [centrality-service.graph :refer :all]
            [centrality-service.bfs-shortest-path :refer :all]))

(deftest score
  (let [edges ['(6 4) '(4 3) '(4 5) '(3 2) '(5 2) '(5 1) '(2 1) '(1 0)]
        graph (build-graph {} edges)
        distances (all-pairs-shortest-path graph)]

  (testing "farness"
    (let [vertice 0]
      (is (= 15 (farness (distances vertice) vertice)) "Doest not calculate de farness correctly")))

  (testing "closeness"
    (let [vertice 0]
      (is (= 1/15 (closeness (distances vertice) vertice)) "Doest not calculate de closeness correctly")))

  (testing "calculate-closeness"
    (let [expected-ranking [[5 1/9] [1 1/10] [2 1/10] [4 1/10] [3 1/11] [0 1/15] [6 1/15]]
          actual-ranking (rank (calculate-closeness distances))]
      (is (= 0 (compare expected-ranking actual-ranking))
        (str "Does not calulate the rank correctly" \newline
             "Actual: " actual-ranking \newline "Expected: " expected-ranking))))

  (testing "fraudulent"
    (let [score [[5 1/9] [1 1/10] [2 1/10] [4 1/10] [3 1/11] [0 1/15] [6 1/15]]
          expected-score [[4 0.08750000000000001]
                          [5 0.08333333333333333] 
                          [3 0.07954545454545454]
                          [2 0.07500000000000001] 
                          [6 0.0625]
                          [1 0.05]
                          [0 0.0]]
          actual-score (rank (fraudulent distances score 0))]
      (is (= 0 (compare expected-score actual-score))
        (str "Does not calulate the rank correctly" \newline
             "Actual: " actual-score \newline "Expected: " expected-score))))))
    


