(ns typecheck.check
  (:require
   [databog.main]
   [typed.clojure :as t]))

(defn -main
  [& _args]
  (t/check-dir-clj "src"))
