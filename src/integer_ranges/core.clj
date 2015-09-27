(ns integer-ranges.core
  (:require [clojure.string :as string]))

(defn- remove-spaces [s]
  (string/replace s #" " ""))

(defn- remove-brackets [descriptor]
  (apply str (drop-last (drop 1 descriptor))))

(defn- numbers-descriptors [descriptor]
  (-> descriptor
      remove-spaces
      remove-brackets
      (string/split #",")))

(defn- numbers [numbers-list-descriptor]
  (map #(Integer/parseInt (str %))
       (numbers-descriptors numbers-list-descriptor)))

(defn- brackets [descriptor]
  (let [stripped_descriptor (string/replace descriptor #" " "")]
    [(first stripped_descriptor) (last stripped_descriptor)]))

(defn- closed-open-interval [descriptor]
  (let [[lower upper] (numbers descriptor)
        [opening-bracket closing-bracket] (brackets descriptor)]
    [(if (= opening-bracket \[) lower (inc lower))
     (if (= closing-bracket \]) (inc upper) upper)]))

(defn- includes-number? [[lower upper] number]
  (<= lower number (dec upper)))

(defn includes? [interval-descriptor numbers-descriptor]
  (every?
    #(includes-number? (closed-open-interval interval-descriptor) %)
    (numbers numbers-descriptor)))

(defn all-numbers [descriptor]
  (apply range (closed-open-interval descriptor)))

(defn contains-range? [descriptor other-descriptor]
  (let [[lower upper] (closed-open-interval descriptor)
        [other-lower other-upper] (closed-open-interval other-descriptor)]
    (<= lower other-lower other-upper upper)))

(def end-points numbers)

(defn overlaps? [descriptor other-descriptor]
  (let [[lower upper] (closed-open-interval descriptor)
        [other-lower other-upper] (closed-open-interval other-descriptor)]
    (and (< lower other-upper) (> upper other-lower))))

(defn equals? [descriptor other-descriptor]
  (= (closed-open-interval descriptor)
     (closed-open-interval other-descriptor)))
