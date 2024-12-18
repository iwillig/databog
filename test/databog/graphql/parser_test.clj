(ns databog.graphql.parser-test
  (:require
    [clojure.java.io :as io]
    [clojure.test :refer [deftest is testing]]
    [databog.graphql.parser :as d.g.parser]
    [matcher-combinators.test]
    [jsonista.core :as j]
    [typed.clojure :as t]))

(defn load-schema [path]
  (-> (io/resource path)
      (slurp)
      (d.g.parser/parse-graphql-schema)))

(def example-schema-path "graphql/exampleSchema.graphql")
(def cat-and-dog-path     "graphql/catAndDog.graphql")

(deftest test-parser-types

  (testing "checking the types in the parser namespace"

    #_(is (t/check-ns-clj 'databog.graphql.parser))))

(deftest test-parsing-example-schemas
  (testing "testing basic graphql schemas"
    (doseq [schema-path [example-schema-path #_cat-and-dog-path]
            :let [parsed-schema (load-schema schema-path)
                  ast           (d.g.parser/antlr-xform parsed-schema)
                  leafs         (d.g.parser/leafs ast)]]

      (testing (str "testing:" schema-path)


        (is (match? nil
                    (map (juxt (comp :t :parent) :t)
                         leafs)))

        (is (match? nil ast))

        ))))
