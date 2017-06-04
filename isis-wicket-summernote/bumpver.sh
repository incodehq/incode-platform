VERSION=$1

if [ ! "$VERSION" ]; then
    echo "usage: $(basename $0) [version]"
    exit 1
fi

# edit parent pom.xml's reference
echo "editing parent's pom.xml (reference to cpt module)"
cat pom.xml | sed "s/<isis-wicket-wickedcharts.version>.*</<isis-wicket-wickedcharts.version>$VERSION</" > pom.xml.$$.sed
mv pom.xml.$$.sed pom.xml

# edit cpt's pom.xml
echo "editing cpt's pom.xml"
pushd cpt >/dev/null
mvn versions:set -DnewVersion=$VERSION > /dev/null
popd >/dev/null

echo "Committing changes"
git commit -am "bumping to $VERSION"

# tag if not a snapshot
echo $VERSION | grep -v SNAPSHOT > /dev/null
if [ $? = 0 ]; then
    echo "tagging (not a snapshot version)"
    git tag $VERSION
fi
