#!/bin/bash
echo "Raspberry Pi Backup Script v1.3"

backupdate=$(date +%Y%m%d)
outputfile="backup_$backupdate.img"
device="/dev/mmcblk0"
bootpartition="/dev/mmcblk0p1"
rootpartition="/dev/mmcblk0p2"
echo "Backing up partitions: $bootpartition, $rootpartition"

echo "Unmounting boot partition..."
sudo umount $bootpartition
echo "Unmounting root partition..."
sudo umount $rootpartition

#backup
if df -h | grep $device
then
  echo "Device $device was not unmounted correctly"
else
  echo "Backing up! Please wait..."
  sudo dd if=$device of=$outputfile bs=1M
fi

echo "Backup created! You can remove the sdcard now!"

echo "Compressing backup, please wait!"
tar -czvf $outputfile.tar.gz $outputfile

echo "Removing $outputfile"
rm $outputfile