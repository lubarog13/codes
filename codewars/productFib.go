package main

import "fmt"

func ProductFib(prod uint64) [3]uint64 {
	lastNumber := uint64(0)
	newNumber := uint64(1)
	for {
		number := newNumber
		newNumber = number + lastNumber
		if newNumber*number == prod {
			return [3]uint64{number, newNumber, uint64(1)}
		} else if number*newNumber > prod {
			return [3]uint64{number, newNumber, uint64(0)}
		}
		lastNumber = number
	}
}

func main() {
	fmt.Println(ProductFib(4895))
}
