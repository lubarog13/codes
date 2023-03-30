package main

import (
	"fmt"
	"math"
	"sort"
)

func SflpfData(k, nMax int) []int {
	simple := make([]int, 0)
	for i := 2; i < k; i++ {
		isSimple := true
		for j := 2; j < i; j++ {
			if i%j == 0 {
				isSimple = false
			}
		}
		if isSimple {
			simple = append(simple, i)
		}
	}
	result := make([]int, 0)
	for i, s1 := range simple {
		for _, s2 := range simple[i:] {
			if s1+s2 == k {
				n := 1
				for {
					fmt.Println(s1, s2, n, int(math.Pow(float64(s1), float64(n))), int(math.Pow(float64(s1), float64(n)))*s2)
					if int(math.Pow(float64(s2), float64(n)))*s1 <= nMax && s2 != s1 {
						result = append(result, int(math.Pow(float64(s2), float64(n)))*s1)
					}
					if int(math.Pow(float64(s1), float64(n)))*s2 <= nMax {
						result = append(result, int(math.Pow(float64(s1), float64(n)))*s2)
						n++
					} else {
						n = 1
						break
					}
				}
			} else if s1+s2 > k {
				break
			}
		}
	}
	sort.Ints(result)
	fmt.Println(simple)
	return result
}

func main() {
	fmt.Println(SflpfData(10, 200))
}
