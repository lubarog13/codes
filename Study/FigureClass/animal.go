package main

import (
	"fmt"
	"strings"
)

type Animal struct {
	eat   string
	move  string
	speak string
}

func (animal *Animal) Eat() {
	fmt.Println(animal.eat)
}

func (animal *Animal) Move() {
	fmt.Println(animal.move)
}

func (animal *Animal) Speak() {
	fmt.Println(animal.speak)
}

func searchAnimal(animal string) Animal {
	switch animal {
	case "cow":
		return Animal{"grass", "walk", "moo"}
	case "bird":
		return Animal{"worms", "fly", "peep"}
	case "snake":
		return Animal{"mice", "slither", "hsss"}
	default:
		return Animal{"none", "none", "none"}
	}
}

func searchAction(animal Animal, action string) {
	if animal.eat == "none" {
		fmt.Println("Error: I don't know animal")
		return
	}
	switch action {
	case "eat":
		animal.Eat()
		return
	case "move":
		animal.Move()
		return
	case "speak":
		animal.Speak()
		return
	default:
		fmt.Println("Error: I don't know action")
		return
	}
}

func main() {
	var animal string
	var action string
	fmt.Println("Enter animal and it's action or exit")
	for {
		fmt.Print(">")
		fmt.Scan(&animal)
		if animal == "exit" {
			fmt.Println("Goodbye")
			break
		}
		fmt.Scan(&action)
		animal = strings.ToLower(animal)
		action = strings.ToLower(action)
		an := searchAnimal(animal)
		searchAction(an, action)
	}

}
