(ns databog.graphql.parser-test
  (:require
    [clojure.test :refer [deftest is testing]]
    [databog.graphql.parser :as d.g.parser]
    [matcher-combinators.test]
    [typed.clojure :as t]
    [clojure.java.io :as io]))

(deftest test-parser-types
  (testing "checking the types in the parser namespace"
    (is (t/check-ns-clj 'databog.graphql.parser))))


(deftest test-parsing-example-schemas
  (testing "testing basic graphql schemas"
    (doseq [path ["graphql/exampleSchema.graphql"
                  "graphql/catAndDog.graphql"]]
      (testing (str "testing:" path)
        (let [example-schema (slurp (io/resource path))]
          (is (match? nil example-schema))
          (let [parsed-schema (d.g.parser/parse-graphql-schema example-schema)]
            (is (match? nil parsed-schema))))))))
