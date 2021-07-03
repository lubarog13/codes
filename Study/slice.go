package main

import (
	"fmt"
	"sort"
	"strconv"
)

func main() {
	str := "0"
	var sli = make([]int, 3)
	for str[0] != 'X' {
		fmt.Println("Enter number slice or X to exit")
		fmt.Scan(&str)
		if str[0] != 'X' {
			a, err := strconv.Atoi(str)
			if err != nil {
				fmt.Println("Wrong number!")
				continue
			}
			sli = append(sli, a)
			sort.Ints(sli)
			for _, val := range sli {
				fmt.Printf("%d ", val)
			}
			fmt.Print("\n")
		}
	}
}
