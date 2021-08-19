package main

import (
	"fmt"
	"strings"
)

type Animal interface {
	Eat()
	Move()
	Speak()
	getName() string
}

type Cow struct {
	name string
}
type Bird struct {
	name string
}
type Snake struct {
	name string
}

func (c Cow) getName() string {
	return c.name
}
func (c Cow) Eat() {
	fmt.Println("grass")
}

func (c Cow) Move() {
	fmt.Println("walk")
}

func (c Cow) Speak() {
	fmt.Println("moo")
}

func (b Bird) getName() string {
	return b.name
}

func (b Bird) Eat() {
	fmt.Println("worms")
}

func (b Bird) Move() {
	fmt.Println("fly")
}

func (b Bird) Speak() {
	fmt.Println("peep")
}

func (s Snake) Eat() {
	fmt.Println("mice")
}

func (s Snake) getName() string {
	return s.name
}

func (s Snake) Move() {
	fmt.Println("slither")
}

func (s Snake) Speak() {
	fmt.Println("hsss")
}

func createAnimal(name string, an_type string) Animal {
	switch an_type {
	case "cow":
		return Cow{name}
	case "bird":
		return Bird{name}
	case "snake":
		return Snake{name}
	default:
		fmt.Println("You write wrong animal type")
		return Cow{" "}
	}
}

func printAction(animal Animal, action string) {
	switch action {
	case "eat":
		animal.Eat()
		break
	case "move":
		animal.Move()
		break
	case "speak":
		animal.Speak()
		break
	default:
		fmt.Println("Error: wrong action")
	}
}

func main() {
	var action string
	var name string
	var third string
	var animals = make([]Animal, 0)
	fmt.Println("Enter the action and it's parametrs or 'exit' to exit")
	for {
		fmt.Print("> ")
		fmt.Scan(&action)
		action = strings.ToLower(action)
		if action == "exit" {
			fmt.Println("Bye")
			break
		}
		fmt.Scan(&name)
		fmt.Scan(&third)
		name = strings.ToLower(name)
		third = strings.ToLower(third)
		switch action {
		case "newanimal":
			animal := createAnimal(name, third)
			if animal.getName() != " " {
				animals = append(animals, animal)
				fmt.Println("Created it!")
			}
			break
		case "query":
			for _, an := range animals {
				if an.getName() == name {
					printAction(an, third)
					break
				}
			}
			break
		default:
			fmt.Println("You write wrong action")
		}
	}
}
