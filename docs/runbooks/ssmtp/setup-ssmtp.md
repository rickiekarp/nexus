# README #

Set up mails from raspberry pi

### How To ###

Install ssmtp
```
sudo apt install ssmtp
```

Edit /etc/ssmtp/ssmtp.conf config. It needs to include this:
```
root=postmaster
mailhub=smtp.gmail.com:587
hostname=raspberrypi
AuthUser=AGmailUserName@gmail.com
AuthPass=TheGmailPassword
FromLineOverride=YES
UseSTARTTLS=YES
```
