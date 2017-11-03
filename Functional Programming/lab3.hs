import Data.Char
import Data.Maybe
import Data.List

onlyUppercase strlst = filter (isUpper . head) strlst

shortestString [] = ""
shortestString strlst = foldl (\x y -> if length y < length x then y else x) (head strlst) strlst

shortestString' [] = ""
shortestString' strlst = foldl (\x y -> if length y <= length x then y else x) (head strlst) strlst

shortestStringHelper _ [] = ""
shortestStringHelper fun strlst = foldl (\x y -> if fun (length y) (length x) then y else x) (head strlst) strlst

shortestString3 strlst = shortestStringHelper (<) strlst

shortestString4 strlst = shortestStringHelper (<=) strlst

shortestUppercase strlst = (shortestString . onlyUppercase) strlst

toLowerCase [] = []
toLowerCase (x:xs) = toLower x : toLowerCase xs
revStringRev str = (reverse . toLowerCase) str

firstAnswer :: (a -> Maybe b) -> [a] -> Maybe b
firstAnswer _ [] = Nothing
firstAnswer fun (x:xs) = case (fun x) of
                          Nothing -> firstAnswer fun xs
                          Just v -> Just v 

allAnswersHelper fun acc lst = 
    case lst of
      [] -> Just acc
      (x:xs) -> case (fun x) of
                   Just v -> allAnswersHelper fun (acc ++ v) xs
                   Nothing -> Nothing

allAnswers :: (a -> Maybe [b]) -> [a] -> Maybe [b]
allAnswers fun lst = if null lst then error "empty list" else allAnswersHelper fun [] lst 

data Pattern = WildcardPat | VariablePat  (String) | UnitPat | ConstantPat (Int) | ConstructorPat (String, Pattern) | TuplePat ([Pattern]) deriving (Show)
data Value = Constant (Int) | Unit | Constructor (String, Value) | Tuple [Value] deriving (Show)



g f1 f2 pat =
 let
   r = g f1 f2
 in
   case pat of
     WildcardPat -> f1 ()
     VariablePat x -> f2 x
     ConstructorPat (_, p) -> r p
     TuplePat values -> foldl (\i p -> (r p) + i) 0 values
     _ -> 0

--g takes 2 functions and a pattern.  f1 is called when a WildcardPat is found, f2 is called when a variablepat x
-- is found.  they both require a num as the type returned. They compute some function that acts on WildcardPats and some
--function that works on VariablePats (both requiring num return) and adds them together.

ret1 () = 1
ret0 str = 0
countWildcards pat = g ret1 ret0 pat

countWildAndVariableLengths pat = g ret1 length pat

countAVar (str, pat) = g (\() -> 0) (\x -> if x == str then 1 else 0) pat

--creates list of variable names from pattern
checkPat' pat = 
  case pat of
    WildcardPat -> []
    VariablePat x -> [x]
    ConstructorPat (_, p) -> checkPat' p
    TuplePat values -> foldl (\lst p -> lst ++ (checkPat' p)) [] values
    _ -> []

--checks if all in list are unique
checkPat'' [] = True
checkPat'' (x:xs) = if x `elem` xs then False else checkPat'' xs

--combine into 1 function that is super readable
checkPat pat = checkPat'' (checkPat' pat)

match (val, pat) =
  case (val, pat) of 
    (_, WildcardPat) -> Just []
    (_, VariablePat x) -> Just [(x, val)]
    (Unit, UnitPat) -> Just []
    (Constant n, ConstantPat n2) -> if n == n2 then Just [] else Nothing
    (Constructor (s2, v) , ConstructorPat (s1, p)) -> if s2 == s1 then match (v, p) else Nothing
    (Tuple vs, TuplePat ps) -> if (length vs == length ps) then allAnswers match (zip vs ps) else Nothing
    (_, _) -> Nothing

firstMatch v p = firstAnswer (\x -> match (v, x)) p
    















 







