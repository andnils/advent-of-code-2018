(ns aoc2018.day11
  (:import [aoc2018.day11 Grid]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* true)

(defn make-grid [x y size]
  (for [y (range y (+ size y))
        x (range x (+ size x))]
    [x y size]))


(defn grid-power ^long [^Grid grid ^long size ^long top-x ^long top-y]
  (reduce
   (fn [acc [x y]] (+ acc (.getPowerLevel grid x y)))
   0
   (make-grid top-x top-y size)))


(defn solve [^Grid grid ^long square-size]
  (reduce
   (fn [[old-x old-y old-sz old-sum] [x y sz]]
     (let [new-sum (grid-power grid square-size x y)]
       (if (< old-sum new-sum)
         [x y square-size new-sum]
         [old-x old-y square-size old-sum])))
   [-1 -1 -1 -1]
   (make-grid 1 1 (- 300 (dec square-size)))))



(defn solve-part-one [serial]
  (solve (Grid. serial) 3))



(defn solve-part-two [serial]
  (let [grid (Grid. serial)]
    (reduce (fn [old [x y size power]]
              (if (< (get old 3) power)
                [x y size power]
                old))
            [-1 -1 -1 -1] 
            (pmap #(solve grid %) (range 1 28)))))



(comment

  ;; TODO: improve performance by memoizing square-sums
  ;; The square sum of e.g. 5x5-grid with at [1,1] equals
  ;; the sum of the 4x4-grid at [1,1] plus row 5 and col 5.
  
  (time
   (let [[x y square-size max-total-power]
         (solve-part-one 9306)]
     (println (str x "," y "  - " max-total-power))))
  

  (time
   (let [[x y square-size max-total-power]
         (solve-part-two 9306)]
     (println (str x "," y "," square-size "  - " max-total-power))))
  

)
