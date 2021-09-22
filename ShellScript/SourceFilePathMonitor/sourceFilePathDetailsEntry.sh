#!/bin/bash
echo -e "\n--------------------------------------------------"
echo -e "\t\t***  Source File Path Details Entry  ***" 
echo -e "--------------------------------------------------\n\n"

secret=`cat secret.txt`

echo "Enter MACHINE TYPE :"
read TYPE

echo "Enter HOST :"
read HOST

echo "Enter USERNAME :"
read USERNAME

echo "Enter PASSWORD :"
read PASSWORD

echo "Enter INTERFACE ID :"
read INTERFACE_ID

echo "Enter SERVER PATH :"
read SERVER_PATH

echo "Enter FILE PATTERN :"
read FILE_PATTERN

echo "Enter TIME INTERVAL (In minutes):"
read TIME_INTERVAL_MIN


pass=`echo $PASSWORD | openssl enc -aes-256-cbc -md sha512 -a -pbkdf2 -iter 100000 -salt -pass pass:$secret`

echo ${TYPE},${HOST},${USERNAME},${pass},${INTERFACE_ID},${SERVER_PATH},${FILE_PATTERN},${TIME_INTERVAL_MIN} >> ./sourceFilePathDetails.csv
