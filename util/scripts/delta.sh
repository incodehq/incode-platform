#!/bin/sh

PROD_URL=$1
DEV_URL=$2
USERNAME=$3
PASSWORD=$4

if [ "$PROD_URL" == "" -o "$DEV_URL" == "" -o "$USERNAME" == "" -o "$PASSWORD" == "" ]; then
    echo "usage: full.sh prod_url dev_url username password" >&2
    exit 1
fi

CLASSPATH=$HOME/.m2/repository/com/microsoft/sqlserver/sqljdbc4/4.2-6225/sqljdbc4-4.2-6225.jar
DRIVER=com.microsoft.sqlserver.jdbc.SQLServerDriver


TMPFILE=/tmp/db.changelog.$$.xml


for SCHEMA in `cat schema.txt`
do

    echo "" >&2
    echo "schema: $SCHEMA" >&2
    echo "" >&2

    echo "--"
    echo "--"
    echo "--"
    echo "-- schema: $SCHEMA"
    echo "--"
    echo "--"
    echo "--"

    echo "" >&2
    echo "running diffChangeLog to determine differences between prod and dev ..."    >&2
    echo "('views' are excluded from diffTypes) ..."    >&2
    liquibase --classpath=$CLASSPATH \
            --driver=$DRIVER \
            --url=$PROD_URL \
            --username=$USERNAME \
            --password=$PASSWORD \
            --diffTypes="tables,columns,indexes,foreignkeys,primarykeys,uniqueconstraints" \
        diffChangeLog \
            --referenceUrl=$DEV_URL \
            --referenceUsername=$USERNAME \
            --referencePassword=$PASSWORD \
            --schemas=$SCHEMA \
            --referenceSchemas=$SCHEMA \
            --includeSchema=true \
        > $TMPFILE
            
#            --defaultSchema=$SCHEMA \
#            --referenceDefaultSchema=$SCHEMA \
            
    echo "" >&2
    echo "converting changelog to SQL ..."  >&2
    liquibase --classpath=$CLASSPATH \
            --driver=$DRIVER \
            --url=$DEV_URL \
            --username=$USERNAME \
            --password=$PASSWORD \
            --changeLogFile=$TMPFILE \
        updateSql \
        | grep -v "INSERT INTO" \
        | grep -v "DATABASECHANGELOGLOCK" \
        | grep -v "DATABASECHANGELOG" \
        | grep -v "Database Lock Table" \
        | grep -v "Database Change Log Table" \
        | grep -v "USE \["

    echo "" >&2

done

rm $TMPFILE
    
