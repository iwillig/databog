(ns databog.main
  (:gen-class))

(defn -main
  [& args]
  (println 'databog.main)
  (println 'args args)
  (println *command-line-args*))
