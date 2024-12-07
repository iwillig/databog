(ns typecheck.check
  (:require
   [databog.datalog.tx-data]
   [typed.clojure :as t]))

(defn -main
  [& _args]
  (t/check-dir-clj "src"))
