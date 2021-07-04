# Go parameters
GOCMD=go
GOBUILD=$(GOCMD) build
GOCLEAN=$(GOCMD) clean
GOTEST=$(GOCMD) test
GOGET=$(GOCMD) get
BUILD_PATH=build
BINARY_NAME=app

buildSysmonAmd64: 
		CGO_ENABLED=0 GOOS=linux GOARCH=amd64 \
		go build -ldflags="-X main.Version=$(shell git rev-parse HEAD)" \
		-o $(BUILD_PATH)/output/sysmon \
		cmd/sysmon/main.go
		cp -r projects/module-deployment/values/sysmon/prod/* $(BUILD_PATH)/output/
		cp build/docker/Dockerfile_sysmon build/output/Dockerfile

buildSysmonARM64v7: 
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 GOARM=7 \
		go build -ldflags="-X main.Version=$(shell git rev-parse HEAD)" \
		-o $(BUILD_PATH)/output/sysmon \
		cmd/sysmon/main.go
		cp -r projects/module-deployment/values/sysmon/prod/* $(BUILD_PATH)/output/
		cp build/docker/Dockerfile_sysmon build/output/Dockerfile

build: 
		GOOS=linux GOARCH=amd64 \
		go build -ldflags="-X main.Version=$(shell git rev-parse HEAD)" \
		-o $(BUILD_PATH)/$(BINARY_NAME) \
		cmd/snakesrv/main.go

buildARMv5: 
		CGO_ENABLED=0 GOOS=linux GOARCH=arm GOARM=5 \
		go build -ldflags="-X main.Version=$(shell git rev-parse HEAD)" \
		-o $(BUILD_PATH)/$(BINARY_NAME) \
		cmd/snakesrv/main.go

buildARM64v5: 
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 GOARM=5 \
		go build -ldflags="-X main.Version=$(shell git rev-parse HEAD)" \
		-o $(BUILD_PATH)/$(BINARY_NAME) \
		cmd/snakesrv/main.go

test: 
		$(GOTEST) -v ./...

clean: 
		$(GOCLEAN)
		rm -rf $(BUILD_PATH)

runBuild:
		$(GOBUILD) -o $(BINARY_NAME) -v ./...
		./$(BUILD_PATH)/$(BINARY_NAME)

copyBuildFolder:
		cp $(BUILD_PATH)/* ../module-deployment/service-gobackend 