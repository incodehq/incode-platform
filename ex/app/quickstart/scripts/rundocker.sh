#!/usr/bin/env bash
if [ $# -lt 2 ]; then
    echo "usage: `basename $0` [image] [isis.properties]" >&2
    exit 1
fi

IMAGE=$1
shift
CONFIGFILE=$1
shift

COUNT=`docker image ls $IMAGE | wc -l`
if [ "$COUNT" -eq "1" ]; then
    echo "usage: `basename $0` - cannot find image '$IMAGE'"
    exit 1
fi

TMPFILE=/tmp/1.$$

firstTime=1
for line in `cat $CONFIGFILE | grep -v ^# | grep -v ^$`
do
    if [ $firstTime -eq 1 ]; then
        echo "X=\"$line\"" >> $TMPFILE
        firstTime=0
    else
        echo "X=\"\${X}||$line\"" >> $TMPFILE
    fi
done


echo "echo \$X" >>$TMPFILE

export ISIS_OPTS=`eval $TMPFILE `

rm $TMPFILE

echo docker run $* -e ISIS_OPTS=$ISIS_OPTS $IMAGE
docker run $* -e ISIS_OPTS=$ISIS_OPTS $IMAGE
