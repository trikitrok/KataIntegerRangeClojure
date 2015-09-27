(ns integer-ranges.core)

(defn- numbers-descriptors [range-descriptor]
  (clojure.string/split
    (apply str (drop-last (drop 1 (clojure.string/replace range-descriptor #" " "")))) #","))

(defn- parse-int [number-descriptor]
  (Integer/parseInt (str number-descriptor)))

(defn- numbers [numbers-list-descriptor]
  (map parse-int (numbers-descriptors numbers-list-descriptor)))

(defn- brackets [range-descriptor]
  (let [stripped_descriptor (clojure.string/replace range-descriptor #" " "")]
    [(first stripped_descriptor) (last stripped_descriptor)]))

(defn- end-points [interval-descriptor]
  (let [[lower upper] (numbers interval-descriptor)
        [opening-bracket closing-bracket] (brackets interval-descriptor)]
    [(if (= opening-bracket \[) lower (inc lower))
     (if (= closing-bracket \]) (inc upper) upper)]))

(def ^:private interval end-points)

(defn- range->set [[lower upper]]
  (set (range lower upper)))

(defn- includes-number? [range number]
  (contains? (range->set range) number))

(defn includes? [interval-descriptor numbers-str]
  (every? #(includes-number? (interval interval-descriptor) %)
          (numbers numbers-str)))

(defn all-numbers [interval-descriptor]
  [2 3 4 5])