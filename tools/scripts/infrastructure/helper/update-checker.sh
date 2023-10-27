#! /bin/bash

logToFileEnabled=true
logFile="/var/log/pi/project6svc.log"
lockFile="/tmp/project6svc.lock"
binaryRoot="/home/pi/operation/project6"

function log() {
	if [[ $logToFileEnabled = true ]]; then
		echo "$(date) $1" >> $logFile
	else 
		echo "$(date) $1"
	fi
}

if [[ ! -f $lockFile ]]; then
	log "Could not find process lock file! Starting service"
    cd $binaryRoot
	./project6svc &
	exit 0
fi

pid=$(cat $lockFile)

# check if process is running
isProcessRunning=$(ps aux | awk '{print $2 }' | grep -w $pid)

# if the process it not running, switch to the new binary and start the service again
if [[ ! -n $isProcessRunning ]]; then
	log "Service is not currently running!"

    cd $binaryRoot

	# if an update file exists, update to the new build
	if [[ -f project6svc_update ]]; then
		log "Updating to new deployment service!"
		rm project6svc
		mv project6svc_update project6svc
	fi

	log "Starting project6 service"
	./project6svc &
fi