module Disemvowel where

disemvowel :: String -> String
disemvowel str =  [ x | x <- str, not (x `elem` "aeuioAUEIO") ]