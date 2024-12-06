(ns databog.main
  (:require [typed.clojure :as t]))

(t/defalias Id t/Num)
(t/defalias TempId t/Num)

(t/defalias TxDatom t/Any)
(t/defalias TxDatoms (t/Vec TxData))


(defrecord IdMapping
  [temp-id db-id])

(t/ann-record IdMapping [temp-id :- TempId db-id :- Id])

(t/defalias IdMappings (t/Vec IdMapping))

(defrecord TxDataPart
  [tx-data id-mappings])

(t/ann-record TxDataPart [tx-data :- t/Any id-mappings :- IdMappings])

(defn id-mapping
  "Given a key attribute and a temp id
   Returns id mapping object"
  [temp-id db-id]
  (map->IdMapping {:temp-id temp-id
                   :db-id   db-id}))

(t/ann id-mapping [TempId Id -> IdMapping])

(defn tx-data
  ([id-key-mapping tx-data]
   (map->TxDataPart {:tx-data tx-data
                     :id-mappings
                     (if (vector? id-key-mapping)
                       id-key-mapping
                       [id-key-mapping])})))

(t/ann tx-data [(t/Vec IdMapping)
                (t/Vec TxDatom) -> TxDataPart])

(defn combine-tx-data
  [& tx-data-seqs]
  (tx-data
    (vec (mapcat :id-mappings tx-data-seqs))
    (vec (mapcat :tx-data tx-data-seqs))))
