(ns aoc2018.day05
  (:require [clojure.string :as str]
            [clojure.core.async :as async :refer [<! <!! >! >!!]]))


(defn same-type? [c1 c2]
  (= (str/lower-case c1)
     (str/lower-case c2)))


(defn react? [c1 c2]
  (and (not= c1 c2)
       (same-type? c1 c2)))


(defn start-channel
  ([data]
   (start-channel data nil)) 
  ([data xf]
   (let [chan (async/chan 128 xf)]
     (async/go
       (doseq [c (seq data)]
         (>! chan c))
       (async/close! chan))
     chan)))


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


(defn- is-not-char [c]
   (fn [x] (or (not= (int x) (int c))
               (not= (int x) (+ 32 (int c))))))


(defn react!
  ([data]
   (-> (start-channel data)
       (read-channel)) )
  ([data exclude-char]
   (-> (start-channel data (filter (is-not-char exclude-char)))
       (read-channel))))


(defn solve-part-one [data]
  (count (react! data)))


(defn solve-part-two [data]
  (apply min (for [exclude-char "abcdefghijklmnopqrstuvwxyz"]
               (-> (react! data exclude-char)
                   count))))




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
