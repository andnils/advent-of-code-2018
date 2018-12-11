(ns aoc2018.day11-test
  (:require [aoc2018.day11 :as x]
            [clojure.test :refer :all])
  (:import [aoc2018.day11 Grid]))


(deftest fuel-cell
  (is (= 4
         (.getPowerLevel (Grid. 8) 3 5)))
  
  (is (= -5
         (.getPowerLevel (Grid. 57) 122 79)))
  (is (= 0
         (.getPowerLevel (Grid. 39) 217 196)))
  (is (= 4
         (.getPowerLevel (Grid. 71) 101 153))))

(deftest grids
  (is (= 29
         (x/grid-power (Grid. 18) 3 33 45))))
