#!/usr/bin/env bash
set -eo pipefail

PROCESS_TYPE="none"

function printUsage() {
    echo "Backup Script (2020.04)"
    echo "Usage: $0 --backup"
}

while [[ "$1" != "" ]]; do
    case $1 in
        --backup )          PROCESS_TYPE="backup"
                            ;;
        -h | --help )       printUsage
                            exit 0
                            ;;
        * )                 echo "Invalid parameter!"
                            printUsage
                            exit 1
    esac
    shift
done

function backupFiles() {
  user="$USER"
  workdir=`pwd`
  currentDate=$(date +%Y%m%d)
  echo "User name: $user"
  echo "Working directory: $workdir"
  echo "Date: $currentDate"

  echo ""
  echo "Preparing backup: $target"
  tar -zcvf ~/$currentDate.tar.xz ~/.ssh ~/.gitconfig ~/.bashrc ~/.profile
}

case $PROCESS_TYPE in
    "none" )                echo "No action defined! Please add the parameter --backup"
                            usage
                            exit 1
                            ;;
    "backup" )              backupFiles
                            ;;
    * )                     echo "Invalid action! ($PROCESS_TYPE)"
                            exit 1
esac
