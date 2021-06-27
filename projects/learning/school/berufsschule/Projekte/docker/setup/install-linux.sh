#! /bin/bash
echo "Docker Install Script for Ubuntu 16.04 Based Systems"
echo "Version: v1.1"
echo "Author: Rickie Karp"

#prints options to select
print_options()
{
	echo "1: Install Docker on Ubuntu"
	echo "2: Install Docker on Zentyal"
	echo "3: Run Opencart (Webshop) container"
	echo "8: Test Docker installation"
	echo "9: Exit"
}

#select an option to execute
select_option()
{
	echo "Please enter your option:"
	read INDEX

	case $INDEX in
		1) dockerinstall_ubuntu ;;
		2) dockerinstall_zentyal ;;
		3) dockerrun_webshop ;;
        8) dockertest ;;
		9) exit ;;
		*) echo "INVALID NUMBER!" ;;
	esac
}

#install docker on ubuntu
dockerinstall_ubuntu()
{
	read -r -p "This option will install Docker on your system. Continue? [y/n]" response
	case $response in 
	   [yY][eE][sS]|[yY]) 
		let idx=1
		echo "Installing docker..."
        
        sudo apt-get -y install apt-transport-https ca-certificates curl
        curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
        sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
        sudo apt-get update
        sudo apt-get -y install docker-ce docker-compose
		echo "DONE!"
	        ;;
	   *)
		echo "Exiting..."
 	       ;;
	esac
}

#install docker on zentyal 5.0
dockerinstall_zentyal()
{
	read -r -p "This option will install Docker on your system. Continue? [y/n]" response
	case $response in 
	   [yY][eE][sS]|[yY]) 
		let idx=1
		echo "Installing docker..."
        
        #instal local system dependencies
        sudo dpkg --install $PWD/linux/software-properties-common_0.96.24.7_all.deb
        sudo dpkg --install $PWD/linux/python3-software-properties_0.96.24.7_all.deb

        #resolve dependency issues
        sudo apt-get -f -y install

        #install docker dependencies
        sudo apt-get -y install apt-transport-https ca-certificates curl
        
        curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

        sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
        sudo apt-get update
        sudo apt-get -y install docker-ce docker-compose
		echo "DONE!"
	        ;;
	   *)
		echo "Exiting..."
 	       ;;
	esac
}

#runs the webshop container
dockerrun_webshop()
{
    # stop apache if it is running
    sudo service apache2 stop
    # start the docker container
	sudo docker-compose -f $PWD/container/opencart/docker-compose.yml up
}

#tests the docker installation
dockertest()
{
	echo "Testing docker installation..."
	sudo docker run hello-world
}


print_options
select_option


