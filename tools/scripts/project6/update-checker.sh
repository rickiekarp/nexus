#! /bin/bash

lockFile="/tmp/project6svc.lock"
binaryRoot="/home/rickie/Programs/Dev/project6"
project6LogFile="/var/log/project6.log"

if [[ ! -f $lockFile ]]; then
   	cd $binaryRoot
	./project6svc -log=$project6LogFile -host=rickiekarp.net:12000 &
	exit 0
fi

pid=$(cat $lockFile)

# check if process is running
isProcessRunning=$(ps aux | awk '{print $2 }' | grep -w $pid)

# if the process it not running, switch to the new binary and start the service again
if [[ ! -n $isProcessRunning ]]; then
    cd $binaryRoot

	# if an update file exists, update to the new build
	if [[ -f project6svc_update ]]; then
		rm project6svc
		mv project6svc_update project6svc
		chmod +x project6svc
	fi

	./project6svc -log=$project6LogFile -host=rickiekarp.net:12000 &
fi