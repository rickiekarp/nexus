#! /bin/bash
echo "Software Install Script for Ubuntu 22.04"
echo "Author: Rickie Karp (contact@rickiekarp.net)"

idx=0

# main option selection
print_options()
{
	echo "1: Easy Install (All options)"
	echo "2: Uninstall redundant software"
	echo "3: Install software"
	echo "4: Install development software"
	echo "5: Install games"
	echo "6: Install extra software (PPA)"
	echo "7: Check for software updates"
	echo "8: Exit"
}

# main option selection
select_option()
{
	echo "Please enter your option:"
	read INDEX

	case $INDEX in
		1) do_all ;;
		2) uninstall_software ;;
		3) install_software ;;
		4) install_dev_software ;;
		5) install_games ;;
		6) install_extra_software ;;
		7) check_for_updates ;;
		8) exit ;;

		*) echo "INVALID NUMBER!" ;;
	esac
}

# uninstalls redundant software
uninstall_software()
{
	echo "Uninstalling not needed software..."
	sudo apt-get --purge remove -y rhythmbox rhythmbox-data librhythmbox-core10

	sudo apt -y autoremove	#removes packages that were installed by other packages and are no longer needed

	check_process_exit
}

# installs software from default repository
install_software()
{
	echo "Installing software..."
	snap install telegram-desktop
	sudo apt install -y chromium-browser steam keepassxc guake bleachbit vlc gimp easytag synaptic vim curl ffmpeg timeshift darktable rawtherapee
	echo "Installing libs..."
	sudo apt install -y libfuse2
	# virtual camera support
	sudo apt install -y v4l2loopback-dkms

	check_process_exit
}

# installs development related software from default repository
install_dev_software()
{
	echo "Installing development software..."
	sudo apt install -y git openjdk-11-jre-headless default-jdk virtualbox virtualbox-qt adb jmeter jmeter-http

	# Install Docker
	sudo apt install -y docker docker.io docker-compose
	sudo usermod -a -G docker $USER

	# Install Visual Studio Code
	snap install --classic code

	check_process_exit
}

# installs games from default repository
install_games()
{
	echo "Installing games..."
	sudo apt install -y warzone2100 warzone2100-music

	check_process_exit
}

# installs software from additional repositories
install_extra_software()
{
	echo "Installing extra software..."
	read -r -p "This option will add additional PPA's to the system. Continue? [y/n]" response
	case $response in 
	   [yY][eE][sS]|[yY]) 
		echo "Adding additional ppa..."
		sudo add-apt-repository ppa:obsproject/obs-studio # OBS
		sudo apt-get update

		echo "Installing OBS"
		sudo apt install -y obs-studio

		echo "All done..."
	        ;;
	   *)
		echo "Exiting..."
 	       ;;
	esac
	check_process_exit
}


# easy install function
do_all()
{
	read -r -p "This option will execute all other listed functions. Continue? [y/n]" response
	case $response in 
	   [yY][eE][sS]|[yY]) 
		let idx=1
		uninstall_software
		check_for_updates
		install_software
		install_dev_software
		install_games
		install_extra_software
		echo "DONE!"
	        ;;
	   *)
		echo "Exiting..."
		process_exit
 	       ;;
	esac
}

# checks the system for updates and installs them
check_for_updates()
{
	echo "Checking for software updates..."
	sudo apt-get update && sudo apt-get -y upgrade
}

# checks $idx variable
# if idx != 1, exit the program
check_process_exit()
{
	if [[ $idx -ne 1 ]]; then
		process_exit
	fi
}

# program exit selection
process_exit()
{
	read -r -p "Do you want to do something else? [y/n]" response
	case $response in 
	   [yY][eE][sS]|[yY]) 
		let idx=0
		print_options
		select_option
	        ;;
	   *)
		echo "DONE!"
 	       ;;
	esac
}

print_options
select_option


