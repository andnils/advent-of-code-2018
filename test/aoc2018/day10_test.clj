(ns aoc2018.day10-test
  (:require [aoc2018.day10 :as x]
            [clojure.test :refer :all]))


(deftest test-point
  (is (= (x/make-point 12 -3 0 -1)
         (x/parse-line "position=< 12,  -3> velocity=< 0, -1>\n"))))

(deftest test-move-all
  (is (= [(x/make-point 1 1 1 1)
          (x/make-point -1 -1 -1 -1)]
         (x/move-all [(x/make-point 0 0 1 1)
                      (x/make-point 0 0 -1 -1)]))))
