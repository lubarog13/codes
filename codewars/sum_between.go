package main

import "fmt"

func GetSum(a, b int) int {
	var sum, k, d int
	if a < b {
		k = a
		d = b
	} else {
		d = a
		k = b
	}
	for i := k; i <= d; i++ {
		sum += i
	}
	return sum
}

func main() {
	fmt.Print(GetSum(14, 14))
}
