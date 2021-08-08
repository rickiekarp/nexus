package main

import "testing"

func BenchmarkXorSwapOne(b *testing.B) {
	// run the Fib function b.N times
	for n := 0; n < b.N; n++ {
		intswapOne(5, 2)
	}
}

func BenchmarkXorSwapTwo(b *testing.B) {
	// run the Fib function b.N times
	for n := 0; n < b.N; n++ {
		intswapTwo(5, 2)
	}
}
