#!/bin/bash

INCODEART=quickstart

TOFIX=""

env | grep INCODEREL >/dev/null
if [ $? -ne 0 ]; then
    echo "\$INCODEREL not set!"
    TOFIX="$TOFIX\nexport INCODEREL=1.15.0"
fi

if [ "$TOFIX" != "" ]; then
    echo -e $TOFIX
    exit 1
fi


env | grep INCODE | sort


echo ""
echo ""
echo "mvn clean ..."
mvn clean -o

echo ""
echo ""
echo "removing other non-source files ..."
for a in .project .classpath .settings bin .idea activemq-data neo4j_DB target-ide; do /bin/find . -name $a -exec rm -r {} \;; done
/bin/find . -name "*.log" -exec rm {} \;
/bin/find . -name "pom.xml.*" -exec rm {} \;

echo ""
echo ""
echo "mvn archetype:create-from-project ..."
mvn archetype:create-from-project -o

echo ""
echo ""
echo "groovy script to update archetypes ..."
groovy ../../../scripts/updateGeneratedArchetypeSources.groovy -n $INCODEART -v $INCODEREL

echo ""
echo ""
echo "deleting old archetype ..."
git rm -rf ../../arch/$INCODEART
rm -rf ../../arch/$INCODEART
mkdir -p ../../arch

echo ""
echo ""
echo "adding new archetype ..."
#ls target/generated-sources/archetype
#ls  ../../archetype/$INCODEART

mv target/generated-sources/archetype ../../arch/$INCODEART

pushd ../../arch/$INCODEART
/bin/find . -name "*.iml" -exec rm {} \;
git add .
#git commit -m "$recreating $INCODEART archetype"


echo ""
echo ""
echo "building the newly created archetype ..."
mvn clean install -o

popd