(ns aoc2018.day12
  (:require [clojure.string :as str]))

(set! *warn-on-reflection* true)
(def ^:dynamic *padding* 3)

(defn state-from-string [s]
  (concat
   (repeat *padding* \.)
   s
   (repeat *padding* \.)))



(defn- line->map-entry [m s]
  (let [[k _ v] (str/split s #" ")]
    (assoc m (vec k) (first v))))



(defn rules-from-string [s]
  (reduce line->map-entry {} (str/split-lines s)))

(def test-state (state-from-string "#..#.#..##......###...###"))
(def real-state (state-from-string "##.##.##..#..#.#.#.#...#...#####.###...#####.##..#####.#..#.##..#..#.#...#...##.##...#.##......####."))

(def test-rules
  (rules-from-string
   "...## => #
..#.. => #
.#... => #
.#.#. => #
.#.## => #
.##.. => #
.#### => #
#.#.# => #
#.### => #
##.#. => #
##.## => #
###.. => #
###.# => #
####. => #"))

(def real-rules
  (rules-from-string
   "##.#. => #
#.#.. => #
##... => .
...## => #
###.# => #
#.##. => #
#.### => #
####. => #
.#..# => #
...#. => .
#..#. => .
#.#.# => .
.##.# => .
..#.. => .
.#.## => #
..##. => .
.#.#. => #
#..## => #
..#.# => #
#.... => .
..### => .
#...# => .
##### => #
###.. => #
....# => .
##.## => #
.#### => .
..... => .
##..# => #
.##.. => .
.###. => .
.#... => #"))

(def ^:dynamic *initial-state* test-state)

(def ^:dynamic *rules* test-rules)

(def states (atom []))


(defn transform [x]
  (get *rules* x \.))

(defn next-state [state]
  (concat
   ".."
   (map transform (partition 5 1 state))
   "....."))


(defn sum-pots [s]
  (reduce +
          (map
           (fn [pot i] (if (= pot \#) (- i *padding*) 0))
           s
           (range))))




(defn solve-part-one [initial-state]
  (loop [state initial-state
         counter 0]
    (print (format "%02d" counter))
    (println (apply str state))
    (if (> 20 counter)
      (recur (next-state state) (inc counter))
      (sum-pots state))))


(defn solve-part-two [initial-state]
  (loop [state initial-state
         counter 0]
    (when (zero? (mod counter 10000000))
      (print "."))
    (if (> 50000000000 counter)
      (recur (next-state state) (inc counter))
      (sum-pots state))))


(comment

  (time
   (binding [*rules* real-rules]
     (solve-part-one real-state)))

  ;; heapspace!!!
  (time
   (binding [*rules* real-rules]
     (solve-part-two real-state)))
  )



