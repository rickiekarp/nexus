package main

import (
	"fmt"
	"math"
	"time"
)

// Cracking the coding interview - Page 68
// Print all positive integer solutions to the equation
// a^3 + b^3 = c^3 + d^3 where a, b, c and d are integers between 1 and 1000"
func main() {
	n := 500

	fmt.Println("Solution 1 (Bruteforce)")
	start := time.Now()
	bruteForce(n)
	duration := time.Since(start)
	fmt.Println(duration)

	fmt.Println("Solution 2 (Optimized Bruteforce)")
	start = time.Now()
	bruteForceOptimized(n)
	duration = time.Since(start)
	fmt.Println(duration)

}

// Bruteforce solution O(N^4)
func bruteForce(n int) {
	for A := 1; A <= n; A++ {
		for B := 1; B <= n; B++ {
			for C := 1; C <= n; C++ {
				for D := 1; D <= n; D++ {
					if A^3+B^3 == C^3+D^3 {
						//fmt.Println("Result: ", A, B, C, D)
						break
					}
				}
			}
		}
	}
}

// Optimized Bruteforce
// If there is only one valid d value for each (a,b,c), we can just compute it. O(N^3)
func bruteForceOptimized(n int) {
	for A := 1; A <= n; A++ {
		for B := 1; B <= n; B++ {
			for C := 1; C <= n; C++ {
				D := int(math.Pow(float64(A^3+B^3-C^3), 1/3))
				if A^3+B^3 == C^3+D^3 && 0 <= D && D <= n {
					//fmt.Println("Result: ", A, B, C, D)
					break
				}
			}
		}
	}
}
