package main

import "fmt"

func MoveZeros(arr []int) []int {
	var i, z int
	max := len(arr)
	for i+z < max {
		if arr[i] == 0 {
			arr = append(arr[:i], arr[i+1:]...)
			arr = append(arr, 0)
			z++
		} else {
			i++
		}
	}
	return arr
}

func main() {
	fmt.Println(MoveZeros([]int{5, 0, 0, 0, 8, 1, 1, 3, 1, 9, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0}))
}
