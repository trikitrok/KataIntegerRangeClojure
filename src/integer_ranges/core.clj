(ns integer-ranges.core)

(defn- numbers-descriptors [range-descriptor]
  (clojure.string/split
    (apply str (drop-last (drop 1 (clojure.string/replace range-descriptor #" " "")))) #","))

(defn- parse-int [number-descriptor]
  (Integer/parseInt (str number-descriptor)))

(defn- numbers [numbers-list-descriptor]
  (map parse-int (numbers-descriptors numbers-list-descriptor)))

(defn- make-range [range-descriptor]
  (let [[lower upper] (numbers range-descriptor)]
    (set (range lower (inc upper)))))

(defn includes? [range-descriptor numbers-str]
  (every? #(contains? (make-range range-descriptor) %)
          (numbers numbers-str)))
