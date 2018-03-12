#!/bin/bash
# common utility scripts shared by cyber installation

if [ -z ${NEXUS_REPO+x} ]; then
  NEXUS_REPO="http://rhlappdip701.fairisaac.com:8081/nexus/content/repositories/CyberSnapshots"
fi

if [ -z ${TOMCAT_HOME+x} ]; then 
  TOMCAT_HOME="/apps/tomcat"
fi

#-read property from a property file---
read_property_from_file(){
#Arguments--
#1 property file path
#2 property name
if [ "$#" -gt 1 ]; then
  file=$1
  if [ ! -f "$file" ]; then
    echo "$file not found, exit"     
    exit 1
   fi
  property=$2
else
  echo "Expected 2 arguments but get fewer, exit now" 
  exit 1
fi

value=`sed '/^\#/d' $file | grep $property  | tail -n 1 | cut -d "=" -f2- | sed 's/^[[:space:]]*//;s/[[:space:]]*$//'`
echo $value
}

replace_property_in_file(){
#$1 property name
#$2 new property value
#$3 property file path
#echo "1=$1 2=$2 3=$3"
if ! [ "$#" -gt 2 ]; then
  echo "Expected 3 arguments but get fewer, exit now"
  exit 1
fi
echo "Replacing property " $1
sed -i "s/$1=[^&]*/$1=$2/" $3
}


# read a stream from stdin and escape characters in text that could be interpreted as
# special characters by sed
escape_sed() {
 echo $1 | sed -e 's/[]\/$*.^|[]/\\&/g'
}

showPorts() {
  local spid=$1
  netstat -nap | grep java | while IFS=' ' read proto a b laddr faddr state pidName
  do
    if [ $proto == 'tcp' ]; then
      pid=${pidName%%/java}
      if [ $pid == "$spid" -a $state == LISTEN ]; then
        port=${laddr##:*:*:}
        echo "    $state on $proto port=$port"
      fi
    fi
  done
}

showTomcatSSLPort() {
pid=$(ps -ef | grep catalina | grep -v "grep catalina" | grep -v "catalina.out"| awk '{print $2}' | head -1)
port=$(showPorts $pid | grep 443 |tail -n 1 | cut -d "=" -f2- | sed 's/^[[:space:]]*//;s/[[:space:]]*$//')
echo $port
}

checkUrlAvailability(){

if [ "$#" -lt 1 ]; then
  echo "Usage: checkUrlAvailability url"
  exit 1
fi
url=$1
#Try 5 minutes
end=$((SECONDS+300))
while [ $SECONDS -lt $end ]; do
  echo "Connecting $url"
  status=$(curl -I -k -s -o /dev/null -w "%{http_code}" "$url")
  if [ $status -ne "302" ]; then
    echo "$url is not available yet, will retry in 10 seconds..."
    sleep 10
  else 
    echo "$url is available." 
    break  
  fi
done
if [ $SECONDS -gt $end ]; then
  echo "Connection timeout: $url"
  exit 1 
fi
}