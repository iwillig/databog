(ns databog.graphql.parser
  (:require
   [clj-antlr.core :as antlr]
   [clojure.java.io :as io]
   [clojure.pprint :as pp]
   [clojure.walk :as walk]
   [typed.clojure :as t]))

(set! *warn-on-reflection* true)

(t/ann clojure.core/slurp [(t/U t/Str
                                nil
                                java.net.URL) -> t/Str])

(t/defalias antlrOptions (t/HMap :mandatory {:throw? false} :complete? true))
(t/defalias antlrGrammar t/Any)
(t/defalias parsedGraphQLSchema t/Any)
(t/defalias parsedGraphQLExpression t/Any)

(t/ann antlr/parser [t/Str -> t/Any])

(t/ann antlr/parse  [antlrGrammar antlrOptions t/Str -> t/Any])

(def default-antlr-options
  {:throw? false})

(t/ann default-antlr-options antlrOptions)

;; The following code is taken from Lacinia
;; https://github.com/walmartlabs/lacinia
(defn compile-grammar
  [path]
  (->
   (io/resource path)
   (slurp)
   (antlr/parser)))

(t/ann compile-grammar [t/Str -> antlrGrammar])

(def graphql-grammar (compile-grammar "Graphql.g4"))
(t/ann graphql-grammar antlrGrammar)

(def schema-grammar (compile-grammar "schema.g4"))
(t/ann schema-grammar antlrGrammar)

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
       [antlrGrammar t/Str -> t/Any])

(defn parse-graphql-schema
  "Given a GraphQL Schema file
   Returns parsed GraphQL SDL Schema Document"
  [schema-string]
  (parse schema-grammar schema-string))

(t/ann parse-graphql-schema
       [t/Str -> parsedGraphQLSchema])

(defn parse-schema-file
  [path]
  (parse-graphql-schema
   (slurp (io/resource path))))

(t/ann parse-schema-file
       [t/Str -> parsedGraphQLSchema])

(defn parse-graphql-expression
  [gql-expression]
  (parse graphql-grammar gql-expression))

(t/ann parse-graphql-expression
       [t/Str -> parsedGraphQLExpression])

(defrecord Ast
  [t c])

(defn walk
  [])

(comment
  (:graphqlSchema
   (:typeDef
    "type"
    (:anyName (:nameTokens "Mutation"))
    (:fieldDefs
     "{"
     (:clj-antlr/error
      (:fieldDef "}" (:anyName (:nameTokens "type")) "Query"))
     (:clj-antlr/error
      (:fieldDef (:anyName (:nameTokens "Query")) "{"))
     "}"))))

(comment
  ;; parsed production
  '[gql-type []]

  [:typeDef
   [:description "\"\"\"\nThis is an example doc string\n\"\"\""]]

  )
