#!/bin/sh

MACOS_JAVA6_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/1.6.0/Home

# check for MacOS, where JRE 6 is at a strange place
if [[ -d ${MACOS_JAVA6_HOME} ]]; then
	export PATH=${MACOS_JAVA6_HOME}/bin:$PATH
fi	

exec java -mx512M -jar eleon.jar
