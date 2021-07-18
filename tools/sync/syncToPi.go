package main

import (
	"fmt"

	"git.rickiekarp.net/rickie/home/pkg/command"
)

func main() {
	fmt.Println("Copying applications")
	command.Shell("rsync -rlvpt service-tomcat/webapps/*.war pi:~/projects/module-deployment/service-tomcat/webapps/")
	command.Shell("rsync -rlvpt --delete values/homebackend/prod/setup/ pi:~/projects/module-deployment/service-tomcat/setup/")

	fmt.Println("Copying go apps")
	command.Shell("rsync -rlvpt service-gobackend/app pi:~/projects/module-deployment/service-gobackend/")
	command.Shell("rsync -rlvpt --delete values/gobackend/prod/conf pi:~/projects/module-deployment/service-gobackend/")

	fmt.Println("Copying nginx files")
	command.Shell("rsync -rlvpt --delete service-nginx/html/ pi:~/projects/module-deployment/service-nginx/html/")
}
