(ns aoc2018.day14)

(defn recipe-maker [recipes sz e1 e2 stop-at]
  (let [sum-of-recipes (+ (get recipes e1) (get recipes e2))
        new-recipes (conj (if (> sum-of-recipes 9)
                            (conj recipes 1)
                            recipes)
                          (mod sum-of-recipes 10))
        new-sz (if (> sum-of-recipes 9) (+ 2 sz) (+ 1 sz))
        new-e1 (mod (+ 1 e1 (get recipes e1)) new-sz)
        new-e2 (mod (+ 1 e2 (get recipes e2)) new-sz)
        ]
    (if (> sz (+ 10 stop-at))
      recipes
      (recur new-recipes new-sz new-e1 new-e2 stop-at))))


(defn solve [stop-at]
  (recipe-maker [3 7] 2 0 1 stop-at))


(defn solve-part-1 [stop-at]
  (->> (solve stop-at)
       (drop stop-at)
       (take 10)
       (apply str)))


(comment
  (solve-part-1 846601)

  )
