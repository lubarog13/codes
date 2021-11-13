package main

import (
	"fmt"
	"sort"
	"sync"
)

func sort_subslice(subslice []int, wg *sync.WaitGroup) {
	defer wg.Done()
	sort.Ints(subslice)
	fmt.Println("Subslice: ", subslice)
}

func main() {
	var n int
	var x int
	slice := make([]int, 0)
	fmt.Println("Enter number of integers: ")
	fmt.Scan(&n)
	fmt.Println("Enter array: ")
	for i := 0; i < n; i++ {
		fmt.Scan(&x)
		slice = append(slice, x)
	}
	var wait sync.WaitGroup
	k := n / 4
	subSlice1 := slice[:k]
	subSlice2 := slice[k : k*2]
	subSlice3 := slice[k*2 : k*3]
	subSlice4 := slice[k*3:]
	wait.Add(4)
	go sort_subslice(subSlice1, &wait)
	go sort_subslice(subSlice2, &wait)
	go sort_subslice(subSlice3, &wait)
	go sort_subslice(subSlice4, &wait)
	wait.Wait()
}
