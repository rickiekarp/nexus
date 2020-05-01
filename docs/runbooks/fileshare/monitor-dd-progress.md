## 1. first start dd as usual ##
sudo dd if=/dev/sdc of=/tmp/demo.img bs=4M
 
## 2. Open another terminal or tab ##
## 3. find pid of dd command ##
pidof dd              ### &lt;--- say pid is 21145
ps aux | grep -w dd
 
## 4. Run bash/sh while loop as follows ##
while sudo kill -USR1 21145 ; do sleep 10 ; done
 
## 5. Switch back to terminal where dd was started ##