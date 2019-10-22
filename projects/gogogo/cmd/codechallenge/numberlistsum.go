package main

import (
	"fmt"
	"os"
	"strconv"
)

/**
 * Given a list of numbers and a number k, return whether any two numbers from the list add up to k
 */
func main() {
	argsWithoutProg := os.Args[1:]
	if len(argsWithoutProg) < 3 {
		println("you need at least 3 arguments")
		return
	}

	var err error
	N := make([]int, len(argsWithoutProg))
	for i := 0; i < len(argsWithoutProg); i++ {
		if N[i], err = strconv.Atoi(argsWithoutProg[i]); err != nil {
			panic(err)
		}
	}

	result := false
	if len(argsWithoutProg) > 0 {
		result = isSumPresent(N[1:], N[0])
	}
	fmt.Println(result)
}

func isSumPresent(list []int, k int) bool {
	fmt.Println(list)
	fmt.Println("k:", k)

	for outerIndex := 0; outerIndex < len(list); outerIndex++ {
		for innerIndex := 1; innerIndex < len(list); innerIndex++ {
			if list[outerIndex] == list[innerIndex] {
				continue
			}

			if list[outerIndex]+list[innerIndex] == k {
				println("result:", list[outerIndex], "+", list[innerIndex], "=", k)
				return true
			}
		}
	}

	return false
}
