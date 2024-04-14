package filewatcher

import (
	"bufio"
	"encoding/csv"
	"errors"
	"fmt"
	"io"
	"os"
	"os/exec"
	"strings"
	"syscall"

	"github.com/sirupsen/logrus"
	"gopkg.in/yaml.v2"
)

var eventMap map[string]float64
var outputFile = "log.yaml"

func Start(directory string, outfile string, event string) {
	outputFile = outfile
	load()
	execute("inotifywait -e " + event + " -m -r " + directory + " --csv")
}

func save() {
	yamlFile, err := yaml.Marshal(&eventMap)
	if err != nil {
		logrus.Error(err)
	}

	f, err := os.Create(outputFile)
	if err != nil {
		logrus.Error(err)
	}
	defer f.Close()

	_, err = io.WriteString(f, string(yamlFile))
	if err != nil {
		logrus.Error(err)
	}
}

func load() {
	yamlFile, err := os.ReadFile(outputFile)
	if err != nil {
		logrus.Error(err)
		eventMap = make(map[string]float64)
		return
	}

	err = yaml.Unmarshal(yamlFile, &eventMap)
	if err != nil {
		logrus.Error(err)
		eventMap = make(map[string]float64)
	}
}

func handle(reader *bufio.Reader) error {
	for {
		str, err := reader.ReadString('\n')
		if len(str) == 0 && err != nil {
			if err == io.EOF {
				break
			}
			return err
		}

		str = strings.TrimSuffix(str, "\n")

		// Use strings.NewReader.
		// ... This creates a new Reader for passing to csv.NewReader.
		r := csv.NewReader(strings.NewReader(str))
		// Read all records.
		result, _ := r.ReadAll()

		// make sure we only look at events that have a folder/event/filename
		if len(result[0]) != 3 {
			continue
		}

		// ignore empty filenames
		if len(result[0][2]) == 0 {
			continue
		}

		// filter directories
		if strings.Contains(result[0][1], "ISDIR") {
			continue
		}

		fileName := result[0][2]

		if strings.HasPrefix(fileName, ".") {
			continue
		}

		eventMap[fileName] += 1
		//fmt.Print(fileName + " - " + fmt.Sprintln(eventMap[fileName]))
		save()

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
