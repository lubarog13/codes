module Codwars.Kata.Duplicates where
import Data.List
import Data.Char

duplicateCount :: String -> Int
duplicateCount a = length $ filter (\x -> length (filter (==x) b) > 1 ) (nub b) where b = map toLower a