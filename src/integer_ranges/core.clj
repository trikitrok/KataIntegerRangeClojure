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

(defn- interval [interval-descriptor]
  (let [[lower upper] (numbers interval-descriptor)
        [opening-bracket closing-bracket] (brackets interval-descriptor)]
    [(if (= opening-bracket \[) lower (inc lower))
     (if (= closing-bracket \]) (inc upper) upper)]))

(defn- range->set [[lower upper]]
  (set (range lower upper)))

(defn- includes-number? [range number]
  (contains? (range->set range) number))

(defn includes? [interval-descriptor numbers-str]
  (every? #(includes-number? (interval interval-descriptor) %)
          (numbers numbers-str)))

(defn all-numbers [interval-descriptor]
  (apply range (interval interval-descriptor)))

(defn contains-range? [range-descriptor other-range-descriptor]
  (let [[lower upper] (interval range-descriptor)
        [other-lower other-upper] (interval other-range-descriptor)]
    (and (<= lower other-lower) (>= upper other-upper))))

(defn end-points [range-descriptor]
  (numbers range-descriptor))

(defn overlaps? [range-descriptor other-range-descriptor]
  (let [[lower upper] (interval range-descriptor)
        [other-lower other-upper] (interval other-range-descriptor)]
    (and (< lower other-upper) (> upper other-lower))))

(def equals? =)
