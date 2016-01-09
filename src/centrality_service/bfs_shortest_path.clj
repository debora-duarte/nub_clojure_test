(ns centrality-service.bfs-shortest-path)

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

(defn single-source-shortest-path [graph vertice]
  "Calculates the single-source shortest paths using Breadth-first search algorithm"
  (loop [queue (list vertice)
         shortest (initialize-shortest graph vertice)]
    (if (empty? queue)
      shortest
      (let[current-vertice (first queue)
           ajd-vertices (adjacent-vertices-not-marked shortest current-vertice graph)]
        (recur  (recalculate-queue queue ajd-vertices)  
                (recalculate-shortest shortest current-vertice ajd-vertices))))))

(defn all-pairs-shortest-path [graph]
  (let [vertices (count graph)
        curried-sssp (partial single-source-shortest-path graph)
        accumulator-function (fn [array vertice] (conj array (curried-sssp vertice)))]
        (reduce accumulator-function [] (range vertices))))