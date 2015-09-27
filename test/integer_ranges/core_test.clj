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
    (all-numbers "[2,5]") => [2 3 4 5]
    (all-numbers "[1,5]") => [1 2 3 4 5]
    (all-numbers "(1,5]") => [2 3 4 5]
    (all-numbers "(1,5)") => [2 3 4])

  (facts
    "it knows when it contains another range"
    (contains-range? "[2,10)" "[2,5]") => true
    (contains-range? "(2,10]" "[2,5]") => false
    (contains-range? "[2,4]" "[2,5]") => false)

  (facts
    "it knows its end points"
    (end-points "[3,8]") => [3 8]))
