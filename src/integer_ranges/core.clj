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

(defn- end-points [range-descriptor]
  (let [[lower upper] (numbers range-descriptor)
        [opening-bracket closing-bracket] (brackets range-descriptor)]
    [lower
     (if (= closing-bracket \]) (inc upper) upper)]))

(defn- make-range [range-descriptor]
  (let [[lower upper] (end-points range-descriptor)]
    (set (range lower upper))))

(defn includes? [range-descriptor numbers-str]
  (every? #(contains? (make-range range-descriptor) %)
          (numbers numbers-str)))
