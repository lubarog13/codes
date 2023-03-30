package main

import (
	"fmt"
	"strings"
)

func ToJadenCase(str string) string {
	words := strings.Fields(str)
	result := make([]string, 0)
	for _, word := range words {
		result = append(result, strings.Title(word))
	}
	return strings.Join(result, " ")
}

func main() {
	fmt.Println(ToJadenCase("All the rules in this world were made by someone no smarter than you. So make your own."))
}
