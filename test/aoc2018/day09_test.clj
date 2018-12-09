(ns aoc2018.day09-test
  (:require [aoc2018.day09 :as x]
            [clojure.test :refer :all]))


(deftest honk
  (is (= 8317
         (x/play-game 10 1618)))
  
  (is (= 146373
         (x/play-game 13 7999)))
  
  (is (= 2764
         (x/play-game 17 1104)))

  (is (= 54718
         (x/play-game 21 6111)))

  (is (= 37305
         (x/play-game 30 5807)))
  )
