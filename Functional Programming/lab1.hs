dotProduct (a,b) (x,y) = a*x + b*y


distance (x1, y1) (x2, y2) = sqrt((x2-x1)^2 + (y2-y1)^2)


tripleDistance (x1, y1, z1) (x2, y2, z2) = sqrt ((x2-x1)^2 + (y2-y1)^2 + (z2-z1)^2)


findMin :: (Ord a) => [a] -> a
findMin [] = error "empty list"
findMin [x] = x
findMin (x:xs) = min x (findMin xs)

