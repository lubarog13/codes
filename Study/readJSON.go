package main

import (
	"encoding/json"
	"fmt"
	"os"
)

type Record struct {
	Name    string
	Surname string
	Tel     []Telephone
}

type Telephone struct {
	Mobile bool
	Number string
}

func loadFromJson(filename string, key interface{}) error {
	in, err := os.Open(filename)
	if err != nil {
		return err
	}
	decodeJson := json.NewDecoder(in)
	err = decodeJson.Decode(key)
	if err != nil {
		return err
	}
	return nil
}

func saveToJSON(filename *os.File, key interface{}) {
	encodeJSON := json.NewEncoder(filename)
	err := encodeJSON.Encode(key)
	if err != nil {
		fmt.Println(err)
		return
	}
}

func main() {
	arguments := os.Args
	if len(arguments) == 2 {
		fmt.Println("А где имя файла?")
	}
	filename := arguments[1]
	var record Record
	err := loadFromJson(filename, &record)
	if err != nil {
		fmt.Println("Что-то не так")
		fmt.Println(err)
	} else {
		fmt.Println(record)
	}
	myRecord := Record{
		Name:    "Masha",
		Surname: "Ivanova",
		Tel:     []Telephone{{Mobile: true, Number: "1029384756"}, {Mobile: false, Number: "1234"}},
	}
	filename = arguments[2]
	file, err := os.OpenFile(filename, os.O_CREATE|os.O_WRONLY, os.ModePerm)
	if err != nil {
		fmt.Print("error: ")
		fmt.Println(err)
	}
	saveToJSON(file, myRecord)
	/*
		// json_data, err := json.Marshal(myRecord)
		if err != nil {
			fmt.Println(err)
		}
		_, err = file.WriteString("{}")
		if err != nil {
			fmt.Println(err)
		}
		file.Close() */

}
