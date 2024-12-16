(ns databog.graphql.parser-test
  (:require
    [clojure.java.io :as io]
    [clojure.test :refer [deftest is testing]]
    [databog.graphql.parser :as d.g.parser]
    [matcher-combinators.test]
    [typed.clojure :as t]))


(deftest test-parser-types

  (testing "checking the types in the parser namespace"

    #_(is (t/check-ns-clj 'databog.graphql.parser))))


(deftest test-parsing-example-schemas

  (testing "testing basic graphql schemas"
    (doseq [path ["graphql/exampleSchema.graphql"
                  #_"graphql/catAndDog.graphql"]]
      (testing (str "testing:" path)
        (let [example-schema (slurp (io/resource path))]

          (is (match? nil example-schema))

          (let [parsed-schema (d.g.parser/parse-graphql-schema example-schema)]

            (is (match? nil parsed-schema))))))))
