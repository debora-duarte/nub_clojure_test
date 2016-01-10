(ns centrality-service.util-test
  (:require [clojure.test :refer :all]
            [centrality-service.util :refer :all]))

(deftest util
  (testing "load-edges"
    (let [edges (load-edges "test.txt")]
      (is (= (first edges) '(1 2)))
      (is (= (last edges) '(5 6)))
      (is (= (count edges) 3)))))
