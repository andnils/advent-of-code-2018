(ns aoc2018.day05
  (:require [clojure.string :as str]))


(defn same-type? [c1 c2]
  (= (str/lower-case c1)
     (str/lower-case c2)))


(defn react? [c1 c2]
  (and (not= c1 c2)
       (same-type? c1 c2)))


(defn single-pass [input-seq]
  (loop [output (transient [])
         input input-seq]
    (let [[x y] (take 2 input)]
      (if x
        (if y
          (if (react? x y)
            (recur output (drop 2 input))
            (recur (conj! output x) (rest input)))
          (persistent! (conj! output x)))
        (persistent! output)))))


(defn full-pass [input-seq]
  (loop [input input-seq]
    (let [new-result (single-pass input)]
      (if (= (count input) (count new-result))
        (apply str new-result)
        (recur new-result)))))


(defn count-units [data]
  (-> (full-pass data)
      (str/trim-newline)
      (count)))


(defn solve-part-one []
  (count-units (slurp "resources/input05.txt")))


(defn solve-part-two []
  (apply min
         (let [data (slurp "resources/input05.txt")]
           (for [exclude "abcdefghijklmnopqrstuvwxyz"]
             (count-units (str/replace data (re-pattern (str "(?i)" exclude)) ""))))))


(comment
  ;; --- Part One ---
  ;; 5 secs!
  (solve-part-one)

  ;; --- Part Two ---
  ;; 2 minutes!!!
  (solve-part-two)

  )
