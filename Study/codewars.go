package main

import (
	"fmt"
	"sort"
	"strconv"
	"strings"

	"github.com/alwindoss/morse"
)

func DigitalRoot(n int) int {
	for {
		arr := strings.Split(strconv.Itoa(n), "")
		n = 0
		for i := 0; i < len(arr); i++ {
			k, _ := strconv.Atoi(arr[i])
			n += k
		}
		if len(arr) == 1 {
			return n
		}
	}
}

func Divisors(n int) int {
	res := 1
	for i := 1; i < n; i++ {
		if n%i == 0 {
			res++
		}
	}
	return res
}

func DuplicateEncode(word string) string {
	word = strings.ToLower(word)
	modified := func(r rune) rune {
		count := 0
		for i := 0; i < len(word); i++ {
			if rune(word[i]) == r {
				count++
			}
			if count > 1 {
				return rune(')')
			}
		}
		return rune('(')
	}
	return strings.Map(modified, word)
}

func DecodeMorse(morseCode string) string {
	h := morse.NewHacker()
	morseDeCode, _ := h.Decode(strings.NewReader(morseCode))
	return string(morseDeCode)
}

func InArray(array1 []string, array2 []string) []string {
	t := strings.Join(array2, " ")
	res := make([]string, 0)
	searchTheSame := func(s string) bool {
		for i := 0; i < len(res); i++ {
			if res[i] == s {
				return true
			}
		}
		return false
	}
	for i := 0; i < len(array1); i++ {
		if strings.Contains(t, array1[i]) && !searchTheSame(array1[i]) {
			res = append(res, array1[i])
		}
	}
	sort.Strings(res)
	return res
}

func main() {
	var a1 = []string{"cod", "code", "wars", "ewar", "ar"}
	var a2 = []string{}
	fmt.Println(InArray(a1, a2))
}
