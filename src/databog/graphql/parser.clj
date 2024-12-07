(ns databog.graphql.parser
  (:require
   [clj-antlr.core :as antlr]
   [clojure.java.io :as io]))

(set! *warn-on-reflection* true)

(def default-antlr-options
  {:throw? false})

;; Taken from Lacinia
(defn compile-grammar
  [path]
  (->
   (io/resource path)
   (slurp)
   (antlr/parser)))

(def graphql-grammar (compile-grammar "Graphql.g4"))
(def schema-grammar (compile-grammar "schema.g4"))

(def ^:private ignored-terminals
  "Textual fragments which are to be immediately discarded as they have no
  relevance to a formed parse tree."
  #{"'{'" "'}'" "'('" "')'" "'['" "']'" "'...'" "'fragment'" "'on'" "type" "&"
    "':'" "'='" "'$'" "'!'" "\"" "'@'"})

(defn ignored-terminal?
  [token-name]
  (contains? ignored-terminals token-name))

(defn parse
  "Given a Grammar file and a GraphQL String
   Rerturns a parsed expression"
  [grammar gql-string]
  (antlr/parse grammar default-antlr-options gql-string))

(defn parse-schema
  [schema-string]
  (parse schema-grammar schema-string))

(defn parse-schema-file
  [path]
  (parse-schema
   (slurp (io/resource path))))
