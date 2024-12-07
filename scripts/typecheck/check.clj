(ns typecheck.check
  (:require [typed.clojure :as t]
            [databog.main]))

(defn -main [& _args]
  (t/check-dir-clj "src"))
