import JsonParser
import JsonLexer
import System.IO
import Data.Maybe
import Data.List

-- #1
dot :: Json -> String -> Maybe Json
dot json str = case json of
                   ObjVal (x:xs) -> case x of
                                      (f, v) -> if f == str then Just v else dot (ObjVal (xs)) str
                   _ -> Nothing
-- #2
filterKeys :: (String -> Bool) -> Json -> Json
filterKeys pred json = case json of
                          ObjVal (lst) -> ObjVal (filter (pred.fst) lst)
                          _ -> error "expected ObjVal"
-- #3
keyCount :: Json -> Int
keyCount json = case json of
                   ObjVal (x:xs) -> length (x:xs)
                   _ -> 0

-- #4
keyList :: Json -> [String]
keyList json = case json of
                 ObjVal (lst) -> map fst lst
                 _ -> error "expected ObjVal"

-- #5
arrayList :: Json -> Int
arrayList json = case json of
                 Array (lst) -> length lst
                 _ -> error "expected Array"

-- #6
filterRange :: Int -> Int -> Json -> Json
filterRange low high json = case json of
                 Array (lst) -> Array (drop low (take (high+1) lst))
                 _ -> error "expected Array"

-- #7
filterArray :: (Json -> Bool) -> Json -> Json
filterArray pred json = case json of
                          Array (lst) -> Array (filter pred lst)
                          _ -> error "expected Array"

-- #8
extractElementsHelper lst list = [list!!x | x <- lst]
extractElements :: Json -> [Int] -> Json
extractElements json lst = case json of
                             Array (aList) -> Array (extractElementsHelper lst aList)
                             _ -> error "expected Array"


-- #9





main = do
handle <- openFile "data.json" ReadMode
jsonObj <- parseObject (makeTokenizer handle) (initPos "data.json" 0 0)
print (dot jsonObj "data")
