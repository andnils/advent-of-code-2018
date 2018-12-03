(ns aoc2018.day03-test
  (:require [aoc2018.day03 :as x]
            [clojure.test :refer [deftest testing is]]))


(deftest test-coords-from-claim
  (is (= (x/coords-from-claim {:id 1 :x 1 :y 3 :width 4 :height 4})
         [[1 3] [2 3] [3 3] [4 3]
          [1 4] [2 4] [3 4] [4 4]
          [1 5] [2 5] [3 5] [4 5]
          [1 6] [2 6] [3 6] [4 6]])))


;; Example state:
;; #1 @ 1,1: 2x2
;; #2 @ 2,2: 2x2
;; #3 @ 3,1: 1x1
;;
;;    1 2 3
;;   ------
;; 1| 1 1 3
;; 2| 1 X 2
;; 3|   2 2
;;
;;
(def state
  {[1 1] #{1}  [2 1] #{1}    [3 1] #{3}
   [1 2] #{1}  [2 2] #{1 2}  [3 2] #{2}
   [1 3] #{}   [2 3] #{2}    [3 3] #{2}})


(deftest test-no-overlap
  (is (false? (x/no-overlap? state 1)))
  (is (false? (x/no-overlap? state 2)))
  (is (true? (x/no-overlap? state 3))))


;; There is only one coordinate with overlaps
;; (marked with X above).
(deftest test-count-overlapping
  (is (= (x/count-overlapping state)
         1)))

;; Claim with id #3 is the only claim without
;; any overlaps.
(deftest test-find-non-overlapping-id
  (is (= (x/find-non-overlapping-id state)
         3)))
