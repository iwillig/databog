(ns databog.graphql.parser
  (:require
   [clj-antlr.core :as antlr]
   [clojure.java.io :as io]
   [clojure.string :as str]
   [clojure.pprint :as pp]
   [clojure.walk :as walk]
   [typed.clojure :as t]))

(set! *warn-on-reflection* true)

(defn- trim-description-value
  "Given an antlr production of a description
   Returns a trimed a string value"
  [^String string-value]
  (str/trim
   (subs string-value 3 (- (.length string-value) 3))))

(t/ann trim-description-value [t/Str -> t/Str])

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
  #{"'{'" "'}'" "'('" "')'" "'['" "']'" "'...'"
    "'fragment'" "'on'" "type" "&" "interface" "implements"
    "union"
    "="
    "enum"
    "input"
    "|"
    ":"
    "{" "}"
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
  [t c p])

(t/ann-record Ast [t :- t/KW
                   c :- (t/U (t/Seq t/Any)
                             t/Any)
                   p :- t/Any])


(defrecord AntlrError [ast])

(t/ann-record AntlrError [ast :- Ast])

(defn ->ast
  ([t c] (->ast t c nil))
  ([t c p]
   (->Ast t c p)))

(defmulti antlr-xform
  first)

(defmethod antlr-xform :default
  [[:as antrl-prod]]
  antrl-prod)

(def transform-xform
  (comp (remove ignored-terminal?)
        (map antlr-xform)
        (remove nil?)))

(defn prep-parsed-antlr-prod
  [antrl-prod]
  (into []
        transform-xform
        antrl-prod))

(defn build-ast
  [[type-def & rest-antlr-prod :as args]]
  (->ast type-def (prep-parsed-antlr-prod rest-antlr-prod) (:clj-antlr/position (meta args))))


(def ast-types #{:typeDef
                 :listType
                 :argList
                 :argument
                 :interfaceDef
                 :implementationDef
                 :required

                 :scalarDef
                 :unionDef
                 :unionTypes


                 :graphqlSchema
                 :inputValueDef
                 :inputValueDefs
                 :inputTypeDef
                 :enum
                 :enumDef
                 :enumValueDefs
                 :enumValueDef

                 :nameTokens
                 :anyName
                 :typeName
                 :typeSpec
                 :fieldDefs
                 :fieldDef
                 :clj-antlr/error})

(doseq [ast-type ast-types]
  (defmethod antlr-xform ast-type
    [antlr-prod]
    (build-ast antlr-prod)))

(defmethod antlr-xform :description
  [args]
  (->ast :description
         (trim-description-value (second args))
         (:clj-antlr/position (meta args))))

(defn graphql-schema->ast
  [gql-string]
  (antlr-xform (parse-graphql-schema gql-string)))

(comment
  (ns-unmap *ns* 'antlr-xform)

  )


(def x '(:graphqlSchema
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

  )



(comment
  ;; parsed production
  '[gql-type []]

  [:typeDef
   [:description "\"\"\"\nThis is an example doc string\n\"\"\""]]

  )
