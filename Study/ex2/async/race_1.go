package main

import "fmt"

func main() {
	/*
	A data race happens when two goroutines access the same variable concurrently, and at least one of the accesses is a write.
	*/
	var counters = map[int]int{}
	for i := 0; i < 5; i++ {
		go func(counters map[int]int, th int) {
			for j := 0; j < 5; j++ {
				counters[th*10+j]++
			}
		}(counters, i)
	}
	fmt.Scanln()
	fmt.Println("counters result", counters)
}
