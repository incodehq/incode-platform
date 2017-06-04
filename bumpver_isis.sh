VERSION=$1

if [ ! "$VERSION" ]; then
    echo "usage: $(basename $0) [version]"
    exit 1
fi

# edit pom.xml
echo "editing pom.xml"
cat pom.xml | sed "s/<isis.version>.*</<isis.version>$VERSION</" > pom.xml.$$.sed
mv pom.xml.$$.sed pom.xml

echo "Committing changes"
git commit -am "bumping isis.version to $VERSION"

