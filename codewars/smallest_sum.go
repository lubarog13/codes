package main

import "fmt"

func Solution(ar []int) int {
	count := 1
	sum := 0
	for count != 0 {
		count = 0
		sum = 0
		for i := 0; i < len(ar); i++ {
			for j := 0; j < len(ar); j++ {
				if ar[j] == 1 {
					continue
				}
				if ar[i] > ar[j] {
					ar[i] = ar[i] - ar[j]
					count++
					continue
				} else if ar[j] > ar[i] {
					ar[j] = ar[j] - ar[i]
					count++
				}
			}
			sum += ar[i]
		}
	}
	return sum
}

func main() {
	fmt.Println(Solution([]int{1, 1, 2, 2, 2, 1, 1}))
}
