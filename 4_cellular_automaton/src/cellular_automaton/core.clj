(ns cellular-automaton.core
  (:use quil.core))

(def live (atom {} ))

;;erwerewrwerfew
(def livenew (atom {}))
(loop [x 0]
	  (when (<= x 40)
(loop [y 0]
	  (when (<= y 30)
	  (reset! livenew (assoc @livenew [x y] :dead))
	    (recur (+ y 1))
        ))
	    (recur (+ x 1))
        ))


(def cell-size 20)
(def new-live (atom #{}))
(defn to-real-coords [cell]
  (map #(* cell-size %) cell))


(defn setup [my-map]
(fn []
(reset! live (my-map :init))
(reset! livenew (my-map :init))
(def solution (my-map :update-fn))
(def my-color (my-map :colof-fn))
(background 200)

  (smooth)                
  (frame-rate 10)  
) 
)




(defn draw-cell [draw-fn cell bl]
  (fill (first (my-color bl)) (second (my-color bl)) (last (my-color bl)))
  (let [[real-x real-y] (to-real-coords cell)]
    (draw-fn real-x real-y cell-size cell-size)))





(defn draw-live []  

(loop [x 0]
	  (when (<= x 40)
(loop [y 0]
	  (when (<= y 30)
        (draw-cell rect [ x y] (@live [x y]) )

        (recur (+ y 1))
        ))
	    (recur (+ x 1))
        )))


(defn myfn []
(fill 0 191 255)
  (rect 10 10 30 30)

  )

(defn draw []
     
	(background 200)
;(myfn)
	(draw-live )
    ) 

(defn retval [m c]
  ( if  (@m c)  (@m c) :dead
  ))


(defn update-fn []

  (reset! livenew @live)
(loop [x 0]
	  (when (<= x 40)
(loop [y 0]
	  (when (<= y 30)
        (reset! live (assoc @live [x y] (solution (retval livenew [x y])
                                    (retval livenew [(inc x) y])
                                    (retval livenew [x (inc y)])
                                    (retval livenew [(dec x) y])
                                    (retval livenew [x (dec y)])
                                    (retval livenew [(inc x) (inc y)])
                                    (retval livenew [(inc x) (dec y)])
                                    (retval livenew [(dec x) (inc y)])
                                    (retval livenew [(dec x) (dec y)]))
                           ) )
        (recur (+ y 1))
        ))
	    (recur (+ x 1))
        )))

   

(defn run [my-map]  
  ;(let [update (update-fn (my-map :update-fn))]
    (sketch
  :title "Planer"  
  :setup (setup my-map)                     
  :draw  #(do (update-fn) (draw))                      
  :size [800 600]));)
