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
echo "sanity check (mvn clean install -T1C -o -DskipTests -Dskip.isis-swagger -Dskip.isis-validate) "
echo ""
mvn clean install -T1C -DskipTests -Dskip.isis-swagger -Dskip.isis-validate -o >/dev/null
if [ $? != 0 ]; then
    echo "... failed" >&2
    exit 1
fi




echo ""
echo "bumping modules version to release: $RELEASE_VERSION"
echo ""

echo ""
echo "... mvn versions:set -DnewVersion=$RELEASE_VERSION"
echo ""
mvn versions:set -DnewVersion=$RELEASE_VERSION > /dev/null

echo ""
echo "... git commit -am \"bumping modules to release: $RELEASE_VERSION\""
echo ""
git commit -am "bumping modules to release: $RELEASE_VERSION"





echo ""
echo "double-check (mvn clean install -T1C -o -DskipTests -Dskip.isis-swagger  -Dskip.isis-validate)"
echo ""
mvn clean install -T1C -DskipTests -Dskip.isis-swagger -Dskip.isis-validate -o >/dev/null
if [ $? != 0 ]; then
    echo "... failed" >&2
    exit 1
fi


echo ""
echo "releasing (mvn clean deploy -P release)"
echo ""
mvn clean deploy -Prelease -Dskip.isis-swagger  -Dskip.isis-validate -Dpgp.secretkey=keyring:id=$KEYID -Dpgp.passphrase="literal:$PASSPHRASE"
if [ $? != 0 ]; then
    echo "... failed" >&2
    exit 1
fi




echo ""
echo "tagging repo as release: $RELEASE_VERSION"
echo ""
echo "... git tag $RELEASE_VERSION"
echo ""

git tag $RELEASE_VERSION

echo ""
echo "bumping modules version to snapshot: $SNAPSHOT_VERSION"
echo ""

echo ""
echo "... mvn versions:set -DnewVersion=$SNAPSHOT_VERSION"
echo ""
mvn versions:set -DnewVersion=$SNAPSHOT_VERSION > /dev/null

echo ""
echo "... git commit -am \"bumping modules to snapshot: $SNAPSHOT_VERSION\""
echo ""
git commit -am "bumping modules to snapshot: $SNAPSHOT_VERSION"



echo ""
echo "now run:"
echo ""
echo "git push origin master && git push origin $RELEASE_VERSION"
echo ""
