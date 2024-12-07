(ns databog.main-test
  (:require
    [clojure.test :refer [deftest is testing]]
    ;;[clojure.test.check :as tc]
    [databog.main]
    [databog.datalog.tx-data]
    ;;[matcher-combinators.matchers :as m]
    [matcher-combinators.test]
    [typed.clojure :as t]))

(deftest test-check-types-on-main
  (is (t/check-ns-clj 'databog.main))
  (is (t/check-ns-clj 'databog.datalog.tx-data)))

(deftest test-okay
  (testing "does this work?"
    (is (true? false))))

(deftest name-test
  (testing "Context of the test assertions"
    (is (match? nil {}))))
