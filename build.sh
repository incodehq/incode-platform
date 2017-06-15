#!/bin/bash
#set -x
#trap read debug
set -o nounset
set -o errexit


#
# Build a single module
#

function die {
	local prefix="[$(date +%Y/%m/%d\ %H:%M:%S)]: "
	echo "${prefix} ERROR: $@" 1>&2
	exit 10
}

typearg=""
type=""
artifact=""
goals="clean install"
extra=""
skipTests=""

#
# validate script args
#

while getopts ":st:g:" opt; do
    case $opt in
        t)
          typearg=$OPTARG
          ;;
        s)
          skipTests="-DskipTests"
          ;;
        g)
          goals=$OPTARG
          ;;
        \?)
          echo "Invalid option: -$OPTARG" >&2
          ;;
    esac
done

shift $((OPTIND-1))
if [ $# -gt 0 ]; then
    artifact=$1
    shift
fi
extra=$@

case $typearg in
    l)
      type="lib"
      ;;
    e)
      type="extension"
      ;;
    m)
      type="module"
      ;;
    s)
      type="spi"
      ;;
    w)
      type="wicket"
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      ;;
esac

echo ""
echo "type      : $type"
echo "artifact  : $artifact"
echo "goals     : $goals"
if [ "$extra" != "" ]; then
    echo "extra args: $extra"
fi
if [ "$skipTests" != "" ]; then
    echo "skipTests : $skipTests"
fi
echo ""
echo ""

if [ "$type" == "" -o "$artifact" == "" -o "$goals" == "" ]; then
    echo "usage: build.sh -t{-m|-l|-e|-s|-w} [-s] [-g goals] artifact [extra flags]" >&2
    echo "" >&2
    echo "       -t = type" >&2
    echo "           m = module" >&2
    echo "           l = library" >&2
    echo "           s = spi" >&2
    echo "           e = extension" >&2
    echo "           w = wicket component" >&2
    echo "" >&2
    echo "       -g = maven goals" >&2
    echo "            optional, defaults to 'clean install'" >&2
    echo "" >&2
    echo "       -s = skip tests" >&2
    echo "            optional, adds '-DskipTests' when invoking mvn" >&2
    echo "" >&2
    echo "       artifact depends on type" >&2
    echo "            eg -tm alias   =  module-alias" >&2
    echo "            eg -ts audit   =  spi-audit" >&2
    echo "" >&2
    echo "       extra flags passed through to mvn directly" >&2
    echo "            eg -o" >&2
    echo "" >&2
    exit 1
fi


echo mvn -Dskip.default -D$type-$artifact $skipTests $goals $extra
echo ""
echo ""

mvn -Dskip.default -D$type-$artifact $skipTests $goals $extra
