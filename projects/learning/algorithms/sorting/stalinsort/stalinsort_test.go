package stalinsort

import (
	"testing"
)

func TestSort(t *testing.T) {
	for _, data := range []struct {
		input  []int
		output int
	}{
		{
			input:  []int{1, 2, 10, 3, 2, 4, 15, 6, 30, 20},
			output: 5,
		},
		{
			input:  []int{78, 33, 100, 61, 65, 72, 11, 66, 89, 3},
			output: 2,
		},
		{
			input:  []int{2, 2, 3, 1, 10},
			output: 4,
		},
		{
			input:  []int{1, 2, 10, 3, 2, 4, 15, 6, 30, 20},
			output: 5,
		},
		{
			input:  []int{1, 2, 2, 3, 2, 5},
			output: 5,
		},
	} {
		res := Sort(data.input)
		if len(res) != data.output {
			t.Fatalf("expected %#v got %#v", data.output, res)
		}
	}
}
