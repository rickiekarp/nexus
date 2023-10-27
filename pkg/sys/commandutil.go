package sys

import "os/exec"

// CommandExists tries to look up the given cmd in the system
// Returns true if the given command exists, false otherwise
func CommandExists(cmd string) (bool, error) {
	_, err := exec.LookPath(cmd)
	return err == nil, err
}
