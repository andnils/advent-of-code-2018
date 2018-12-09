(ns aoc2018.day08-test
  (:require [aoc2018.day08 :as x]
            [clojure.test :refer :all]))


(deftest test1
  (is (= {:md [99]
          :children []}
         (first (x/parse-root [0 1 99]))))

)


