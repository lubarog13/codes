package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strings"
)

type User struct {
	fname string
	lname string
}

func main() {
	var str string
	fmt.Println("Enter filename:")
	fmt.Scan(&str)
	f, err := os.Open(str)
	if err == nil {
		fileScanner := bufio.NewScanner(f)
		var sli = make([]User, 0)
		for fileScanner.Scan() {
			str = fileScanner.Text()
			s := strings.Split(str, " ")
			sli = append(sli, User{s[0], s[1]})
		}
		for _, i := range sli {
			fmt.Printf("%s %s \n", i.fname, i.lname)
		}
	} else {
		log.Fatal(err)
	}
}
