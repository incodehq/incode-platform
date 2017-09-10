#!/usr/bin/env bash
VERSION=$1

if [ ! "$VERSION" ]; then
    echo "usage: $(basename $0) [version]"
    exit 1
fi


echo ""
echo "mvn versions:set -DnewVersion=$VERSION"
echo ""
mvn versions:set -DnewVersion=$VERSION > /dev/null

echo ""
echo "git commit -am \"bumping to $VERSION\""
echo ""
git commit -am "bumping to $VERSION"


# tag if not a SNAPSHOT version
echo $VERSION | grep -v SNAPSHOT > /dev/null
if [ $? = 0 ]; then
    echo ""
    echo "git tag $VERSION"
    echo ""
    git tag $VERSION
fi
