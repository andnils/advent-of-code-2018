(ns aoc2018.day04-test
  (:require [aoc2018.day04 :as x]
            [clojure.test :refer [deftest is]]))


(def test-lines
  ["[1518-11-01 00:00] Guard #10 begins shift"
   "[1518-11-01 00:05] falls asleep"
   "[1518-11-01 00:25] wakes up"
   "[1518-11-01 00:30] falls asleep"
   "[1518-11-01 00:55] wakes up"
   "[1518-11-01 23:58] Guard #99 begins shift"
   "[1518-11-02 00:40] falls asleep"
   "[1518-11-02 00:50] wakes up"
   "[1518-11-03 00:05] Guard #10 begins shift"
   "[1518-11-03 00:24] falls asleep"
   "[1518-11-03 00:29] wakes up"
   "[1518-11-04 00:02] Guard #99 begins shift"
   "[1518-11-04 00:36] falls asleep"
   "[1518-11-04 00:46] wakes up"
   "[1518-11-05 00:03] Guard #99 begins shift"
   "[1518-11-05 00:45] falls asleep"
   "[1518-11-05 00:55] wakes up"])



(deftest minutes-between
  (is (= (x/get-minutes-between 10 11)
         [10]))
  
  (is (= (x/get-minutes-between (x/get-minute "xyz22:58abc")
                                (x/get-minute "   23:02"))
         [58 59 0 1])))


(deftest solve-part-one
  (is (= (* 10 24)
         (x/strategy-1 test-lines)))
  
  (is (= (* 42 3491))
      (x/strategy-1 (x/lines-from-file "resources/input04.txt"))))


(deftest solve-part-two
  (is (= (* 99 45)
         (x/strategy-2 test-lines)))
  
  (is (= (* 1327 24)
         (x/strategy-2 (x/lines-from-file "resources/input04.txt")))))
