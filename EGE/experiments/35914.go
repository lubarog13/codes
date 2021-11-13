package main

import "fmt"

func main() {
	count := 0
	for i := 45000000; i <= 50000000; i++ {
		count = 0
		func(i int) {
			count = 0
			for j := 1; j <= i; j += 2 {
				if i%j == 0 {
					count++
				}
				if count==6{
					break
				}
			}
			if count == 5 {
				fmt.Println(i)
			}
			if i%1000000 == 0 {
				fmt.Println(i)
			}
		}(i)
	}
}
