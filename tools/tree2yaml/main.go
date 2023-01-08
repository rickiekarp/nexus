package main

import (
	"crypto/md5"
	"flag"
	"fmt"
	"io"
	"log"
	"os"
	"path"
	"path/filepath"
	"time"

	"gopkg.in/yaml.v2"
)

var rootDir string
var flagCalcMd5 *bool

func main() {

	flagCalcMd5 = flag.Bool("calcmd5", false, "calculate md5 sum of each file")
	flag.Parse()

	if flag.Args()[0] == "" {
		os.Exit(1)
	}

	rootDir = flag.Args()[0]

	tree := BuildTree(rootDir)

	yamlData, err := yaml.Marshal(&tree)

	if err != nil {
		fmt.Printf("Error while Marshaling. %v", err)
		os.Exit(1)
	}

	fmt.Println(string(yamlData))
}

type FileTree struct {
	RootDir      string    `yaml:"directory"`
	CreationDate time.Time `yaml:"created"`
	Tree         *Folder
}

type Folder struct {
	Name    string             `yaml:"-"`
	Files   []*File            `yaml:"files,omitempty"`
	Folders map[string]*Folder `yaml:"folders,omitempty"`
}

type File struct {
	Name string
	Size int64
	Md5  string `yaml:"md5,omitempty"`
}

func (f *Folder) String() string {
	j, _ := yaml.Marshal(f)
	return string(j)
}

func BuildTree(dir string) *FileTree {
	dir = path.Clean(dir)

	var filetree *FileTree = &FileTree{}
	var tree *Folder
	var nodes = map[string]interface{}{}
	var walkFunc filepath.WalkFunc = func(p string, info os.FileInfo, err error) error {
		if info.IsDir() {
			nodes[p] = &Folder{
				path.Base(p),
				[]*File{},
				map[string]*Folder{},
			}
		} else {
			var md5 = ""
			if *flagCalcMd5 {
				md5 = calcMd5(p)
			}
			nodes[p] = &File{
				Name: path.Base(p),
				Size: info.Size(),
				Md5:  md5,
			}
		}
		return nil
	}

	err := filepath.Walk(dir, walkFunc)
	if err != nil {
		log.Fatal(err)
	}

	for key, value := range nodes {
		var parentFolder *Folder
		if key == dir {
			tree = value.(*Folder)
			continue
		} else {
			parentFolder = nodes[path.Dir(key)].(*Folder)
		}

		switch v := value.(type) {
		case *File:
			parentFolder.Files = append(parentFolder.Files, v)
		case *Folder:
			parentFolder.Folders[v.Name] = v
		}
	}

	filetree.RootDir = rootDir
	filetree.CreationDate = time.Now()
	filetree.Tree = tree

	return filetree
}

func calcMd5(filePath string) string {
	file, err := os.Open(filePath)

	if err != nil {
		panic(err)
	}

	defer file.Close()

	hash := md5.New()
	_, err = io.Copy(hash, file)

	if err != nil {
		panic(err)
	}

	return fmt.Sprintf("%x", hash.Sum(nil))
}
