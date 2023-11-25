#!/bin/bash
echo "Raspberry Pi Backup Script v1.4"

backupdate=$(date +%Y%m%d)
outputfile="backup.img"
device="/dev/sdb"
bootpartition="/dev/sdb1"
rootpartition="/dev/sdb2"
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
  sudo dd if=$device of=$outputfile bs=1M status=progress
  date -ud "@$SECONDS" "+Time elapsed: %H:%M:%S"
fi

echo "Backup created! You can remove the sdcard now!"

mkdir -p $backupdate
echo "Compressing backup, please wait!"
tar -czvf $backupdate/$outputfile.tar.gz $outputfile

echo "Encrypting backup..."
gpg --output $backupdate/$outputfile.tar.gz.gpg \
    --encrypt \
    --recipient contact@rickiekarp.net \
    --recipient rickie.karp@yandex.com  \
    $backupdate/$outputfile.tar.gz

echo "Removing $outputfile"
rm $outputfile

echo "Removing unencrypted backup $backupdate/$outputfile.tar.gz"
rm $backupdate/$outputfile.tar.gz