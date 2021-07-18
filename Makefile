GOCMD=go
GOBUILD=$(GOCMD) build
GOCLEAN=$(GOCMD) clean
GOTEST=$(GOCMD) test
GOGET=$(GOCMD) get
BUILD_PATH=build
BINARY_NAME=app

buildSysmonAmd64: 
		CGO_ENABLED=0 GOOS=linux GOARCH=amd64 \
		go build -ldflags="-X main.Version=$(shell git rev-parse HEAD) -X main.ConfigBaseDir=config/" \
		-o $(BUILD_PATH)/output/sysmon \
		cmd/sysmon/main.go
		cp -r projects/module-deployment/values/sysmon/prod/* $(BUILD_PATH)/output/
		cp deployments/docker/Dockerfile_sysmon build/output/Dockerfile

buildSysmonARM64v7: 
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 GOARM=7 \
		go build -ldflags="-X main.Version=$(shell git rev-parse HEAD) -X main.ConfigBaseDir=config/" \
		-o $(BUILD_PATH)/output/sysmon/app \
		cmd/sysmon/main.go
		cp -r projects/module-deployment/values/sysmon/prod/* $(BUILD_PATH)/output/sysmon/
		cp deployments/docker/Dockerfile_goscratch build/output/sysmon/Dockerfile

deploySysmon:
		rsync -rlvpt --delete build/output/sysmon pi:~/apps/

buildMailServiceARM64v7: 
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 GOARM=7 \
		go build -ldflags="-X main.Version=$(shell git rev-parse HEAD) -X main.ConfigBaseDir=config/" \
		-o $(BUILD_PATH)/output/mailsvc/app \
		cmd/services/mailsvc/main.go
		cp -r projects/module-deployment/values/services/mailsvc/prod/* $(BUILD_PATH)/output/mailsvc/
		cp deployments/docker/Dockerfile_goscratch build/output/mailsvc/Dockerfile

deployMailService:
		rsync -rlvpt --delete build/output/mailsvc pi:~/apps/

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