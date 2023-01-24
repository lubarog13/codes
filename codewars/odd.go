package main

import "fmt"

func FindOdd(seq []int) int {
	m := make(map[int]int)
	for _, v := range seq {
		m[v] = m[v] + 1
	}
	for key, value := range m {
		if value%2 != 0 {
			return key
		}
	}
	return 0
}

func main() {
	fmt.Println(FindOdd(3))
}
