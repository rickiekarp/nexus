package main

import (
	"fmt"
)

const arraySize = 100

/*
 * Imagine 100 bulbs in a long row. Each bulb has an on / off switch.
 *
 * First, all 100 switches are moved so that all the lights are on.
 * In the next step only every 2nd switch will be moved, in the 3rd step every 3rd, in the 4th step every 4th, in the 5th every 5th and
 * this continues until the 99th and the 100th switch.
 *
 * And now the big question: How many lamps burn after this action?
 */
func main() {

	//first step: set all bools to true
	var boolArray [arraySize]bool
	for i := range boolArray {
		boolArray[i] = true
	}

	//second step: update bools with each iteration
	for i := 2; i < arraySize; i++ {
		a := 0
		for a < len(boolArray) {
			if i <= a {
				boolArray[a] = !boolArray[a]
			}
			a += i
		}
	}

	fmt.Println("Lamps active:", getTrueCount(boolArray))
}

func getTrueCount(array [arraySize]bool) int {
	boolCounter := 0
	for i, selectedBool := range array {
		fmt.Println(i, selectedBool)
		if selectedBool {
			boolCounter++
		}
	}
	return boolCounter
}
