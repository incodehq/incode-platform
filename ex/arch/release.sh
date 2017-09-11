#!/usr/bin/env bash
RELEASE_VERSION=$1
shift
SNAPSHOT_VERSION=$1
shift
KEYID=$1
shift
PASSPHRASE=$*

if [ ! "$RELEASE_VERSION" -o ! "$SNAPSHOT_VERSION" -o ! "$KEYID" -o ! "$PASSPHRASE" ]; then
    echo "usage: $(basename $0) [release_version] [snapshot_version] [keyid] [passphrase]" >&2
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


echo ""
echo "releasing (mvn clean deploy -P release)"
echo ""
mvn clean deploy -Prelease -DskipTests -Dpgp.secretkey=keyring:id=$KEYID -Dpgp.passphrase="literal:$PASSPHRASE"
if [ $? != 0 ]; then
    echo "... failed" >&2
    exit 1
fi





echo ""
echo "bumping archetype version to snapshot: $SNAPSHOT_VERSION"
echo ""

echo ""
echo "... mvn versions:set -DnewVersion=$SNAPSHOT_VERSION"
echo ""
mvn versions:set -DnewVersion=$SNAPSHOT_VERSION > /dev/null

echo ""
echo "... git commit -am \"bumping archetype to snapshot: $SNAPSHOT_VERSION\""
echo ""
git commit -am "bumping archetype to snapshot: $SNAPSHOT_VERSION"



echo ""
echo "now run:"
echo ""
echo "git push origin master"
echo ""
