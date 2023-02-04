package main

import (
	"fmt"
	"sort"
)

func QueueTime(customers []int, n int) int {
	times := make([]int, n)
	for _, t := range customers {
		times[0] += t
		sort.Ints(times)
	}
	return times[n-1]
}

func main() {
	fmt.Println(QueueTime([]int{1, 2, 3, 4, 5}, 100))
}
