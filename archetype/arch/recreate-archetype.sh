#!/bin/bash

#
# intended to be run from the ex/app/quickstart directory.
#

RELEASE_VERSION=$1
shift

if [ ! "$RELEASE_VERSION"  ]; then
    echo "usage: $(basename $0) [release_version]" >&2
    exit 1
fi



INCODEART=quickstart



echo ""
echo "checking no isis.version reference to -SNAPSHOT"
echo ""
grep SNAPSHOT pom.xml | grep isis.version
if [ $? == 0 ]; then
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
echo "Workaround: temporarily renaming Dockerfile"
mv webapp/src/main/resources/docker/Dockerfile webapp/src/main/resources/docker/Dockerfile.txt

echo ""
echo ""
echo "mvn archetype:create-from-project ..."
mvn archetype:create-from-project -o

echo ""
echo ""
echo "Renames Dockerfile in ex/app"
mv webapp/src/main/resources/docker/Dockerfile.txt webapp/src/main/resources/docker/Dockerfile

echo ""
echo ""
echo "Renames Dockerfile in generated-sources"
mv target/generated-sources/archetype/src/main/resources/archetype-resources/webapp/src/main/resources/docker/Dockerfile.txt target/generated-sources/archetype/src/main/resources/archetype-resources/webapp/src/main/resources/docker/Dockerfile


echo ""
echo ""
echo "groovy script to update archetypes ..."
groovy ../../arch/_scripts/updateGeneratedArchetypeSources.groovy

echo ""
echo ""
echo "deleting old archetype ..."
git rm -rf ../../arch/$INCODEART
rm -rf ../../arch/$INCODEART

echo ""
echo ""
echo "adding new archetype ..."

mv target/generated-sources/archetype ../../arch/$INCODEART

pushd ../../arch/$INCODEART
/bin/find . -name "*.iml" -exec rm {} \;
git add .


echo ""
echo ""
echo "building the newly created archetype ..."
mvn clean install -o

popd