#!/usr/bin/env bash

#
# intended to be run from the ex/arch directory.
#


RELEASE_VERSION=$1
shift

if [ ! "$RELEASE_VERSION"  ]; then
    echo "usage: $(basename $0) [release_version]" >&2
    exit 1
fi


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
echo "sanity check (mvn clean install -T1C -o -DskipTests) "
echo ""
mvn clean install -T1C -o -DskipTests >/dev/null
if [ $? != 0 ]; then
    echo "... failed" >&2
    exit 1
fi




echo ""
echo "bumping archetype version to release: $RELEASE_VERSION"
echo ""

echo ""
echo "... mvn versions:set -DnewVersion=$RELEASE_VERSION"
echo ""
mvn versions:set -DnewVersion=$RELEASE_VERSION > /dev/null

echo ""
echo "... git commit -am \"bumping archetype to release: $RELEASE_VERSION\""
echo ""
git commit -am "bumping archetype to release: $RELEASE_VERSION"





echo ""
echo "double-check (mvn clean install -T1C -o -DskipTests)"
echo ""
mvn clean install -T1C -o -DskipTests >/dev/null
if [ $? != 0 ]; then
    echo "... failed" >&2
    exit 1
fi

