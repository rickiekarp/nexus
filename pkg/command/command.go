package command

import (
	"bytes"
	"fmt"
	"log"
	"os"
	"os/exec"
	"syscall"

	"github.com/sirupsen/logrus"
)

const ShellToUse = "bash"

func Shell(command string) {
	var stdout bytes.Buffer
	var stderr bytes.Buffer
	cmd := exec.Command(ShellToUse, "-c", command)
	cmd.Stdout = &stdout
	cmd.Stderr = &stderr
	err := cmd.Run()
	if err != nil {
		log.Printf("error: %v\n", err)
	}

	if stdout.String() != "" {
		fmt.Println("--- stdout ---")
		fmt.Println(stdout.String())
	}

	if stderr.String() != "" {
		fmt.Println("--- stderr ---")
		fmt.Println(stderr.String())
	}

}

func Shellout(command string) (error, string, string) {
	var stdout bytes.Buffer
	var stderr bytes.Buffer
	cmd := exec.Command(ShellToUse, "-c", command)
	cmd.Stdout = &stdout
	cmd.Stderr = &stderr
	err := cmd.Run()
	return err, stdout.String(), stderr.String()
}

// ExecuteCmd executes a given system command
// It returns the exitCode of the executed command and the Stdout AND Stderr buffer as a string
func ExecuteCmd(command string, args ...string) (string, string, int) {

	var stdout bytes.Buffer
	var stderr bytes.Buffer

	cmd := exec.Command(command, args...)
	cmd.Stdout = &stdout
	cmd.Stderr = &stderr

	err := cmd.Start()
	if err != nil {
		log.Printf("cmd.Start() failed with '%s'\n", err)
		return "", "", 1
	}

	err = cmd.Wait()
	if err != nil {
		log.Printf("cmd.Run() failed with %s\n", err)

		if exiterr, ok := err.(*exec.ExitError); ok {
			// The program has exited with an exit code != 0

			// This works on both Unix and Windows. Although package
			// syscall is generally platform dependent, WaitStatus is
			// defined for both Unix and Windows and in both cases has
			// an ExitStatus() method with the same signature.
			if status, ok := exiterr.Sys().(syscall.WaitStatus); ok {
				log.Printf("Exit Status: %d", status.ExitStatus())
				return stdout.String(), stderr.String(), status.ExitStatus()
			}
		}
	}

	return stdout.String(), stderr.String(), 0
}

// ExecuteCommandAndGetExitCode is deprecated! Use Shell() instead
func ExecuteCommandAndGetExitCode() int {
	cmd := exec.Command("ls", "/foo/bar")
	var waitStatus syscall.WaitStatus
	if err := cmd.Run(); err != nil {
		if err != nil {
			os.Stderr.WriteString(fmt.Sprintf("Error: %s\n", err.Error()))
		}
		if exitError, ok := err.(*exec.ExitError); ok {
			waitStatus = exitError.Sys().(syscall.WaitStatus)
			//fmt.Printf("Output: %s\n", []byte(fmt.Sprintf("%d", waitStatus.ExitStatus())))
		}
	} else {
		// Success
		waitStatus = cmd.ProcessState.Sys().(syscall.WaitStatus)
		//fmt.Printf("Output: %s\n", []byte(fmt.Sprintf("%d", waitStatus.ExitStatus())))
	}
	return waitStatus.ExitStatus()
}

// ExecuteCommandAndPrintResult is deprecated! Use Shell() instead
func ExecuteCommandAndPrintResult(command string, arguments string) {
	cmd := exec.Command(command, arguments)
	cmdOutput := &bytes.Buffer{}
	cmd.Stdout = cmdOutput
	err := cmd.Run()
	if err != nil {
		os.Stderr.WriteString(err.Error())
	}
	fmt.Print(string(cmdOutput.Bytes()))
}

func ExecuteCmdAndGetOutput(command string, args ...string) string {
	out, err := exec.Command(command, args...).Output()
	if err != nil {
		logrus.Error(err)
	}
	return string(out)
}
