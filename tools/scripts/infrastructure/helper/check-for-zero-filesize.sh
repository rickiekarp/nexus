#!/bin/bash
# USAGE:
# bash git/homesrv/tools/scripts/helper/check-for-zero-filesize.sh /home/$USER/foo *.tar.xz

echo "Checking for files with filesize = 0"

path="$1"
extension="$2"

if [ ! -d "$path" ]; then
  echo "Path $path does not exist! Exiting..."
  exit 1
fi

for file in $(find $path -name $extension);
do
	if [ $(ls -la $file | awk '{print $5}') -eq "0" ]; then
	  echo "FOUND: $file";
	fi
done