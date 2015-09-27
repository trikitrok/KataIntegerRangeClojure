(ns integer-ranges.core-test
  (:use midje.sweet)
  (:use [integer-ranges.core]))

(facts
  "about Integer range"

  (fact
    "it knows which numbers it includes"
    (includes? "[2, 5]" "{2,3,4,5}") => true
    (includes? "[2, 5]" "{2,-1}") => false
    (includes? "[2, 5)" "{5}") => false
    (includes? "(2, 5]" "{2}") => false
    (includes? "(2, 5)" "{2}") => false
    (includes? "(2, 5)" "{5}") => false)

  (fact
    "it tells all numbers it includes"
    (all-numbers "[2,5]") => [2 3 4 5]
    (all-numbers "[1,5]") => [1 2 3 4 5]
    (all-numbers "(1,5]") => [2 3 4 5]
    (all-numbers "(1,5)") => [2 3 4])

  (fact
    "it knows when it contains another range"
    (contains-range? "[2,10)" "[2,5]") => true
    (contains-range? "(2,10]" "[2,5]") => false
    (contains-range? "[2,4]" "[2,5]") => false)

  (fact
    "it knows its end points"
    (end-points "[3,8]") => [3 8])

  (fact
    "it knows when two ranges overlap"
    (overlaps? "[2,10)" "[9,10)") => true
    (overlaps? "[2,10)" "[1,2)") => false
    (overlaps? "[2,10)" "[10,12)") => false
    (overlaps? "[2,10]" "[10,12)") => true
    (overlaps? "[2,10]" "[3,5)") => true
    (overlaps? "[3,5)" "[2,10]") => true
    (overlaps? "[9,10)" "[2,10)") => true
    (overlaps? "[1,2)" "[2,10)") => false
    (overlaps? "[1,2)" "[5,10)") => false)

  (fact
    "it knows if two intervals are equal or not"
    (equals? "[2,10)" "[9,10)") => false))
