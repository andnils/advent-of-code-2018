(ns aoc2018.day14-test
  (:require [aoc2018.day14 :refer [solve-part-1]]
            [clojure.test :refer [deftest is]]))

(deftest test-part-1
  (is (= "5158916779"
         (solve-part-1 9)))
  
  (is (= "0124515891"
         (solve-part-1 5)))
  
  (is (= "9251071085"
         (solve-part-1 18)))
  
  (is (= "5941429882"
         (solve-part-1 2018))))
