(ns databog.main-test
  (:require [clojure.test :as t :refer [deftest is testing]]))

(deftest test-okay

  (testing "does this work?"
    (is (true? false))))
