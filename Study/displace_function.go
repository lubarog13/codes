package main

import (
	"fmt"
)

func GenDisplaceFn(a float64, v0 float64, s0 float64) func(float64) float64 {
	fn := func(t float64) float64 {
		return 0.5*a*t*t + v0*t + s0
	}
	return fn
}

func main() {
	fmt.Println("Enter a, v0, s0")
	var a, v0, s0, t float64
	fmt.Scanf("%f%f%f", &a, &v0, &s0)
	fn := GenDisplaceFn(a, v0, s0)
	fmt.Println("Enter time")
	fmt.Scan(&t)
	fmt.Print("Displacement is: ")
	fmt.Println(fn(t))
}
