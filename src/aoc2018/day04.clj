(ns aoc2018.day04
  (:require [clojure.string :as str]))

(defn lines-from-file [path]
  (str/split-lines (slurp path)))


;; Note: this implementation does not handle intervals more
;; than one hour, but that's OK in this case.
(defn get-minutes-between [from to]
  (let [adjusted-to (if (> from to) (+ 60 to) to)]
    (map #(rem % 60) (range from adjusted-to))))


(defn get-guard-id [line]
  (->> line
       (re-find #"#(\d+)")
       (second)
       (Long/parseLong)))


(defn get-minute [line]
  (->> line
       (re-find #"\d\d:(\d\d)")
       (second)
       (Long/parseLong)))



(defmulti process-line
  (fn [acc line] (second (re-find #"(Guard|asleep|wakes up)" line))))


(defmethod process-line "Guard"
  [acc line]
  (assoc acc :current-guard (get-guard-id line)))


(defmethod process-line "asleep"
  [acc line]
  (assoc acc :asleep (get-minute line)))



(defmethod process-line "wakes up"
  [{:keys [current-guard asleep] :as acc} line]
  (let [wake-up-time (get-minute line)]
    (update acc current-guard
               (fn [minute-list]
                 (into (or minute-list []) (get-minutes-between asleep wake-up-time))))))



(defn get-state [lines]
  (-> (reduce process-line {} (sort lines))
      (dissoc :current-guard :asleep)))


(defn find-guard-with-max-sleep [state]
  (reduce
   (fn [current [id minutes]]
     (if (> (:asleep current) (count minutes))
       current
       {:id id :asleep (count minutes)}))
   {:id -1 :asleep 0}
   state))


(defn find-sleepiest-minute-for-guard [state id]
  (let [sleeping-minutes (get state id)]
    (key (apply max-key val (frequencies sleeping-minutes)))))


(defn find-max-sleepy-minute [state]
  (->> state
       (map (fn [[id minutes]] [id (apply max-key val (frequencies minutes))]))
       (reduce (fn [acc [id [min count]]]
                 (if (> (:max-count acc) count)
                   acc
                   {:id id :max-count count :minute min}))
               {:id -1 :max-count -1 :minute -1})))


(defn strategy-1 [input-lines]
  (let [state (get-state input-lines)
        guard-id (:id (find-guard-with-max-sleep state))
        sleepy-minute (find-sleepiest-minute-for-guard state guard-id)]
    (println "Guard" guard-id "@ minute" sleepy-minute)
    (* guard-id sleepy-minute)))


(defn strategy-2 [input-lines]
  (let [state (get-state input-lines)
        {:keys [id max-count minute]} (find-max-sleepy-minute state)]
    (println "Guard" id "@ minute" minute)
    (* id minute)))




(comment

  (strategy-1 (lines-from-file "resources/input04.txt"))
  (strategy-2 (lines-from-file "resources/input04.txt"))

  
  )
