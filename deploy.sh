#!/bin/bash

ZONE="europe-west1-b"
INSTANCE="hacker-cache-vm"

echo "Copying jar to cloud..."
gcloud compute scp --zone ${ZONE} target/hacker-cache-1.0.0.jar ${INSTANCE}:~/hacker-cache/
if [ $? -ne 0 ]	
then
	echo "Could not copy jar to remote VM. Exiting..." 
	exit 1
fi
echo "Success."

echo "Copying install script to cloud..."
gcloud compute scp --zone ${ZONE} install.sh ${INSTANCE}:~/hacker-cache/
if [ $? -ne 0 ]
then
	echo "Could not copy install script to remote VM. Exiting..." 
	exit 1
fi
echo "Success."

echo "Attempting to ssh into remote VM and run install script";
gcloud compute ssh --zone ${ZONE} ${INSTANCE} --command "cd hacker-cache && ./install.sh"
if [ $? -ne 0 ]
then
	echo "Could not install application remotely. Exiting..." 
	exit 1
fi
echo "Success."

exit 0;
