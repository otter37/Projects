
removeEl _ [] = []
removeEl x (y:ys) =
  if y == x then [] ++ ys else y : removeEl x ys


removeAll _ [] = []
removeAll x (y:ys) =
  if y == x then removeAll x ys else y : removeAll x ys

substitute x y [] = []
substitute x y (z:zs) =
  if z == x then y : substitute x y zs else z : substitute x y zs

mergeRev3 [] [] [] = [] 
mergeRev3 (x:xs) [] [] = (x:xs) 
mergeRev3 [] (y:ys) [] = (y:ys)
mergeRev3 [] [] (z:zs) = (z:zs) 
mergeRev3 (x:xs) (y:ys) [] =
  if x >= y 
     then x : mergeRev3 xs (y:ys) []
     else y : mergeRev3 (x:xs) ys []
mergeRev3 (x:xs) [] (z:zs) =
  if x >= z 
     then x : mergeRev3 xs [] (z:zs)
     else z : mergeRev3 (x:xs) [] zs
mergeRev3 [] (y:ys) (z:zs) =
  if y >= z 
     then y : mergeRev3 [] ys (z:zs)
     else z : mergeRev3 [] (y:ys) zs
mergeRev3 (x:xs) (y:ys) (z:zs) = 
  if x >= y && x >= z 
     then x : mergeRev3 xs (y:ys) (z:zs)
     else if y >= z 
           then y : mergeRev3 (x:xs) ys (z:zs)
           else z : mergeRev3 (x:xs) (y:ys) zs

data Tree a = Empty | Node a (Tree a) (Tree a) deriving (Show)

nodeValue (Empty) = error "Empty node"
nodeValue (Node a b c) = a

leftChild (Empty) = error "Empty Node"
leftChild (Node _ Empty _) = error "No left child"
leftChild (Node _ l _) = l

rightChild (Empty) = error "Empty Node"
rightChild(Node _ _ Empty) = error "No right child"
rightChild(Node _ _ r) = r

inTree x (Empty) = False
inTree x (Node v l r) = if v == x then True else (inTree x l) || (inTree x r)

leafList (Empty) = []
leafList (Node v Empty Empty) = v : []
leafList (Node v l r) = (leafList l) ++ (leafList r)

inOrderMap f (Empty) = (Empty)
inOrderMap f (Node v l r) = Node (f v) (inOrderMap f l) (inOrderMap f r)

preOrderFold f x (Empty) = x
preOrderFold f x (Node v l r) = preOrderFold f (preOrderFold f (f x v) l) r
