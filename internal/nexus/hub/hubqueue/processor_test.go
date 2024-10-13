package hubqueue

import (
	"testing"
)

// Test function for Sha1 method
func TestGenerateChecksum(t *testing.T) {
	expectedChecksum := "9a8de5804eb9bfdc0321fbd02ea09e588443729a"
	actualChecksum := generateChecksum("foo/bar", "e69e351aa99283867acbbdaa9f530a12c43b1c14")

	// Compare the actual hash to the expected one
	if actualChecksum != expectedChecksum {
		t.Errorf("Expected SHA1 hash %s, but got %s", expectedChecksum, actualChecksum)
	}
}
