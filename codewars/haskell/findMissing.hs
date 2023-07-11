module Codewars.Kata.Arithmetic where

findMissing :: Integral n => [n] -> n
findMissing [] = -1
findMissing (_:[]) = -1
findMissing (_:_:[]) = -1
findMissing (x:y:z:xs) | (y - x) < (z - y) && (y - x)>0 = y + (y-x)
                       | (y - x) < (z - y) && (y - x)<0 = x + (z - y)
                       | (y - x) > (z - y) && (y - x)>0 = x + (z - y)
                       | (y - x) > (z - y) && (y - x)<0 = y + (y-x)
                       | otherwise = findMissing (y:(z:xs))