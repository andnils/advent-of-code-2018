(ns aoc2018.day09
  (:import [aoc2018.day09 Marble]))


(def ^:dynamic *number-of-players* 9)

(defn next-player [current-player]
  (inc (rem current-player *number-of-players*)))

(defn step-back [marble]
  (.getPrev marble))


(defn something-entirely-different-happens
  [{:keys [marbles current-player scores round] :as state}]
  (let [seven-steps-back (nth (iterate step-back marbles) 7)
        this-score (inc (.getPoints marbles))
        increase-score (+ this-score (.getPoints seven-steps-back))]
    {:marbles (.remove seven-steps-back)
     :current-player (next-player current-player)
     :round (inc round)
     :scores (update scores current-player (fnil #(+ % increase-score) 0)) 
     }))


(defn step [{:keys [marbles current-player scores round] :as state}]
  (if (= (rem round 23) 0)
    (something-entirely-different-happens state)
    {:marbles (.insertAfter (.getNext marbles) (Marble. round))
     :current-player (next-player current-player)
     :round (inc round)
     :scores scores}))

(defn setup-initial-marble []
  (let [m1 (Marble. 0)]
    (.setNext m1 m1)
    (.setPrev m1 m1)
    m1))

(defn get-initial-state []
  {:marbles (setup-initial-marble)
   :current-player 1
   :round 1
   :scores {}})


(defn play-game [num-players rounds]
  (binding [*number-of-players* num-players]
    (-> (iterate step (get-initial-state))
        (nth rounds)
        :scores
        vals
        sort
        last)))

(defn print-marbles! [m]
  (let [zero-marble (loop [xm m]
                      (if (not= 0 (.getPoints xm))
                        (recur (.getNext xm))
                        xm))]
    (loop [xm zero-marble]
      (print (.getPoints xm) " ")
      (if (not= 0 (.getPoints (.getNext xm)))
        (recur (.getNext xm))
        (println)))))

(comment

  ;; -- Part One --
  (play-game 458 71307)

  ;; -- Part Two --
  (play-game 458 7130700)
  
  )
