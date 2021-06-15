package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"time"
)

func main() {
	start := time.Now().UnixNano()
	n := 0
	max := 0
	data, err := ioutil.ReadFile("27688.txt")
	if err != nil {
		log.Fatal(err)
	}
	chars := string(data)
	for i := 0; i < len(chars); i++ {
		if chars[i] == 'Z' {
			n++
		} else {
			if n > max {
				max = n
			}
			n = 0
		}
	}
	fmt.Print(max)
	end := time.Now().UnixNano()
	itog := end - start
	fmt.Print(itog)
}
