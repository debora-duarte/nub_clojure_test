(ns centrality-service.core
  (:gen-class)
  (:require [centrality-service.util :as util-ns])
  (:require [centrality-service.graph :as graph-ns])
  (:require [centrality-service.bfs-shortest-path :as shortest-path])
  (:require [centrality-service.score :as score]))

(def edges-file "edges.txt")

(defn initialize [] 
  (let [edges (util-ns/load-edges edges-file)]
    ; A question that I made myself here was: is it better to left all these bindings that change here, in one
    ; place and maintain the part where there are changing bindings and functions with side effects in one
    ; place only or is it better to take the distance binding and all its calculation to the scores namespace
    ; and hide some complexity but have side effects and changing bindings ocurring in more than one place?
    (def graph (ref (graph-ns/build-graph {} edges)))
    (def distances (ref (shortest-path/all-pairs-shortest-path @graph)))
    (def scores (ref (score/rank (score/closeness @distances))))
    (def fraudulent-clients (ref #{}))))

(defn rank-the-clients []
  (score/rank @scores))
    
(defn add-another-edge [edge]
  (dosync
    (alter graph graph-ns/add-edge edge)
    (ref-set distances (shortest-path/all-pairs-shortest-path @graph))
    (ref-set scores (score/closeness @distances))
    (alter scores (partial score/mark-as-fraudulents @distances) @fraudulent-clients)))

(defn mark-as-fraudulent [client]
  (if (@fraudulent-clients client) ; if client has already been marked 
    @scores 
    (dosync 
      (commute fraudulent-clients conj client) ; adds to the list of fraudulent clients
      (alter scores (partial score/mark-as-fraudulent @distances) client)))) ; recalculates score 

; If there's time left, make a real test - an automatic one! - out of it
(defn -main
  [& args]
  (println "---------------------------after initialize--------------------------------------")
  (initialize)
  (println @graph)
  (println (rank-the-clients))
  (println "---------------------------add-edge old 64-48------------------------------------")
  (add-another-edge '(64 48))
  (println (rank-the-clients))
  (println "---------------------------add-edge new 88-33------------------------------------")
  (add-another-edge '(88 33))
  (println (rank-the-clients))
  (println "---------------------------mark 44 as fraudulent---------------------------------")
  (mark-as-fraudulent 44)
  (println (rank-the-clients))
  (println "---------------------------mark 88 as fraudulent---------------------------------")
  (mark-as-fraudulent 88)
  (println (rank-the-clients))
  (println "---------------------------add-edge new 5-51-------------------------------------")
  (add-another-edge '(5 51))
  (add-another-edge '(5 76))
  (add-another-edge '(5 89))
  (println (rank-the-clients))
  (println "---------------------------rank-the-clients--------------------------------------")
  (println (rank-the-clients)))



 
