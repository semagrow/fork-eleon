#!/bin/sh

UNAME=$(uname)

if [[ x${UNAME} -eq "xDarwin" ]]; then
	echo "MACOS"
	JVM_PARAMS="-mx512M -d64 -XstartOnFirstThread"
elif [[ x${UNAME} -eq "xLinux" ]]; then
	echo "Linux"
	JVM_PARAMS="-mx512M"
else
	echo "Other"
fi	

exec java $JVM_PARAMS -jar eleon.jar

