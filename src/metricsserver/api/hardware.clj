(ns metrics-server.api.hardware
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-metrics-with-http-info
  "Get hardware metrics"
  []
  (call-api "/hardware" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-metrics
  "Get hardware metrics"
  []
  (:data (get-metrics-with-http-info)))

;; Список, у которых cpuTemp < 2
(defn task1 [get-metrics]
  (filter (fn [x] (> (get x :cpuTemp) 2 ))get-metrics)
  )

;; Средняя cpuTemp
(defn task2 [get-metrics]
  (/ (reduce + (map (fn [x] (get x :cpuTemp)) get-metrics))
     (count (filter (fn [x] (get x :cpuTemp))get-metrics)))
  )

;; Средняя cpuLoad
(defn task3 [get-metrics]
  (/ (reduce + (map (fn [x] (get x :cpuLoad)) get-metrics))
     (count (filter (fn [x] (get x :cpuLoad))get-metrics)))
  )

(defn -main [& args]
  (println (task1(get-metrics)))
  (println (task2(get-metrics)))
  (println (task3(get-metrics)))
  )
