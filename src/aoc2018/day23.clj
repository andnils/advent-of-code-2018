(ns aoc2018.day23)


(def line-regex #"pos=<(-?\d+),(-?\d+),(-?\d+)>, r=(-?\d+)")

(defn ->long [s]
  (Long/parseLong s))

(defn parse-line [line]
  (let [[_ x y z r] (re-matches line-regex line)]
    {:x (->long x)
     :y (->long y)
     :z (->long z)
     :r (->long r)}))


(defn max-r
  ([] {:r 0})
  ([x] x)
  ([old {:keys [r] :as new}]
   (if (> r (:r old))
     new
     old)))


(defn find-strongest [filename]
  (with-open [r (clojure.java.io/reader (str "resources/" filename))]
    (transduce (map parse-line) max-r (line-seq r))))

(find-strongest "input23.txt")

(defn- abs [i]
  (if (< i 0)
    (- i)
    i))


(defn cube-distance [{ax :x ay :y az :z} {bx :x by :y bz :z}]
  (+ (abs (- ax bx)) (abs (- ay by)) (abs (- az bz))))

(defn within [strongest]
  (fn [m]
    (let [d (cube-distance strongest m)]
      (<= d (:r strongest)))))

(defn counter
  ([] 0)
  ([x] x)
  ([a x] (inc a)))

(defn solve-part-one [filename]
  (let [strongest (find-strongest filename)
        filter-fn (within strongest)]
      (with-open [r (clojure.java.io/reader (str "resources/" filename))]
        (transduce
         (comp (map parse-line) (filter filter-fn))
         counter 
         (line-seq r)))))

(solve-part-one "input23.txt")

