(ns databog.main-test
  (:require [clojure.test :as t :refer [deftest is testing]]))

(deftest test-okay
  (is (true? false)))
