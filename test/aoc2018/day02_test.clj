(ns aoc2018.day02-test
  (:require [aoc2018.day02 :as x]
            [clojure.test :refer :all]))

(deftest test-part-one []
  (testing "twos and threes"
    (is (= (x/has-twos-and-threes "abcdef")
           [0 0]))

    (is (= (x/has-twos-and-threes "bababc")
           [1 1]))

    (is (= (x/has-twos-and-threes "abbcde")
           [1 0]))

    (is (= (x/has-twos-and-threes "abcccd")
           [0 1]))

    (is (= (x/has-twos-and-threes "aabcdd")
           [1 0]))

    (is (= (x/has-twos-and-threes "abcdee")
           [1 0]))
    
    (is (= (x/has-twos-and-threes "ababab")
           [0 1])))

  (testing "checksum"
    (is (= (x/checksum ["abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"])
           12))))




(def test-input
  ["abcde"
   "fghij"
   "klmno"
   "pqrst"
   "fguij"
   "axcye"
   "wvxyz"])

(deftest test-part-two []
  (testing "differ by one char"
    (is (false? (x/differ-by-only-one-char? "abcde" "axcye")))

    (is (true? (x/differ-by-only-one-char? "fghij" "fguij"))))

  (testing "find-pair"
    (is (= (x/find-pair "abcd" ["qqqq" "dcba" "abab" "abcP"])
           #{"abcd" "abcP"})))


  (testing "strip diffing char"
    (is (= (x/strip-diffing-char "fghij" "fguij")
           "fgij"))
    (is (= (x/strip-diffing-char "ooXoo" "ooIoo")
           "oooo")))

  (testing "get common letters"
    (is (= (x/get-common-letters test-input)
           "fgij"))))



