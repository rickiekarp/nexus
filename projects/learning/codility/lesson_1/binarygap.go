package main

import (
	"fmt"
	"strconv"
)

// A binary gap within a positive integer N is any maximal sequence of consecutive zeros that is surrounded by ones at both ends
// in the binary representation of N.

// For example, number 9 has binary representation 1001 and contains a binary gap of length 2.
// The number 529 has binary representation 1000010001 and contains two binary gaps: one of length 4 and one of length 3.
// The number 20 has binary representation 10100 and contains one binary gap of length 1. The number 15 has binary representation 1111
// and has no binary gaps. The number 32 has binary representation 100000 and has no binary gaps.

// Write a function:

// func Solution(N int) int

// that, given a positive integer N, returns the length of its longest binary gap.
// The function should return 0 if N doesn't contain a binary gap.

// For example, given N = 1041 the function should return 5, because N has binary representation 10000010001
// and so its longest binary gap is of length 5. Given N = 32 the function should return 0, because N has binary representation '100000'
// and thus no binary gaps.

// Write an efficient algorithm for the following assumptions: N is an integer within the range [1..2,147,483,647].

func main() {
	fmt.Println(solution(1))
	fmt.Println(solution(1041))
	fmt.Println(solution(529))
	fmt.Println(solution(1376796946))
}

func solution(n int) int {

	// convert to int64 to make string conversion possible with strconv
	m := int64(n)
	// format input to binary
	nBinary := strconv.FormatInt(m, 2)
	// length of the binary representation
	nBinaryLength := len(nBinary)

	// used to indicate when a binary gap starts/ends AND if we are currently in a binary gap
	binaryGapFound := false
	// length of the binary gap of one loop iteration
	binaryGapLength := 0
	// longest binary gap length of all iterations
	longestBinaryGap := 0

	for i := 0; i < nBinaryLength; i++ {
		charAtPos := byte(nBinary[i])
		// if the charAtPos is '1', we found our start/end point of a binary gap
		if charAtPos == '1' {
			if binaryGapFound {
				binaryGapFound = false

				if binaryGapLength > longestBinaryGap {
					longestBinaryGap = binaryGapLength
				}
				binaryGapLength = 0
			}

			// set binaryGapFound to true to indicate that we are in a binary gap
			binaryGapFound = true
		} else {
			// only increment counter if we are in a binary gap
			if binaryGapFound {
				binaryGapLength++
			}
		}
	}

	return longestBinaryGap
}
