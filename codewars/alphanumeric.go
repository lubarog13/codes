package main

import (
	"fmt"
	"regexp"
)

func alphanumeric(str string) bool {
	match, _ := regexp.MatchString(`^\w+$`, str)
	return match && len(str) != 0
}

func main() {
	fmt.Println(alphanumeric(".*?"))
	fmt.Println(alphanumeric("43534h56jmTHHF3k"))
	fmt.Println(alphanumeric("hi"))
}
