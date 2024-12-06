(ns databog.main
  (:require
   [typed.clojure :as t]))


(t/defalias Attribute t/Keyword)
(t/defalias Id t/Num)
(t/defalias TempId t/Num)

(t/defalias DatomValue (t/U Id t/Bool))

(t/defalias TxDatom  (t/HVec [t/Keyword Id Attribute DatomValue]))

(t/defalias TxDatoms (t/Vec TxDatom))


(defrecord IdMapping
  [temp-id db-id])


(t/ann-record IdMapping [temp-id :- TempId db-id :- Id])

(t/defalias IdMappings (t/Vec IdMapping))


(defrecord TxDataPart
  [tx-data id-mappings])


(t/ann-record TxDataPart [tx-data :- TxDatoms id-mappings :- IdMappings])


(defn id-mapping
  "Given a key attribute and a temp id
   Returns id mapping object"
  [temp-id db-id]
  (map->IdMapping {:temp-id temp-id
                   :db-id   db-id}))


(t/ann id-mapping [TempId Id -> IdMapping])


(defn tx-data
  ([id-key-mappings tx-data]
   (map->TxDataPart
    {:tx-data     tx-data
     :id-mappings id-key-mappings})))

(t/ann tx-data [(t/Vec IdMapping)
                (t/Vec TxDatom) -> TxDataPart])


(defn combine-tx-data
  [& tx-data-seqs]
  (tx-data
    (vec (mapcat :id-mappings tx-data-seqs))
    (vec (mapcat :tx-data tx-data-seqs))))


(t/ann combine-tx-data [TxDataPart :* :-> TxDataPart])
