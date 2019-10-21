package main

import (
	"fmt"
	"math"
	"time"
)

/**
 * Write a function:
 *
 * class Solution { public int solution(int[] A); }
 *
 * that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.
 *
 * For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.
 *
 * Given A = [1, 2, 3], the function should return 4.
 *
 * Given A = [−1, −3], the function should return 1.
 *
 * Write an efficient algorithm for the following assumptions:
 *
 * N is an integer within the range [1..100,000];
 * each element of array A is an integer within the range [−1,000,000..1,000,000].
 */
func main() {
	one := []int{1, 3, 6, 4, 1, 2}
	two := []int{1, 2, 3}
	three := []int{-1, -3}

	fmt.Println("Solution 1 (Bruteforce)")
	start := time.Now()
	fmt.Println("Result: ", getResult(one))
	fmt.Println("Result: ", getResult(two))
	fmt.Println("Result: ", getResult(three))
	duration := time.Since(start)
	fmt.Println(duration)

	fmt.Println("Solution 2")
	start = time.Now()
	fmt.Println("Result: ", getResultNew(one))
	fmt.Println("Result: ", getResultNew(two))
	fmt.Println("Result: ", getResultNew(three))
	duration = time.Since(start)
	fmt.Println(duration)
}

/**
 * O(n**2)
 * @param array
 * @return
 */
func getResult(array []int) int {
	result := 1

	for i, value := range array {
		if value > 0 {
			if contains(array, value) {
				result++
			} else {
				result = i
				break
			}
		}
	}

	return result
}

/**
 * O(n)
 * @param array Array to search in
 * @return Smallest positive integer
 */
func getResultNew(array []int) int {
	min := 1
	seen := make(map[int]bool) // New empty set

	for _, value := range array {
		if value > 0 {
			seen[value] = true
		}
	}

	for i := 1; i <= math.MaxInt32; i++ {
		if _, ok := seen[i]; ok {
			fmt.Println("In:", i)
		} else {
			return i
		}
	}

	return min
}

func contains(array []int, key int) bool {
	for i := 1; i < len(array); i++ {
		if array[i-1] == key {
			return true
		}
	}
	return false
}
