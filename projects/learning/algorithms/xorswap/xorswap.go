package main

import "fmt"

func main() {
	a, b := intswapOne(5, 2)
	fmt.Println(a, b)

	a, b = intswapTwo(5, 2)
	fmt.Println(a, b)
}

func intswapOne(a, b int) (int, int) {
	// swap integers
	a = a + b
	b = a - b
	a = a - b

	return a, b
}

func intswapTwo(a, b int) (int, int) {
	// swap integers
	a = a ^ b
	b = b ^ a
	a = a ^ b

	return a, b
}
