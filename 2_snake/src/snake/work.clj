(ns snake.work
  (:use [snake.core :only (run-not-grow run-grow run-many-apples run-with-walls)]))

;;; You're writing a bot for playing snake.
;;; So, you are a snake and your goal is to collect apples.
;;; Field sizes: 40 x 30
;;; Every turn you move to one of the neighbours cell.
;;; Your function must take 2 arguments: snake's position and apple's position and decide which direction to move.
;;; Directions are: :up, :down, :left, :right (they are keywords). Your function must return one of these directions.
;;; Position (snake's or apple's) is a vector of 2 elements: x and y.
;;; In this task snake is not growing after it ate an apple so there is no danger of snake hitting itself.
;;; Note: upper left corner cell is (0, 0).
(defn first-solution [s a] (let [sx (first s) sy (second s) ax (first a) ay (second a)  ]
(if (not= sx ax )   (if (< sx ax) :right :left   )     (if (< sy ay ) :down :up )    )))



;;; Uncomment and substitute your solution
; (run-not-grow first-solution)



;;; Snake grows now (each time snake eats an apple, it's length increases).
;;; You need to write similar function as in previous task.
;;; It takes 2 arguments.
;;; First argument is snake's body - collection of cells, each cell is a vector of x and y. First cell is snake's head.
;;; Second argument is apple's position - vector of x and y.
;;; It should return direction: :up, :down, :left or :right.
;;; Note that you cannot change direction to the opposite in 1 move: snake will hit it's tail if length is 2 or more.
;;; Wait, you can change direction but snake will die :\


(defn f3 [x]  
(if (< (first x) 0) [40 (second x)]
(if (> (first x) 40)  [0 (second x)] 
(if (< (second x) 0)  [(first x) 30]
(if (> (second x) 30)  [(first x) 0]  x )))))  


(defn fn2 [i mas s] (let [ m
                          (set (remove #(contains? (set s) %) 
                            (reduce #( conj                      ( conj 
                                     ( conj 
                                     ( conj % 
                                       (f3 [(-  (first %2) 1) (second %2) ]) )
                                       (f3 [(first  %2) (- (second %2) 1) ]))
                                       (f3 [(+ (first  %2) 1) (second  %2) ]))
                                       (f3 [(first  %2) (+  (second %2) 1) ]))
                                    mas mas)))]
                      (if (contains? (set s) (first mas)) 1000
  (if (contains? m i) 1 (if (= m mas) 10000  (fn2 i m s) )))  ))



