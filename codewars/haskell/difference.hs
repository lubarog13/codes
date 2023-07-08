module Difference where

difference :: Eq a => [a] -> [a] -> [a]
difference a b = [x | x<-a, length (filter (==x) b) ==0]

{-

difference :: Eq a => [a] -> [a] -> [a]
difference a b = filter (`notElem` b) 
-}