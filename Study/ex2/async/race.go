package main

import (
	"fmt"
)

func main() {
	c := 0
	/*
		A data race happens when two goroutines access the same variable concurrently, and at least one of the accesses is a write.
		 The records are overlapped.
		 Expected Value: 200000
	*/
	for j := 0; j < 2; j++ {
		go func(c *int) {
			for i := 0; i < 100000; i++ {
				*c++
			}
		}(&c)
	}
	fmt.Println("Press Enter")
	fmt.Scanln()
	fmt.Print(c)
}
