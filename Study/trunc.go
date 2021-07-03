package main

import (
	"fmt"
)

func main() {
	var fNumb int32
	fmt.Printf("Enter a floating point number\n")
	_, err := fmt.Scan(&fNumb)
	if err == nil {
		fmt.Printf("%d", fNumb)
	}
	if err != nil {
		fmt.Errorf(err.Error())
	}
}
