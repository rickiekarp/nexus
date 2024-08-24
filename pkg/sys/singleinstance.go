package sys

import (
	"errors"
	"os"
	"strconv"
	"syscall"
	"time"
)

func GetExclusiveLock(lockFile string, timeoutInSeconds int, shouldTryToRestart bool) error {
	_, err := CreateLockFile(lockFile)
	if err != nil {
		// if we can't get an exclusive lock, but the restart flag was set, try to restart the service
		if shouldTryToRestart {
			err := StopRunningServiceForLockFile(lockFile)
			if err != nil {
				return err
			}

			for i := 0; i < timeoutInSeconds+1; i++ {
				_, err = CreateLockFile(lockFile)
				if err != nil {
					if i == timeoutInSeconds {
						return errors.New("timed out acquiring exclusive lock. root cause: " + err.Error())
					}
					time.Sleep(1 * time.Second)

				} else {
					break
				}
			}
		} else {
			// exit execution since we could not get an exclusive lock
			return errors.New("could not get an exclusive lock on " + lockFile)
		}
	}
	return nil
}

// If filename is a lock file, returns the PID of the process locking it
func GetLockFilePid(filename string) (pid int, err error) {
	contents, err := os.ReadFile(filename)
	if err != nil {
		return
	}

	pid, err = strconv.Atoi(string(contents))
	return
}

// CreateLockFile tries to create a file with given name and acquire an
// exclusive lock on it. If the file already exists AND is still locked, it will fail.
func CreateLockFile(filename string) (*os.File, error) {
	file, err := os.OpenFile(filename, os.O_WRONLY|os.O_CREATE, 0600)
	if err != nil {
		return nil, err
	}

	err = syscall.Flock(int(file.Fd()), syscall.LOCK_EX|syscall.LOCK_NB)
	if err != nil {
		file.Close()
		return nil, err
	}

	// Write PID to lock file
	contents := strconv.Itoa(os.Getpid())
	if err := file.Truncate(0); err != nil {
		file.Close()
		return nil, err
	}
	if _, err := file.WriteString(contents); err != nil {
		file.Close()
		return nil, err
	}

	return file, nil
}

// StopRunningServiceForLockFile attempts to stop a running process by reading the pid from the given
// lock file and sending a SIGINT to the running process
func StopRunningServiceForLockFile(lockFile string) error {
	// try to find the process to stop
	pid, err := GetLockFilePid(lockFile)
	if err != nil {
		return err
	}
	process, err := os.FindProcess(pid)
	if err != nil {
		return err
	}

	// send interrupt signal to old process
	err = process.Signal(os.Interrupt)
	if err != nil {
		return err
	}

	return nil
}
