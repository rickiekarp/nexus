package stalinsort

// I came up with a single pass O(n) sort algorithm I call StalinSort.
// You iterate down the list of elements checking if they're in order.
// Any element which is out of order is eliminated. At the end you have a sorted list.
func Sort(sliceToSort []int) []int {
	if len(sliceToSort) == 0 {
		return []int{}
	}
	sortedSlice := sliceToSort[:1]
	for i := 1; i < len(sliceToSort); i++ {
		if sliceToSort[i] >= sortedSlice[len(sortedSlice)-1] {
			sortedSlice = append(sortedSlice, sliceToSort[i])
		}
	}
	return sortedSlice
}