(defn second-solution [s i] (let [
                                  a (fn2 i #{  (f3 [(- (first (first s)) 1) (second (first s)) ]) }   s)         
                                  b (fn2 i #{  (f3 [(first (first s)) (- (second (first s)) 1) ]) }   s)
                                  c (fn2 i #{  (f3 [(+ (first (first s)) 1) (second (first s)) ]) }   s) 
                                  d (fn2 i #{  (f3 [(first (first s)) (+ (second (first s)) 1) ]) }   s) 
                                  xxa (Math/abs (- (- (- (first (first s)) 1) (first i))))
                                  yxa (Math/abs (- (second (first s)) (second i)))
                                  xxb (Math/abs (- (first (first s)) (first i)))
                                  yxb (Math/abs (- (- (second (first s)) 1) (second i)))
                                  xxc (Math/abs (- (+ (first (first s)) 1) (first i)))
                                  yxc (Math/abs (- (second (first s))  (second i)))
                                  xxd (Math/abs (- (first (first s)) (first i)))
                                  yxd (Math/abs (- (+ (second (first s)) 1) (second i)))
                                  xa (if (= a 1)
                                     (+ (min xxa (Math/abs(- 40 xxa)))
                                        (min yxa (Math/abs(- 30 yxa))))
                                        1000)    
                                  xb (if (= b 1)
                                     (+ (min xxb (Math/abs(- 40 xxb)))
                                        (min yxb (Math/abs(- 30 yxb))))
                                        1000)
                                  xc (if (= c 1)
                                      (+ (min xxc (Math/abs(- 40 xxc)))
                                         (min yxc (Math/abs(- 30 yxc))))
                                        1000)
                                  xd (if (= d 1)
                                      (+ (min xxd (Math/abs(- 40 xxd)))
                                         (min yxd (Math/abs(- 30 yxd))))
                                       1000)
                                  mi (reduce min [xa xb xc xd])

                                  ]
( if ( = xa mi )  :left  
  ( if (= xb mi) :up  
    (if (= xc mi)  :right :down )))))


;;; Uncomment and substitute your solution
; (run-grow second-solution)



;;; Now you have many apples (5) instead of one.
;;; Function the same as previous but it takes set of apples instead of the single apple.
;;; Each apple in the set is a vector of x and y.
;;; E.g. you can try to reach nearest apple to the snake.

(defn tfn2 [i mas s] (let [ m
                          (set (remove #(contains? (set s) %) 
                            (reduce #( conj                      ( conj 
                                     ( conj 
                                     ( conj % 
                                       (f3 [(-  (first %2) 1) (second %2) ]) )
                                       (f3 [(first  %2) (- (second %2) 1) ]))
                                       (f3 [(+ (first  %2) 1) (second  %2) ]))
                                       (f3 [(first  %2) (+  (second %2) 1) ]))
                                    mas mas)))]
                      (if (contains? (set s) (first mas)) 10000
  (if (some m i) (some m i) (if (= m mas) 10000  (tfn2 i m s) )))  ))



(defn third-solution [s i] (let [
                                  a (tfn2 (set i) #{  (f3 [(- (first (first s)) 1) (second (first s)) ]) }   s)         
                                  b (tfn2 (set i) #{  (f3 [(first (first s)) (- (second (first s)) 1) ]) }   s)
                                  c (tfn2 (set i) #{  (f3 [(+ (first (first s)) 1) (second (first s)) ]) }   s) 
                                  d (tfn2 (set i) #{  (f3 [(first (first s)) (+ (second (first s)) 1) ]) }   s) 
                                  xxa (if (not= a 10000)
                                      (Math/abs (- (- (- (first (first s)) 1) (first a)))) 1000)
                                  yxa (if (not= a 10000) 
                                      (Math/abs (- (second (first s)) (second a))) 1000)
                                  xxb (if (not= b 10000)
                                      (Math/abs (- (first (first s)) (first b))) 1000)
                                  yxb (if (not= b 10000)
                                      (Math/abs (- (- (second (first s)) 1) (second b))) 1000)
                                  xxc (if (not= c 10000)
                                      (Math/abs (- (+ (first (first s)) 1) (first c))) 1000)
                                  yxc (if (not= c 10000)
                                      (Math/abs (- (second (first s))  (second c))) 1000)
                                  xxd (if (not= d 10000)
                                      (Math/abs (- (first (first s)) (first d))) 1000)
                                  yxd (if (not= d 10000) 
                                      (Math/abs (- (+ (second (first s)) 1) (second d))) 1000)
                                  xa (if (not= a 10000)
                                     (+ (min xxa (Math/abs(- 40 xxa)))
                                        (min yxa (Math/abs(- 30 yxa))))
                                        1000)    
                                  xb (if (not= b 10000)
                                     (+ (min xxb (Math/abs(- 40 xxb)))
                                        (min yxb (Math/abs(- 30 yxb))))
                                        1000)
                                  xc (if (not= c 10000)
                                      (+ (min xxc (Math/abs(- 40 xxc)))
                                         (min yxc (Math/abs(- 30 yxc))))
                                        1000)
                                  xd (if (not= d 10000)
                                      (+ (min xxd (Math/abs(- 40 xxd)))
                                         (min yxd (Math/abs(- 30 yxd))))
                                       1000)
                                  mi (reduce min [xa xb xc xd])

                                  ]
( if ( = xa mi )  :left  
  ( if (= xb mi) :up  
    (if (= xc mi)  :right :down )))))



;;; Uncomment and substitute your solution
; (run-many-apples third-solution)



;;; Walls are added. So snake can hit wall and die.
;;; Your function now takes third argument - set of walls.
;;; Each wall is a cell that snake is not allowed to  move to.
;;; Wall is a vector of x and y.
(defn four-solution [s i z]  (third-solution (vec (concat  s  z)) i ))



;;; Uncomment and substitute your solution
 (run-with-walls four-solution)