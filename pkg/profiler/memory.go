package profiler

import (
	"fmt"
	"runtime"
)

// PrintMemUsage outputs the current, total and OS memory being used. As well as the number
// of garage collection cycles completed.
func PrintMemUsage() {
	var memStats runtime.MemStats
	runtime.ReadMemStats(&memStats)
	// For info on each, see: https://golang.org/pkg/runtime/#MemStats
	fmt.Printf("Alloc = %v", memStats.Alloc)
	fmt.Printf("\tTotalAlloc = %v", memStats.TotalAlloc)
	fmt.Printf("\tSys = %v", memStats.Sys)
	fmt.Printf("\tNumGC = %v\n", memStats.NumGC)
}
