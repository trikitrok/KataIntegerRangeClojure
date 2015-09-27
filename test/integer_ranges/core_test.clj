(ns integer-ranges.core-test
  (:use midje.sweet)
  (:use [integer-ranges.core]))

(facts
  "about Integer range"

  (fact
    "it knows which numbers it includes"
    (includes? "[2, 5]" "{2,3,4,5}") => true
    (includes? "[2, 5]" "{2,-1}") => false
    (includes? "[2, 5)" "{5}") => false))
