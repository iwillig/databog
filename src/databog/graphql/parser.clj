(ns databog.graphql.parser
  (:require
   [typed.clojure :as t]
   [clj-antlr.core :as antlr]
   [clojure.java.io :as io]))

(t/ann clojure.core/slurp [(t/U t/Str
                                nil
                                java.net.URL) -> t/Str])
(t/ann antlr/parser [t/Str -> t/Any])

(t/ann antlr/parse  [t/Str (t/HMap :complete? false) t/Str -> t/Any])



(set! *warn-on-reflection* true)

(def default-antlr-options
  {:throw? false})

(t/ann default-antlr-options
       (t/HMap :mandatory {:throw? false} :complete? true))

;; Taken from Lacinia
(defn compile-grammar
  [path]
  (->
   (io/resource path)
   (slurp)
   (antlr/parser)))

(t/ann compile-grammar [t/Str -> t/Any])

(t/defalias Gramar t/Any)

(def graphql-grammar (compile-grammar "Graphql.g4"))
(t/ann graphql-grammar t/Str)
(def schema-grammar (compile-grammar "schema.g4"))
(t/ann schema-grammar t/Str)


(def ^:private ignored-terminals
  "Textual fragments which are to be immediately discarded as they have no
  relevance to a formed parse tree."
  #{"'{'" "'}'" "'('" "')'" "'['" "']'" "'...'" "'fragment'" "'on'" "type" "&"
    "':'" "'='" "'$'" "'!'" "\"" "'@'"})

(t/ann ignored-terminals
       (t/Set t/Str))

(defn ignored-terminal?
  [token-name]
  (contains? ignored-terminals token-name))

(t/ann ignored-terminal? [t/Str -> t/Bool])

(defn parse
  "Given a Grammar file and a GraphQL String
   Rerturns a parsed expression"
  [grammar gql-string]
  (antlr/parse grammar default-antlr-options gql-string))

(t/ann parse
       [t/Str t/Str -> t/Any])

(defn parse-schema
  [schema-string]
  (parse schema-grammar schema-string))



(defn parse-schema-file
  [path]
  (parse-schema
   (slurp (io/resource path))))
