module WeIrDStRiNgCaSe where
import Data.Char

toWeirdCase :: String -> String
toWeirdCase a = helper a "upper" where
                helper :: String -> String -> String
                helper [] _ = ""
                helper (x:xs) now | now == "lower" = toLower x : helper xs "upper"
                                   | now == "upper" && x/=' ' = toUpper x : helper xs "lower"
                                   | otherwise = toUpper x : helper xs "upper"