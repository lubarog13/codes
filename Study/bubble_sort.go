package main

import (
	"fmt"
)

func Swap(sli []int, i int) {
	t := sli[i]
	sli[i] = sli[i+1]
	sli[i+1] = t
}

func BubbleSort(sli []int) {
	for i := 0; i < len(sli); i++ {
		for j := 0; j < len(sli)-1; j++ {
			if sli[j] > sli[j+1] {
				Swap(sli, j)
			}
		}
	}
}

func intScanln(n int) ([]int, error) {
	x := make([]int, n)
	y := make([]interface{}, len(x))
	fmt.Println("Enter numbers: ")
	for i := range x {
		y[i] = &x[i]
	}
	n, err := fmt.Scanln(y...)
	x = x[:n]
	return x, err
}

func main() {
	fmt.Println("Enter up to 10 numbers ")
	fmt.Println("Enter number of numbers: ")
	var n int
	fmt.Scanln(&n)
	sli, err := intScanln(n)
	if err == nil {
		BubbleSort(sli)
		fmt.Print(sli)
	} else {
		fmt.Errorf(err.Error())
	}
}
