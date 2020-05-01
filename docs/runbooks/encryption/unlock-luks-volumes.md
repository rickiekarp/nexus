# README #

Locking/Unlocking LUKS volumes

### Unlock ###

- udisksctl unlock -b /dev/sdbX
- udisksctl mount -b /dev/dm-X

Example output:
rickie@rickie-GA-MA770T-UD3P:~$ udisksctl unlock -b /dev/sdb1
Passphrase: 
==== AUTHENTICATING FOR org.freedesktop.udisks2.encrypted-unlock-system ===
Authentication is required to unlock the encrypted device Hitachi HUA723020ALA641 (/dev/sdb1)
Authenticating as: rickie,,, (rickie)
Password: 
==== AUTHENTICATION COMPLETE ===
Unlocked /dev/sdb1 as /dev/dm-0.

rickie@rickie-GA-MA770T-UD3P:~$ udisksctl mount -b /dev/dm-0
==== AUTHENTICATING FOR org.freedesktop.udisks2.filesystem-mount ===
Authentication is required to mount /dev/mapper/luks-497e72c1-16d8-42d1-9eac-bef4f01ad6b6
Authenticating as: rickie,,, (rickie)
Password: 
==== AUTHENTICATION COMPLETE ===
Mounted /dev/dm-0 at /media/rickie/HDD.


### Lock ###

- udisksctl unmount -b /dev/dm-X
- udisksctl lock -b /dev/sdbX

Example output:
rickie@rickie-GA-MA770T-UD3P:~$ udisksctl unmount -b /dev/dm-0
Unmounted /dev/dm-0.
rickie@rickie-GA-MA770T-UD3P:~$ udisksctl lock -b /dev/sdb1
Locked /dev/sdb1.
