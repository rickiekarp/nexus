package main

import (
	"flag"
	"fmt"
	"log"
	"os"
	"path"
	"path/filepath"
	"sort"

	"git.rickiekarp.net/rickie/home/tools/tree2yaml/hash"
	"git.rickiekarp.net/rickie/home/tools/tree2yaml/model"
	"git.rickiekarp.net/rickie/home/tools/tree2yaml/sorting"
	"gopkg.in/yaml.v2"
)

var Version = "development" // Version set during go build using ldflags

var rootDir string
var flagCalcMd5 *bool

func main() {

	flagCalcMd5 = flag.Bool("calcMd5", false, "calculate md5 sum of each file")
	flag.Parse()

	if flag.Args()[0] == "" {
		os.Exit(1)
	}

	rootDir = flag.Args()[0]

	tree := BuildTree(rootDir)

	data, err := yaml.Marshal(&tree)

	if err != nil {
		fmt.Printf("Error while Marshaling. %v", err)
		os.Exit(1)
	}

	fmt.Println(string(data))
}

func BuildTree(dir string) *model.FileTree {
	dir = path.Clean(dir)

	var filetree *model.FileTree = &model.FileTree{}
	var tree *model.Folder
	var nodes = map[string]interface{}{}
	var walkFunc filepath.WalkFunc = func(p string, info os.FileInfo, err error) error {
		if info.IsDir() {
			nodes[p] = &model.Folder{
				Name:    path.Base(p),
				Files:   []*model.File{},
				Folders: []*model.Folder{},
			}
		} else {
			var md5 = ""
			if *flagCalcMd5 {
				md5 = hash.CalcMd5(p)
			}

			filetree.Size += info.Size()

			nodes[p] = &model.File{
				Name:         path.Base(p),
				Size:         info.Size(),
				LastModified: info.ModTime(),
				Md5:          md5,
			}
		}

		return nil
	}

	err := filepath.Walk(dir, walkFunc)
	if err != nil {
		log.Fatal(err)
	}

	for key, value := range nodes {
		var parentFolder *model.Folder
		if key == dir {
			tree = value.(*model.Folder)
			continue
		} else {
			parentFolder = nodes[path.Dir(key)].(*model.Folder)
		}

		switch v := value.(type) {
		case *model.File:
			parentFolder.Files = append(parentFolder.Files, v)
			sort.Sort(sorting.ByName(parentFolder.Files))
		case *model.Folder:
			parentFolder.Folders = append(parentFolder.Folders, v)
			sort.Sort(sorting.ByFolderName(parentFolder.Folders))
		}
	}

	filetree.RootDir = rootDir
	filetree.ParserVersion = Version
	filetree.Tree = tree

	return filetree
}
