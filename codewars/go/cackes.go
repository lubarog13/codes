package main

import (
	"fmt"
	"sort"
)

func Cakes(recipe, available map[string]int) int {
	count := make([]int, 0)
	for k, v := range recipe {
		count = append(count, available[k]/v)
	}
	sort.Ints(count)
	return count[0]
}

func main() {
	fmt.Println(Cakes(map[string]int{"flour": 500, "sugar": 200, "eggs": 1}, map[string]int{"flour": 1200, "sugar": 1200, "eggs": 5, "milk": 200}))
}
