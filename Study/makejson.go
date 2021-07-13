package main

import (
	"encoding/json"
	"fmt"
)

func main() {
	str := "0"
	fmt.Println("Enter name:")
	m := make(map[string]string)
	fmt.Scan(&str)
	m["name"] = str
	fmt.Println("Enter address:")
	fmt.Scan(&str)
	m["address"] = str
	barr, err := json.Marshal(m)
	if err == nil {
		fmt.Print(string(barr))
	} else {
		fmt.Print(err)
	}

}
