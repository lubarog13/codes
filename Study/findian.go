package main

import (
	"fmt"
	"strings"
)

func main() {
	var str string
	fmt.Printf("Enter a string\n")
	_, err := fmt.Scan(&str)
	str = strings.ToLower(str)
	if err == nil {
		if str[0] == 'i' && str[len(str)-1] == 'n' && strings.Contains(str, "a") {
			fmt.Println("Found!")
		} else {
			fmt.Println("Not Found!")
		}
	}
	if err != nil {
		fmt.Errorf(err.Error())
	}
}
