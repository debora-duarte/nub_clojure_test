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

(defn adjacent-vertices-not-marked [shortest vertice graph]
  (filter #(= (shortest %) nil) (graph vertice))) 

(defn recalculate-queue [queue new-vertices]
  (concat (rest queue) new-vertices))

(defn recalculate-shortest [shortest current-vertice adj-vertices]
  (let [dist-current-vertice (shortest current-vertice)]
    (into (vector) 
      (map-indexed 
        (fn [index item] 
          (if (some #(= % index) adj-vertices) (inc dist-current-vertice) item))  
        shortest))))

(defn initialize-shortest [graph vertice]
  (assoc (vec (repeat (count graph) nil)) vertice 0))

(defn shortest-path [graph vertice]
  "Calculates the single-source shortest paths using Breadth-first search algorithm"
  (loop [queue (list vertice)
         shortest (initialize-shortest graph vertice)]
    (if (empty? queue)
      shortest
      (let[current-vertice (first queue)
           ajd-vertices (adjacent-vertices-not-marked shortest current-vertice graph)]
        (recur  (recalculate-queue queue ajd-vertices)  
                (recalculate-shortest shortest current-vertice ajd-vertices))))))