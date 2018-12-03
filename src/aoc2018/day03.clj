(ns aoc2018.day03
  (:require [clojure.string :as str]))


(def claim-regex #"#(?<id>\d+) @ (?<x>\d+),(?<y>\d+): (?<width>\d+)x(?<height>\d+)")


(defn ->long [s]
  (Long/parseLong s))


(defrecord Claim [id x y width height])


(defn- line->claim [line]
 (let [m (re-matcher claim-regex line)]
   (if (.matches m)
     (->Claim 
      (->long (.group m "id"))
      (->long (.group m "x"))
      (->long (.group m "y"))
      (->long (.group m "width"))
      (->long (.group m "height")))
      (throw (ex-info (str "Fail parsing line: " line))))))


(defn coords-from-claim
  "Return all coordinates for a Claim."
  [{:keys [x y width height]}]
  (for [Y (range y (+ y height))
        X (range x (+ x width))]
    [X Y]))


(defn- coords-map-from-claim [claim]
  (zipmap (coords-from-claim claim) (repeat #{(:id claim)})))


(defn- append-claim-to-state [state claim]
  (merge-with into state (coords-map-from-claim claim)))


(defn get-state-from-file
  "Read input from file and convert into a coordinate state map.
  The keys are coordinates (vectors of shape [x y]), and the values
  are sets containing ids of the claims that covers the coordinate."
  [path]
  (->> (slurp path)
       (str/split-lines)
       (map line->claim)
       (reduce append-claim-to-state {})))


(defn no-overlap?
  "Return true if all coordinates for claim id belongs
  to no other claim."
  [state id]
  (->> state
       (filter (fn [[coord ids]] (contains? ids id)))
       (every? (fn [[coord ids]] (= ids #{id})))))


(defn- coord-with-many-claims? [[coord ids]]
  (> (count ids) 1))


(defn count-overlapping
  "Number of coordinates within state with more than one claim."
  [state]
  (count (filter coord-with-many-claims? state)))


(defn find-non-overlapping-id
  "Find (the first) the id of a non-overlapping claim within state."
  [state]
  (some (fn [id] (when (no-overlap? state id) id)) (range 1 1260)))



(comment
  ;; --- Part One ---
  (count-overlapping (get-state-from-file "resources/input03.txt"))

  ;; --- Part Two ---
  (find-non-overlapping-id (get-state-from-file "resources/input03.txt"))
  )

