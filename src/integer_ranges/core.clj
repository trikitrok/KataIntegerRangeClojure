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

(defn- closed-open-interval [interval-descriptor]
  (let [[lower upper] (numbers interval-descriptor)
        [opening-bracket closing-bracket] (brackets interval-descriptor)]
    [(if (= opening-bracket \[) lower (inc lower))
     (if (= closing-bracket \]) (inc upper) upper)]))

(defn- includes-number? [[lower upper] number]
  (<= lower number (dec upper)))

(defn includes? [interval-descriptor numbers-str]
  (every? #(includes-number? (closed-open-interval interval-descriptor) %)
          (numbers numbers-str)))

(defn all-numbers [interval-descriptor]
  (apply range (closed-open-interval interval-descriptor)))

(defn contains-range? [range-descriptor other-range-descriptor]
  (let [[lower upper] (closed-open-interval range-descriptor)
        [other-lower other-upper] (closed-open-interval other-range-descriptor)]
    (and (<= lower other-lower) (>= upper other-upper))))

(def end-points numbers)

(defn overlaps? [range-descriptor other-range-descriptor]
  (let [[lower upper] (closed-open-interval range-descriptor)
        [other-lower other-upper] (closed-open-interval other-range-descriptor)]
    (and (< lower other-upper) (> upper other-lower))))

(def equals? =)
