(ns aoc2018.day02
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn get-inputs-from-file [path]
  (-> (slurp path)
      (str/split-lines)))


(defn has-twos-and-threes [box-id]
  (let [char-counts (frequencies box-id)
        any-char-with-count-2  (some (fn [[char count]] (= count 2)) char-counts)
        any-char-with-count-3  (some (fn [[char count]] (= count 3)) char-counts)]
    [ (if any-char-with-count-2 1 0)
      (if any-char-with-count-3 1 0)]))

(defn count-twos-and-threes [[a1 b1] [a2 b2]]
  [(+ a1 a2) (+ b1 b2)])

(defn checksum [lines]
  (->> lines
       (map has-twos-and-threes)
       (reduce count-twos-and-threes)
       (apply *)))




(defn differ-by-only-one-char? [s1 s2]
  (let [equal-chars (frequencies (map = s1 s2))
        num-of-differing-chars (get equal-chars false)]
    ;; if all chars except one differs, return true
    (= 1 num-of-differing-chars)))


(defn- if-same [[a b]]
  (when (= a b) a))


(defn strip-diffing-char [s1 s2]
  (->> (map vector s1 s2)
       (keep if-same)
       (apply str)))


(defn find-pair [inputs]
  (into #{}
        (flatten
         (for [input1 inputs
               input2 inputs
               :when (differ-by-only-one-char? input1 input2)]
           [input1 input2]))))


(defn get-common-letters [inputs]
  (apply strip-diffing-char (find-pair inputs)))




(comment
  ;; --- Part One ---
  (checksum (get-inputs-from-file "resources/input02.txt"))

  ;; --- Part Two ---
  (get-common-letters (get-inputs-from-file "resources/input02.txt"))
  
  )

