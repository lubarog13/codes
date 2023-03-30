package main

import "fmt"

func MultiplicationTable(size int) [][]int {
	arr := make([][]int, size)
	for i := 0; i < size; i++ {
		arr[i] = make([]int, size)
		arr[0][i] = i + 1
		arr[i][0] = i + 1
	}
	for i := 0; i < size; i++ {
		for j := 0; j < size; j++ {
			arr[i][j] = arr[i][0] * arr[0][j]
		}
	}
	return arr
}

func main() {
	fmt.Println(MultiplicationTable(3))
}
