GOCMD=go
GOBUILD=$(GOCMD) build
GOCLEAN=$(GOCMD) clean
GOTEST=$(GOCMD) test
GOGET=$(GOCMD) get
BUILD_PATH=build

buildTree2Yaml:
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 GOARM=7 \
		go build -o $(BUILD_PATH)/tree2yaml tree2yaml/main.go

deployTree2Yaml:
		rsync -rlvpt --delete build/tree2yaml pi:~/tools/
