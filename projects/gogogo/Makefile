# Go parameters
GOCMD=go
GOBUILD=$(GOCMD) build
GOCLEAN=$(GOCMD) clean
GOTEST=$(GOCMD) test
GOGET=$(GOCMD) get
BUILD_PATH=build
BINARY_NAME=gamesrv
BINARY_UNIX=$(BINARY_NAME)_unix

all: clean test build
build: 
		CGO_ENABLED=0 GOOS=linux GOARCH=arm GOARM=5 go build -o ../module-deployment/service-gobackend/app cmd/gamesrv/main.go
test: 
		$(GOTEST) -v ./...
clean: 
		$(GOCLEAN)
		rm -rf $(BUILD_PATH)
runBuild:
		$(GOBUILD) -o $(BINARY_NAME) -v ./...
		./$(BUILD_PATH)/$(BINARY_NAME)
