
if [ $# -lt 1 ]; then
    echo "usage: `basename $0` [NewNameUpperCase]" >&2
    exit 1
fi

FROM_UPPER="Xxx"
FROM_LOWER=`echo ${FROM_UPPER} | tr "[:upper:]" "[:lower:]"`

TO_UPPER=$1
TO_LOWER=`echo ${TO_UPPER} | tr "[:upper:]" "[:lower:]"`

for a in `find . -type d | grep ${FROM_LOWER}$`
do 
    b=`echo $a | sed "s/${FROM_LOWER}/${TO_LOWER}/g"`
    echo $a $b
    mv $a $b
done

for a in `find . -type f | grep ${FROM_UPPER}`
do 
    b=`echo $a | sed "s/${FROM_UPPER}/${TO_UPPER}/g"`
    echo $a $b
    mv $a $b
done
