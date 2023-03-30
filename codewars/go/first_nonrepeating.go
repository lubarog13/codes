package main

import (
	"fmt"
	"strings"
)

func FirstNonRepeating(str string) string {
	low := strings.ToLower(str)
	for _, symbol := range str {
		if strings.Count(low, strings.ToLower(string(symbol))) == 1 {
			return string(symbol)
		}
	}
	return ""
}

func main() {
	fmt.Println(FirstNonRepeating("stress"))
	fmt.Println(MultiplicationTable(3))
}
