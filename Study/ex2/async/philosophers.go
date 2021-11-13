package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var wait sync.WaitGroup

type fork struct{ sync.Mutex }

type philosopher struct {
	num                 int
	leftFork, rightFork *fork
	is_eating           bool
}

func (p philosopher) eat() {
	defer wait.Done()
	p.is_eating = true
	for j := 0; j < 3; j++ {
		p.leftFork.Lock()
		p.rightFork.Lock()
		fmt.Printf("starting to eat %d\n", p.num)
		time.Sleep(time.Second)
		fmt.Printf("finishing eating %d\n", p.num)
		p.rightFork.Unlock()
		p.leftFork.Unlock()
		time.Sleep(time.Second)
	}
}

func main() {
	forks := make([]*fork, 5)
	for i := 0; i < 5; i++ {
		forks[i] = new(fork)
	}
	philosophers := make([]*philosopher, 5)
	for i := 0; i < 5; i++ {
		philosophers[i] = &philosopher{
			num: i + 1, leftFork: forks[i], rightFork: forks[(i+1)%5], is_eating: false}
	}
	var k, l int
	eating_count := 5
	for eating_count != 0 {
		if eating_count != 1 {
			wait.Add(2)
		} else {
			wait.Add(1)
		}
		k = rand.Intn(5)
		for philosophers[k].is_eating {
			k = rand.Intn(5)
		}
		eating_count -= 1
		philosophers[k].is_eating = true
		go philosophers[k].eat()
		if eating_count != 0 {
			l = rand.Intn(5)
			for philosophers[l].is_eating || l == k {
				l = rand.Intn(5)
			}
			philosophers[l].is_eating = true
			go philosophers[l].eat()
			eating_count -= 1
		}
		wait.Wait()
	}
}
