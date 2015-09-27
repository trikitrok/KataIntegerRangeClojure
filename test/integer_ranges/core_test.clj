(ns integer-ranges.core-test
  (:use midje.sweet)
  (:use [integer-ranges.core]))

(facts
  "about Integer range"

  (facts
    "it knows which numbers it includes"
    (includes? "[2, 5]" "{2,3,4,5}") => true
    (includes? "[2, 5]" "{2,-1}") => false
    (includes? "[2, 5)" "{5}") => false
    (includes? "(2, 5]" "{2}") => false
    (includes? "(2, 5)" "{2}") => false
    (includes? "(2, 5)" "{5}") => false)

  (facts
    "it tells all numbers it includes"
    (all-numbers "[2,5]") => [2 3 4 5]))
