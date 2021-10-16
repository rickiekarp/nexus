package main

import "fmt"

// Tower of Hanoi, is a mathematical puzzle which consists of three towers (pegs) and more than one rings.
// The puzzle starts with the disks in a neat stack in ascending order of size on one rod, the smallest at the top, thus making a conical shape

type solver interface {
	play(int)
}

// towers is example of type satisfying solver interface
type towers struct {
	// an empty struct
}

// play is sole method required to implement solver type
func (t *towers) play(disks int) {
	t.moveN(disks, 1, 2, 3)
}

// recursive algorithm
func (t *towers) moveN(disks, from, to, via int) {
	if disks > 0 {
		t.moveN(disks-1, from, via, to)
		t.moveDiskToRod(from, to)
		t.moveN(disks-1, via, to, from)
	}
}

func (t *towers) moveDiskToRod(from, to int) {
	fmt.Println("Move disk from rod", from, "to rod", to)
}

func main() {
	var towers solver = new(towers)
	towers.play(4)
}
