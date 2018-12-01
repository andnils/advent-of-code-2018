(ns aoc2018.day01
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))


(defn apply-frequency-change [frequency s]
  (let [change (edn/read-string s)]
    (+ frequency change)))

(defn sum-all [path]
  (with-open [file (io/reader path)]
    (reduce apply-frequency-change 0 (line-seq file))))




(defn reducer-fn [{:keys [frequency seen]} s]
  (let [change (edn/read-string s)
        new-frequency (+ change frequency)]
    (if (contains? seen new-frequency)
      (reduced new-frequency)
      {:frequency new-frequency
       :seen (conj seen new-frequency)})))

(defn find-first-duplicate [path]
  (with-open [rdr (io/reader path)]
    (let [lines (cycle (line-seq rdr))
          init-val {:frequency 0 :seen #{}}]
      (reduce reducer-fn init-val lines))))


;;;;;;;
(comment
  
  ;; --- Part One ---
  (sum-all "resources/input01.txt")
  ;; --- Part Two ---
  (find-first-duplicate "resources/input01.txt")
 
  )
