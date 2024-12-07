(ns databog.main-test
  (:require [clojure.test :refer [deftest is testing]]
            [databog.main]
            [typed.clojure :as t]))


(deftest test-check-types-on-main
  (is (t/check-ns-clj 'databog.main)))

(deftest test-okay
  (testing "does this work?"
    (is (true? false))))
