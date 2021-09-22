#!/bin/bash
date=`date +"%Y-%m-%d"`
dateTime=`date +"%Y-%m-%d_%H-%M-%S"`

reportFileCnt=`ls ./Report/*.txt 2>/dev/null | grep $date | wc -l`

if [ $reportFileCnt -gt 0 ]
then
   reportFile=`ls ./Report/*.txt 2>/dev/null | grep $date | tail -n 1`
   echo -e "\n" >> $reportFile
else
   reportFile=`echo "./Report/report_$dateTime.txt"`
   echo -e "\n                                     ===== SOURCE FILE PATH STATUS REPORT =====                                     " >> $reportFile
fi


echo -e "\n*** Run Time : $dateTime ***\n" >> $reportFile

exec < ./sourceFilePathDetails.csv
read header
while read line
do
   
   secret=`cat secret.txt`

   machineType=`echo "${line}" | cut -d',' -f1`

   host=`echo "${line}" | cut -d',' -f2`

   userName=`echo "${line}" | cut -d',' -f3`

   passWord=`echo "${line}" | cut -d',' -f4`

   interfaceId=`echo "${line}" | cut -d',' -f5`

   serverPath=`echo "${line}" | cut -d',' -f6`

   filePattern=`echo "${line}" | cut -d',' -f7`

   timeIntervalMin=`echo "${line}" | cut -d',' -f8`

   errorFlag='N'
   resultContent=''
   result=''
   decryptPass=''
   scriptReplaced=''
   status=0


   if [ $machineType == "Linux" ] 
   then
        scriptName='Linux_Script.sh'

   elif [ $machineType == "Windows" ]
   then
        scriptName='Windows_Script.sh'
   fi
   
   
   scriptContent=`sed '$!s/$/;/' ./Script/$scriptName`
   script=${scriptContent#*;}

   scriptReplaced=`echo $script | sed "s|SERVER_PATH|${serverPath}|g; s|TIME_INTERVAL_MIN|${timeIntervalMin}|g; s|FILE_PATTERN|${filePattern}|g"`


   echo -e "\n\nSOURCE DIRECTORY:" $serverPath >> $reportFile
   echo -e "\nFILE NAME(S):" >> $reportFile

   decryptPass=`echo $passWord | openssl enc -aes-256-cbc -md sha512 -a -d -pbkdf2 -iter 100000 -salt -pass pass:$secret 2>&1`
   status=$?
   
   

   if [[ $status == 1 ]] 
   then     
         echo "Password retrieval for interface id : ${interfaceId} resulted in failure." >> $reportFile
         errorFlag='Y'
          
   else
        status=0
        tmp_pass=$(mktemp ./tmp.XXX)
        echo $decryptPass > tmp_pass
        resultContent=`sshpass -f tmp_pass ssh -no StrictHostKeyChecking=no $userName@$host "${scriptReplaced}" 2>&1`
        status=$?
        rm $tmp_pass
        rm -f tmp_pass
   fi

   
   if [[ $resultContent == *"No such file or directory"* ]]
   then
       resultContent=`echo ${resultContent[@]:5}`
       echo -e "\t\t\tNo file found. \n\t\t\t"$resultContent"\n" >> $reportFile
   elif [[ $errorFlag == "Y" ]]
   then
       echo ""
   elif [[ status == 1 ]]
   then
       echo "Source path status retrieval for interface id : ${interfaceId} resulted in failure. Please check the password or source file permission." >> $reportFile
   else
       result=`echo $resultContent | sed "s|[.][/]||g;"`

       echo -e "\t\t\t"$result"\n" >> $reportFile
   fi
   
done

echo "......................................................................." >> $reportFile

echo "Script Run Completed !!"


