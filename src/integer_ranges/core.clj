(ns integer-ranges.core
  (:require [clojure.string :as string]))

(defn- remove-spaces [s]
  (string/replace s #" " ""))

(defn- remove-brackets [descriptor]
  (apply str (drop-last (drop 1 descriptor))))

(defn- numbers-descriptors [range-descriptor]
  (string/split (remove-brackets (remove-spaces range-descriptor)) #"," ))

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

(defn includes? [interval-descriptor numbers-descriptor]
  (every? #(includes-number? (closed-open-interval interval-descriptor) %)
          (numbers numbers-descriptor)))

(defn all-numbers [interval-descriptor]
  (apply range (closed-open-interval interval-descriptor)))

(defn contains-range? [range-descriptor other-range-descriptor]
  (let [[lower upper] (closed-open-interval range-descriptor)
        [other-lower other-upper] (closed-open-interval other-range-descriptor)]
    (<= lower other-lower other-upper upper)))

(def end-points numbers)

(defn overlaps? [range-descriptor other-range-descriptor]
  (let [[lower upper] (closed-open-interval range-descriptor)
        [other-lower other-upper] (closed-open-interval other-range-descriptor)]
    (and (< lower other-upper) (> upper other-lower))))

(defn equals? [range-descriptor other-range-descriptor]
  (= (closed-open-interval range-descriptor)
     (closed-open-interval other-range-descriptor)))
