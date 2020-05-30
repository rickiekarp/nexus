# Go parameters
GOCMD=go
GOBUILD=$(GOCMD) build
GOCLEAN=$(GOCMD) clean
GOTEST=$(GOCMD) test
GOGET=$(GOCMD) get
BUILD_PATH=build
BINARY_NAME=app
BINARY_VERSION=$(shell date +%y.%m)
DEPLOYMENT_PATH=../module-deployment/service-gobackend

all: clean test build

build: 
		GOOS=linux GOARCH=amd64 \
		go build -ldflags="-X main.Version=$(BINARY_VERSION)" \
		-o $(BUILD_PATH)/$(BINARY_NAME) \
		cmd/gamesrv/main.go

buildARMv5: 
		CGO_ENABLED=0 GOOS=linux GOARCH=arm GOARM=5 \
		go build -ldflags="-X main.Version=$(BINARY_VERSION)" \
		-o $(BUILD_PATH)/$(BINARY_NAME) \
		cmd/gamesrv/main.go

buildARM64v5: 
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 GOARM=5 \
		go build -ldflags="-X main.Version=$(BINARY_VERSION)" \
		-o $(BUILD_PATH)/$(BINARY_NAME) \
		cmd/gamesrv/main.go

test: 
		$(GOTEST) -v ./...

clean: 
		$(GOCLEAN)
		rm -rf $(BUILD_PATH)

runBuild:
		$(GOBUILD) -o $(BINARY_NAME) -v ./...
		./$(BUILD_PATH)/$(BINARY_NAME)

copyBuildFolder:
		cp $(BUILD_PATH)/* $(DEPLOYMENT_PATH) 