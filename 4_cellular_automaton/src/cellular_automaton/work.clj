(ns cellular-automaton.work
  (:use [cellular-automaton.core :only(run)]))


(defn my-color [bl]
  (if (= bl :live) [0 191 255] [100 100 100]))

(def live1  #{[0 4] [0 5] [1 4] [1 5] [10 4] [10 5] [10 6] [11 3] [11 7] [12 2]
[12 8] [13 2] [13 8] [14 5] [15 3] [15 7] [16 4] [16 5] [16 6] [17 5] [20 2] [20 3]
[20 4] [21 2] [21 3] [21 4] [22 1] [22 5] [24 0] [24 1] [24 5] [24 6] [34 2] [34 3]
[35 2] [35 3] } )

(def live (atom {}))
(loop [x 0]
	  (when (<= x 40)
(loop [y 0]
	  (when (<= y 30)
	  (reset! live (assoc @live [x y] ( if (contains? live1 [x y] ) :live  :dead)))
	    (recur (+ y 1))
        ))
	    (recur (+ x 1))
        ))


(defn game-live [a1 a2 a3 a4 a5 a6 a7 a8 a9]
  (let [a (count (remove #(not= :live %) [a2 a3 a4 a5 a6 a7 a8 a9] ))]
    (if (= a 3)  :live (if (and (= a 2) (= a1 :live) ) :live :dead ) )
    ))






(def my-map
  {:init @live
   :update-fn game-live
   :colof-fn my-color
  }

)



(run  my-map )





     
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


>>>>>>> olololo
;;; Add ability to change cells' states by mouse click, to restart and pause simulation.