package main

import (
	"flag"
	"fmt"
	"os"

	"git.rickiekarp.net/rickie/home/tools/tree2yaml/generator"
	"gopkg.in/yaml.v2"
)

var rootDir string
var flagCalcMd5 *bool

func main() {

	flagCalcMd5 = flag.Bool("calcMd5", false, "calculate md5 sum of each file")
	flag.Parse()

	if flag.Args()[0] == "" {
		os.Exit(1)
	}

	rootDir = flag.Args()[0]

	tree := generator.BuildTree(rootDir, flagCalcMd5)

	data, err := yaml.Marshal(&tree)

	if err != nil {
		fmt.Printf("Error while Marshaling. %v", err)
		os.Exit(1)
	}

	fmt.Println(string(data))
}
