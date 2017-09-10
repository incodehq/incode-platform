#!/usr/bin/env bash
if [ "$1" = "" ]; then
	MSG=`git log -1 --pretty=%B`
else
	MSG=$1
fi
SHA_ID=`git rev-parse --verify HEAD`
echo $MSG
mvn clean 
mvn install -f pom-pdf.xml
mvn install -D message="$MSG (commit: $SHA_ID)"
