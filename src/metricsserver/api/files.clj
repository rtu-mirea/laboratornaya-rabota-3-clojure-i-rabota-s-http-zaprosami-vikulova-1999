(ns metrics-server.api.files
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-files-with-http-info
  "Get files in directory on server"
  []
  (call-api "/files" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-files
  "Get files in directory on server"
  []
  (:data (get-files-with-http-info)))

;; Убрать директории
(defn task1 [get-files]
  (filter (fn [x] (not (get x :directory)))get-files)
  )

;; Только исполняемые файлы
(defn task2 [get-files]
  (filter (fn [x] (get x :executable))get-files)
  )

;; Поменять расширения
(defn task3 [get-files]
  (map (fn [x]
         {
          :name (clojure.string/replace (get x :name) #".conf" ".cfg")
          :size (get x :size)
          :changed (get x :changed)
          :directory (get x :directory)
          :executable (get x :executable)
          }) get-files)
  )

;; Средний размер файла, не директории
(defn task4 [get-files]
  (/ (reduce + (map (fn [x] (get x :size))
                    (filter (fn [x] (not (get x :directory)))get-files))
             )
     (count (filter (fn [x] (not (get x :directory)))get-files)))
  )

(defn -main [& args]
  (println (task1 (get-files)))
  (println (task2 (get-files)))
  (println (task3 (get-files)))
  (println (task4 (get-files)))
  )

