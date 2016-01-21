(ns centrality-service.score)

(defn farness [distances vertice]
  (reduce + 0 (distances vertice)))

(defn closeness 
  ([distances vertice] (double (/ 1 (farness distances vertice))))
  
  ([distances]
    (let [vertices (range (count distances))
          pair-vertice-closeness (fn [i] (vector i (closeness distances i)))]
      (mapv pair-vertice-closeness vertices))))

(defn rank [score]
  (vec (sort-by second > score)))

(defn pow [b e] (Math/pow b e))

(defn fraudulent-coefficient [k]
  (- 1 (pow 1/2 k)))

(defn mark-as-fraudulent [distances score client]
  (let [coefficient-by-client (mapv #(fraudulent-coefficient %) (distances client))
        update-score (fn [[client, client-score]] (vector client (* (coefficient-by-client client) client-score)))]
    (mapv update-score score)))

(defn mark-as-fraudulents [distances score clients]
  (let [curried-mark-as-fraudulent (partial mark-as-fraudulent distances)]
    (reduce curried-mark-as-fraudulent score clients)))