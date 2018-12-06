(ns aoc2018.day06
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))



(defn abs [i]
  (Math/abs i))


(defn manhattan-distance [[x1 y1] [x2 y2]]
  (+ (abs (- x2 x1))
     (abs (- y2 y1))))


(defn read-points-from-file []
  (->> (slurp "resources/input06.txt")
      (str/split-lines)
      (map #(str "[" % "]"))
      (map #(edn/read-string %))))

(def points
  (into #{} (read-points-from-file)))

(def max-x
  (reduce max (map first points)))

(def max-y
  (reduce max (map second points)))

(def min-x
  (reduce min (map first points)))

(def min-y
  (reduce min (map second points)))

(def un-occupied-coords
      (for [y (range min-y (inc max-y))
            x (range min-x (inc max-x))
            :when (not (contains? points [x y]))]
        [x y]))


(defn- rfn [dist-fn]
  (fn [{:keys [distance] :as acc} new-point]
    (let [new-distance (dist-fn new-point)]
      (if (< distance new-distance)
        acc
        (if (= distance new-distance)
          (update acc :points conj new-point)
          {:points #{new-point} :distance new-distance})))))


(defn find-closest-point [coord]
  (let [init-val {:distance 999 :points #{}}
        reducer-fn (rfn (partial manhattan-distance coord))]
    (reduce reducer-fn init-val points)))

(def grid-state
    (reduce (fn [acc coord] (assoc acc coord (find-closest-point coord)))
                {}
                un-occupied-coords))

(defn coords-for-point [point]
  (filter (fn [[[x y] {:keys [points]}]]
            (and (= 1 (count points))
                 (contains? points point)))
          grid-state))


(defn- on-border? [honk]
  (let [[[x y] _] honk]
    (or (= x min-x) (= x max-x) (= y min-y) (= y max-y))))


(defn- single-claimer? [honk]
  (let [[_ {:keys [points]}] honk]
    (= 1 (count points))))


(defn infinite? [data]
  (->> data
       (filter on-border?)
       (filter single-claimer?)
       (count)
       (pos?)))


(defn count-size-for-point
  "Return -1 if inifinte"
  [point]
  (let [cfp (vec (coords-for-point point))]
    (if (infinite? cfp)
      -1
      ;; inc to include self!
      (inc (count cfp)))))


(defn solve-part-one []
 (->> (map (fn [point] [point (count-size-for-point point)]) points)
      (map (fn [[_ size]] size))
      (apply max)))



(defn- distance-to-all-points-lt? [coord]
  (let [sum (apply + (map (partial manhattan-distance coord) points))]
    (< sum 10000)))

(defn solve-part-two []
  (let [all-coords (for [y (range min-y (inc max-y))
                         x (range min-x (inc max-x))]
                     [x y])]
    (count (filter distance-to-all-points-lt? all-coords))))


(println "READY.")


(comment

  ;; --- Part One ---
  (solve-part-one)

  ;; --- Part Two ---
  (solve-part-two)

  )
