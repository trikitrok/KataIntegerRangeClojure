(ns integer-ranges.core-test
  (:use midje.sweet)
  (:use [integer-ranges.core]))

(facts
  "about integer-ranges"

  (fact
    "it knows which numbers an interval includes"
    (includes? "[2, 5]" "{2,3,4,5}") => true
    (includes? "[2, 5]" "{2,-1}") => false
    (includes? "[2, 5)" "{5}") => false
    (includes? "(2, 5]" "{2}") => false
    (includes? "(2, 5)" "{2}") => false
    (includes? "(2, 5)" "{5}") => false)

  (fact
    "it tells all numbers an interval includes"
    (all-numbers "[2,5]") => [2 3 4 5]
    (all-numbers "[1,5]") => [1 2 3 4 5]
    (all-numbers "(1,5]") => [2 3 4 5]
    (all-numbers "(1,5)") => [2 3 4])

  (fact
    "it knows when an interval contains another interval"
    (contains-range? "[2,10)" "[2,5]") => true
    (contains-range? "(2,10]" "[2,5]") => false
    (contains-range? "[2,4]" "[2,5]") => false
    (contains-range? "[2,4]" "[2,5)") => true)

  (fact
    "it knows an interval's end points"
    (end-points "[3,8]") => [3 8])

  (fact
    "it knows when two intervals overlap"
    (overlaps? "[2,10)" "[9,10)") => true
    (overlaps? "[2,10)" "[1,2)") => false
    (overlaps? "[2,10)" "[10,12)") => false
    (overlaps? "[2,10]" "[10,12)") => true
    (overlaps? "[2,10]" "[3,5)") => true
    (overlaps? "[3,5)" "[2,10]") => true
    (overlaps? "[9,10)" "[2,10)") => true
    (overlaps? "[1,2)" "[2,10)") => false
    (overlaps? "[1,2)" "[5,10)") => false
    (overlaps? "[2,10]" "(10,20)") => false
    (overlaps? "[2,10]" "[10,20)") => true
    (overlaps? "[2,10)" "[10,20)") => false)

  (fact
    "it knows if two intervals are equal or not
    (being equal means that they include the same numbers)"
    (equals? "[2,10)" "[9,10)") => false
    (equals? "[5,8]" "[5,8]") => true
    (equals? "[5,8]" "[5,9)") => true
    (equals? "[4,8]" "(3,9)") => true
    (equals? "(4,8]" "[5,9)") => true))
