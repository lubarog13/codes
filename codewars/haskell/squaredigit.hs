module SquareDigit where
import Data.Char

squareDigit :: Int -> Int
squareDigit a = read $ (foldr (++) "" [if x=='-' then "-" else show (((^2) . digitToInt) x) | x <- (show a)]) :: Int