(ns integer-ranges.core-test
  (:use midje.sweet)
  (:use [integer-ranges.core]))

(facts
  "about Integer range"

  (fact
    "it contains numbers"

    (includes? "[2, 5]" "{2,3,4,5}") => truthy))
