(ns aoc2018.day08
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))



(defn read-from-file [path]
  (->> (str/split (slurp path) #" ")
       (map #(edn/read-string %))))


(declare parse)

(defn parse-root [xs]
  (let [num-children (first xs)
        num-metadata (second xs)
        tail (drop 2 xs)
        [children tail] (nth (iterate parse [[] tail]) num-children)
        [md tail] (split-at num-metadata tail)]
    [{:md md :children children} tail]))


(defn parse [xs]
  (let [data (parse-root (second xs))]
    [(conj (first xs) (first data)) (second data)]))

(defn solve-part-one [xs]
  (->> (parse-root xs)
      first
      (tree-seq (fn [x] (pos? (count (:children x)))) :children)
      (mapcat :md)
      (reduce +)))


(comment

  ;; -- Part One --
  (-> (read-from-file "resources/input08.txt")
      (solve-part-one))

  ;; TODO: Part Two
  
  )
