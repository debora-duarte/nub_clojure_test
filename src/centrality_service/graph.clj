(ns centrality-service.graph)

(defn has-d-edge? [graph v1 v2]
  (some #(= v2 %) (graph v1)))

(defn has-edge? [graph v1 v2]
  (and (has-d-edge? graph v1 v2) (has-d-edge? graph v2 v1)))

(defn add-d-edge [graph [v1 v2]]
  (assoc graph v1 (conj (graph v1) v2)))

(defn add-edge [graph [v1 v2]]
  (add-d-edge (add-d-edge graph [v1 v2]) [v2 v1]))

(defn build-graph [graph edges]
  (reduce add-edge graph edges))