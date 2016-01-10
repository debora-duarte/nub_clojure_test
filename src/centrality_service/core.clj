(ns centrality-service.core
  (:gen-class)
  (:require [centrality-service.util :as util-ns])
  (:require [centrality-service.graph :as graph-ns])
  (:require [centrality-service.bfs-shortest-path :as shortest-path])
  (:require [centrality-service.score :as score]))

(def edges-file "edges.txt")
(def ^:dynamic *fraudulent-clients* #{})

; Not all these bindings should be here.
; #TODO If there's time left, refactor to move some of them and to encapsulate some of the complexity,
; specially the distances calculation
(defn set-graph [graph] (def ^:dynamic *graph* graph))
(defn set-distances [distances] (def ^:dynamic *distances* distances))
(defn set-scores [scores] (def ^:dynamic *scores* scores))
(defn add-fraudulent-client [client]
  (def ^:dynamic *fraudulent-clients* (conj *fraudulent-clients* client)))

(defn initialize [] 
  (let [edges (util-ns/load-edges edges-file)]
    (set-graph (graph-ns/build-graph {} edges))
    (set-distances (shortest-path/all-pairs-shortest-path *graph*))
    (set-scores (score/rank (score/closeness *distances*)))))

(defn rank-the-clients []
  (score/rank *scores*))
    
(defn add-another-edge [edge]
  (set-graph (graph-ns/add-edge *graph* edge))
  (set-distances (shortest-path/all-pairs-shortest-path *graph*))
  (set-scores (score/rank (score/closeness *distances*)))
  (set-scores (score/rank (score/mark-as-fraudulents *distances* *scores* *fraudulent-clients*))))

(defn mark-as-fraudulent [client] ()
  (add-fraudulent-client client)
  (set-scores (score/rank (score/mark-as-fraudulent *distances* *scores* client))))

; If there's time left, make a real test - an automatic one - out of it
(defn -main
  [& args]
  (println "---------------------------after initialize--------------------------------------")
  (initialize)
  (println *scores*)
  (println "---------------------------add-edge old 64-48------------------------------------")
  (add-another-edge '(64 48))
  (println *scores*)
  (println "---------------------------add-edge new 88-33------------------------------------")
  (add-another-edge '(88 33))
  (println *scores*)
  (println "---------------------------mark 44 as fraudulent---------------------------------")
  (mark-as-fraudulent 44)
  (println *scores*)
  (println "---------------------------mark 88 as fraudulent---------------------------------")
  (mark-as-fraudulent 88)
  (println *scores*)
  (println "---------------------------add-edge new 5-51-------------------------------------")
  (add-another-edge '(5 51))
  (add-another-edge '(5 76))
  (add-another-edge '(5 89))
  (println *scores*)
  (println "---------------------------rank-the-clients--------------------------------------")
  (println (rank-the-clients)))



 
