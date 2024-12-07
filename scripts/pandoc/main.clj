(ns pandoc.main
  (:require
   [clojure.pprint :as pp]
   [clojure.walk :as walk]
   [jsonista.core :as j]))

(def mapper
  (j/object-mapper {:encode-key-fn name
                    :decode-key-fn keyword}))

(def total-types (atom #{}))

;; https://play.teod.eu/document-transform-pandoc-clojure/
(defn pandoc-filter
  [in-string]
  (let [in-data (j/read-value in-string mapper)]
    ;; depth-first
    (walk/postwalk (fn [x]
                     (when-let [t (:t x)]
                       (swap! total-types conj t))
                     x)
                   in-data)))

(defn -main
  [& _args]
  (let [in-string (slurp *in*)
        in-data (pandoc-filter in-string)]


    (pp/pprint @total-types)

    ;; Print the out value
    #_(println (j/write-value-as-string in-data))))
