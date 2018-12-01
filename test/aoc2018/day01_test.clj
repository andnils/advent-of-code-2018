(ns aoc2018.day01-test
  (:require [aoc2018.day01 :as x]
            [clojure.test :refer :all]))

(def test-file-path "resources/input01-test.txt")
(deftest test-sum-all
  (is (= (x/sum-all test-file-path)
         3)))

(deftest test-find-first-duplicate
  (is (= (x/find-first-duplicate test-file-path)
         2)))
