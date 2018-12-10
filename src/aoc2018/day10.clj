(ns aoc2018.day10
  (:require [clojure.string :as str]))

(set! *warn-on-reflection* true)

(def ^:dynamic *width* 70)
(def ^:dynamic *height* 20)


(defprotocol Movable
  (move [this]))

(defrecord Point [^long x ^long y ^long dx ^long dy]
  Movable
  (move [this]
    (->Point (+ x dx) (+ y dy) dx dy)))

(defn make-point [x y dx dy]
    (->Point x y dx dy))

(defn ->long [^String s]
  (Long/parseLong s))

(defn abs [^long n]
  (Math/abs n))

(def r #"position=<[ ]?([-0-9]+),[ ]*([-0-9]+)> velocity=<[ ]?([-0-9]+),[ ]*([-0-9]+)>")

(defn parse-line [^String s]
  (if-let [[_ X Y DX DY] (re-find r s)]
    (make-point (->long X) (->long Y) (->long DX) (->long DY))
    (throw (ex-info "Parse failed" {:input s}))))

(defn parse-lines [lines]
  (map parse-line lines))

(defn lines-from-file [path]
  (-> (slurp path)
      (str/split-lines)))

(defn move-all [points]
  (mapv move points))

(defn boundaries [points]
  (reduce
   (fn [{:keys [min-x max-x min-y max-y]} point]
     {:min-x (min min-x (:x point))
      :min-y (min min-y (:y point))
      :max-x (max max-x (:x point))
      :max-y (max max-y (:y point))})
    {:min-x Long/MAX_VALUE
     :min-y Long/MAX_VALUE
     :max-x Long/MIN_VALUE
     :max-y Long/MIN_VALUE}
   points))

(defn within-bounds [{:keys [min-x min-y max-x max-y]}]
  (and (< (abs (- max-x min-x)) *width*)
       (< (abs (- max-y min-y)) *height*)))


(defn collect-points [points]
  (let [boundaries (boundaries points)]
    (persistent!
     (reduce
      (fn [acc point]
        (conj! acc [(- (:x point) (:min-x boundaries))
                    (- (:y point) (:min-y boundaries))]))
      (transient #{})
      points))))


(defn draw-points [points iteration]
  (println iteration)
  (let [point-idx (collect-points points)]
    (doseq [y (range *height*)
            x (range *width*)]
      (if (contains? point-idx [x y])
        (print "*")
        (print " "))
      (when (= x (dec *width*))
        (println)))))




(comment
  "
 
 
  **    *    *  ******  *       *        ****     **    *
 *  *   *    *       *  *       *       *    *   *  *   *
*    *  *    *       *  *       *       *       *    *  *
*    *  *    *      *   *       *       *       *    *  * 
*    *  ******     *    *       *       *       *    *  * 
******  *    *    *     *       *       *       ******  * 
*    *  *    *   *      *       *       *       *    *  * 
*    *  *    *  *       *       *       *       *    *  * 
*    *  *    *  *       *       *       *    *  *    *  * 
*    *  *    *  ******  ******  ******   ****   *    *  ****** 
 
AHZLLCAL
 
"
  )


(defn -main [& args]
  (let [max-iterations 10333
          data (parse-lines (lines-from-file "resources/input10.txt"))]
      (loop [points data
             count 0]
        (when (within-bounds (boundaries points))
          (draw-points points count))
        (if (< count max-iterations)  
          (recur (move-all points) (inc count))
          points))))
