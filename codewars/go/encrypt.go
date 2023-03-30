package main

import (
	"fmt"
	"strings"
)

func EncryptThis(text string) string {
	if text == "" {
		return ""
	}
	words := strings.Fields(text)
	for i, word := range words {
		firstLetter := int(rune(word[0]))
		var newWord string
		if len(word) > 2 {
			newWord = fmt.Sprintf("%d%s%s%s", firstLetter, string(word[len(word)-1]), word[2:len(word)-1], string(word[1]))
		} else if len(word) > 1 {
			newWord = fmt.Sprintf("%d%s", firstLetter, string(word[1]))
		} else {
			newWord = fmt.Sprintf("%d", firstLetter)
		}
		words[i] = newWord
	}
	return strings.Join(words, " ")
}

func main() {
	fmt.Println(EncryptThis("A wise old owl lived in an oak"))
}
