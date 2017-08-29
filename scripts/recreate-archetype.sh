#!/bin/bash

INCODEART=quickstart

TOFIX=""

env | grep INCODEREL >/dev/null
if [ $? -ne 0 ]; then
    echo "\$INCODEREL not set!"
    TOFIX="$TOFIX\nexport INCODEDEV=1.15.0"
fi

if [ "$TOFIX" != "" ]; then
    echo -e $TOFIX
    exit 1
fi


#
#
#
env | grep INCODE | sort


echo "mvn clean ..."
mvn clean

echo "removing other non-source files ..."
for a in .project .classpath .settings bin .idea neo4j_DB target-ide; do /bin/find . -name $a -exec rm -r {} \;; done
/bin/find . -name "*.iml" -exec rm {} \;
/bin/find . -name "*.log" -exec rm {} \;
/bin/find . -name "pom.xml.*" -exec rm {} \;

echo "mvn archetype:create-from-project ..."
mvn archetype:create-from-project -o

echo "groovy script to update archetypes ..."
groovy ../../../scripts/updateGeneratedArchetypeSources.groovy -n $INCODEART -v $INCODEREL

echo "deleting old archetype ..."
git rm -rf ../../archetype/$INCODEART
rm -rf ../../archetype/$INCODEART
mkdir -p ../../archetype


echo "adding new archetype ..."
ls target/generated-sources/archetype 
ls  ../../archetype/$INCODEART

mv target/generated-sources/archetype ../../archetype/$INCODEART
git add ../../archetype/$INCODEART
git commit -m "$recreating $INCODEART archetype"


echo "building the newly created archetype ..."
cd ../../archetype/$INCODEART
mvn clean install
