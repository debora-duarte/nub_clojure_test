(ns centrality-service.core-test
  (:require [clojure.test :refer :all]
            [centrality-service.core :refer :all]))

;;(deftest a-test
;;  (testing "FIXME, I fail."
;;    (is (= 0 1))))

0 1 1
0 2 2
0 3 3
0 4 3
0 5 2
0 6 4
1 2 1
1 3 2
1 4 2
1 5 1
1 6 3
2 3 1
2 4 2
2 5 1
2 6 3
3 4 1
3 5 2
3 6 2
4 5 1
4 6 1
5 6 2

[0 1 2 3 3 2 4]



