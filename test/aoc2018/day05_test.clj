(ns aoc2018.day05-test
  (:require [aoc2018.day05 :as x]
            [clojure.test :refer :all]))


(deftest will-react?
  (is (false? (x/react? \a \a)))
  (is (false? (x/react? \A \A)))
  (is (false? (x/react? \a \b)))
  (is (false? (x/react? \A \B)))
  (is (true? (x/react? \a \A)))
  (is (true? (x/react? \A \a))))



(deftest react-data
  (is (= (x/react! "dabAcCaCBAcCcaDA")
         "dabCBAcaDA")))

(deftest part-one
  (is (= (x/solve-part-one "dabAcCaCBAcCcaDA")
         10)))

(deftest part-two
  (is (= (x/solve-part-two "dabAcCaCBAcCcaDA")
         4)))
