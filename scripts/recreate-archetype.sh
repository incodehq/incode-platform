#!/bin/bash

RELEASE_VERSION=$1
shift

if [ ! "$RELEASE_VERSION"  ]; then
    echo "usage: $(basename $0) [release_version]" >&2
    exit 1
fi



INCODEART=quickstart



echo ""
echo "checking no reference to isis.version of -SNAPSHOT"
echo ""
grep SNAPSHOT pom.xml | grep isis.version
if [ $? == 0 ]; then
    echo ""
    echo "... failed" >&2
    exit 1
fi


echo ""
echo "checking reference to incode-platform.version matches provided release_version"
echo ""
grep "<incode-platform.version>$RELEASE_VERSION</incode-platform.version>" pom.xml >/dev/null
if [ $? != 0 ]; then
    echo ""
    echo "... failed" >&2
    exit 1
fi



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
groovy ../../../scripts/updateGeneratedArchetypeSources.groovy

echo ""
echo ""
echo "deleting old archetype ..."
git rm -rf ../../arch/$INCODEART
rm -rf ../../arch/$INCODEART
mkdir -p ../../arch

echo ""
echo ""
echo "adding new archetype ..."

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