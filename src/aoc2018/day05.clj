(ns aoc2018.day05
  (:require [clojure.string :as str]
            [clojure.core.async :as async :refer [<! <!! >! >!!]]))


(defn same-type? [c1 c2]
  (= (str/lower-case c1)
     (str/lower-case c2)))


(defn react? [c1 c2]
  (and (not= c1 c2)
       (same-type? c1 c2)))


(defn start-channel [data]
  (let [chan (async/chan 128)]
    (async/go
      (doseq [c (seq data)]
        (>! chan c))
      (async/close! chan))
    chan))


(defn read-channel [chan]
  (loop [acc (transient [])
         x (<!! chan)
         y (<!! chan)]
    (if (and y (not= y \newline))
      (if (react? x y)
        (if-let [last-acc (get acc (dec (count acc)))]
          (recur (pop! acc) last-acc (<!! chan))
          (recur acc (<!! chan) (<!! chan)))
        (recur (conj! acc x) y (<!! chan)))
      (apply str (persistent! (conj! acc x))))))


(defn react! [data]
  (-> (start-channel data)
      (read-channel)))


(defn solve-part-one [data]
  (count (react! data)))


(defn solve-part-two [data]
  (apply min (for [exclude "abcdefghijklmnopqrstuvwxyz"]
               (let [regex (re-pattern (str "(?i)" exclude))]
                 (-> (str/replace data regex "")
                     react!
                     count)))))




(comment
  ;; --- Part One ---
  ;; 0.03 secs!
  (time
   (-> (slurp "resources/input05.txt")
       (solve-part-one)))

  ;; --- Part Two ---
  ;; 0.6 secs
  (time
   (-> (slurp "resources/input05.txt")
       (solve-part-two)))

  )
