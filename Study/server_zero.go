package main

import (
	"fmt"
	"net/http"
)

type msg string

func (m msg) ServeHTTP(resp http.ResponseWriter, req *http.Request) {
	fmt.Fprint(resp, m)
}
func main() {
	http.HandleFunc("/about", func(w http.ResponseWriter, r *http.Request) {
		fmt.Fprint(w, "About Page")
	})
	http.HandleFunc("/cat", func(w http.ResponseWriter, r *http.Request) {
		http.ServeFile(w, r, "Cat3.html")
	})
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		fmt.Fprint(w, "Index Page")
	})
	fmt.Println("Server is listening...")
	http.ListenAndServe("localhost:8181", nil)
	var c = 0
	fmt.Scanln(&c)
	if c == 0 {
		return
	}
}
