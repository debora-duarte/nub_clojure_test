(ns centrality-service.score)

;; fazer o score receber só o grafo, ele mesmo conhecer o cálculo de shortest path que tá usando

(defn farness [distances vertice]
  (reduce + 0 distances))

(defn closeness [distances vertice]
  (/ 1 (farness distances vertice)))

(defn calculate-closeness [distances] ; rename to closeness e usar arity
  (let [vertices (range (count distances))
        pair-vertice-closeness (fn [i] (vector i (closeness (distances i) i)))]
    (mapv pair-vertice-closeness vertices)))

(defn rank [score]
  (vec (sort-by second > score)))

(defn pow [b e] (Math/pow b e))

(defn fraudulent-coefficient [k]
  (- 1 (pow 1/2 k)))

(defn fraudulent [distances score client]
  (let [coefficient-by-client (mapv #(fraudulent-coefficient %) (distances client))
        update-score (fn [[client, score]] (vector client (* (coefficient-by-client client) score)))]
    (mapv update-score score)))