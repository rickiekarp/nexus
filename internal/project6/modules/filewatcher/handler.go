package filewatcher

import (
	"bufio"
	"errors"
	"fmt"
	"io"
	"os"
	"os/exec"
	"strings"
	"syscall"

	"github.com/sirupsen/logrus"
)

func Start(directory string) {
	execute("inotifywait -e open -m -r " + directory + " --csv")
}

func handle(reader *bufio.Reader) error {
	eventMap := make(map[string]float64)

	for {
		str, err := reader.ReadString('\n')
		if len(str) == 0 && err != nil {
			if err == io.EOF {
				break
			}
			return err
		}

		str = strings.TrimSuffix(str, "\n")
		eventArr := strings.Split(str, ",")
		if len(eventArr) != 3 {
			continue
		}

		fileName := eventArr[2]

		if strings.HasPrefix(fileName, ".") {
			continue
		}

		eventMap[fileName] += 1
		fmt.Print(fileName + " - " + fmt.Sprintln(eventMap[fileName]))

		if err != nil {
			if err == io.EOF {
				break
			}
			return err
		}
	}

	return nil
}

func execute(cmd string) (err error) {
	if cmd == "" {
		return errors.New("no command provided")
	}

	cmdArr := strings.Split(cmd, " ")
	name := cmdArr[0]

	args := []string{}
	if len(cmdArr) > 1 {
		args = cmdArr[1:]
	}

	command := exec.Command(name, args...)
	command.Env = os.Environ()

	stdout, err := command.StdoutPipe()
	if err != nil {
		logrus.Error("Failed creating command stdoutpipe: ", err)
		return err
	}
	defer stdout.Close()
	stdoutReader := bufio.NewReader(stdout)

	stderr, err := command.StderrPipe()
	if err != nil {
		fmt.Println("Failed creating command stderrpipe: ", err)
		return err
	}
	defer stderr.Close()
	stderrReader := bufio.NewReader(stderr)

	if err := command.Start(); err != nil {
		logrus.Error("Failed starting command: ", err)
		return err
	}

	go handle(stdoutReader)
	go handle(stderrReader)

	if err := command.Wait(); err != nil {
		if exiterr, ok := err.(*exec.ExitError); ok {
			if status, ok := exiterr.Sys().(syscall.WaitStatus); ok {
				logrus.Error("Exit Status: ", status.ExitStatus())
				return err
			}
		}
		return err
	}

	return
}
