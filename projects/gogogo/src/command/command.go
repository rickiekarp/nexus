package command

import (
	"bytes"
	"fmt"
	"log"
	"os"
	"os/exec"
	"syscall"
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
