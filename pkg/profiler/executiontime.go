package profiler

import (
	"log"
	"time"
)

// TimeTrack prints the elapsed time of the function it is invoked at
// Simply place this function at the top of the function you want to analyze like so
// defer apm.TimeTrack(time.Now(), "FunctionToAnalyze")
// Once the function is done executing, TimeTrack will print the execution time
func TimeTrack(start time.Time, name string) {
	elapsed := time.Since(start)
	log.Printf("%s took %s", name, elapsed)
}
