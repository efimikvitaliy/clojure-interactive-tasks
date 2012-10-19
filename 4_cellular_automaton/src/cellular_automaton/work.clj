(ns cellular-automaton.work
  (:use quil.core))
(def live (atom #{[0 4] [0 5] [1 4] [1 5] [10 4] [10 5] [10 6] [11 3] [11 7] [12 2]
[12 8] [13 2] [13 8] [14 5] [15 3] [15 7] [16 4] [16 5] [16 6] [17 5] [20 2] [20 3]
[20 4] [21 2] [21 3] [21 4] [22 1] [22 5] [24 0] [24 1] [24 5] [24 6] [34 2] [34 3]
[35 2] [35 3] } ))
(def cell-size 20)
(def new-live (atom #{}))
(defn to-real-coords [cell]
  (map #(* cell-size %) cell))


(defn draw-cell [draw-fn cell]
  (let [[real-x real-y] (to-real-coords cell)]
    (draw-fn real-x real-y cell-size cell-size)))




(defn setup []
  (smooth)                 
  (frame-rate 10)  
  (background 200)) 


(defn draw-live [live-cells]  
  (fill 0 191 255)
  (doseq [cell live-cells]
    (draw-cell rect cell)))

(defn neigh [x y] (let [ 
                        a (if (contains? @live [(inc x) y]) 1 0 )
                        b (if (contains? @live [x (inc y)]) 1 0 )
                        c (if (contains? @live [(dec x) y]) 1 0 )
                        d (if (contains? @live [x (dec y)]) 1 0 )
                        a1 (if (contains? @live [(inc x) (inc y)]) 1 0 )
                        b1 (if (contains? @live [(dec x) (inc y)]) 1 0 )
                        c1 (if (contains? @live [(dec x) (dec y)]) 1 0 )
                        d1 (if (contains? @live [(inc x) (dec y)]) 1 0 )

                        ]  
                      (reduce + 0 [a b c d a1 b1 c1 d1])))


(defn update-live [lcells]
(loop [x 0]
    (when (<= x 40)
(loop [y 0]
	  (when (<= y 30)
	    (if (= (neigh x y) 3) (swap! new-live conj [x y])  (if (and (= (neigh x y) 2) (contains? @live [x y]))  (swap! new-live conj [x y]) ))
	    (recur (+ y 1))
        ))
	    (recur (+ x 1))
        ))
)

(defn draw []


  (let [diam (random 100)           
        x    (random (width))       
        y    (random (height))]     
	(background 200)
	(draw-live @live)
 	(update-live @live)
	(reset! live @new-live)
	(reset! new-live #{})
    ))       

(defsketch example                  
  :title "Planer"  
  :setup setup                      
  :draw draw                        
  :size [800 600])                  

;;; Your task is to implement cellular automaton.
;;; The most famous example of cellular automaton is Conway's Game of Life.
;;; Unlike previous tasks now you have to implement visualization and bots. So you need to implement everything :)
;;; I suggest to use quil library for animation (it was used in all previous tasks): https://github.com/quil/quil
;;; But of course you can use whatever you want.
;;; Keep in mind that is should be simple to run your simulator with different automata (Game of Life is only 1 example).


;;; Implement and run Brian's Brain automaton in your simulator: http://en.wikipedia.org/wiki/Brian%27s_Brain


;;; Implement Wireworld automaton: http://en.wikipedia.org/wiki/Wireworld


;;; Add Wireworld implementation to Rosetta Code (it's not present here yet): http://rosettacode.org/wiki/Wireworld


;;; Implement Von Neumann cellular automaton: http://en.wikipedia.org/wiki/Von_Neumann_cellular_automata


;;; Implement Langton's ant: http://en.wikipedia.org/wiki/Langton%27s_ant


;;; Add ability to change cells' states by mouse click, to restart and pause simulation.