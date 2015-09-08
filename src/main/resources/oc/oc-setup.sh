#!/bin/bash

DIRNAME=$(dirname $0)

if [ "$#" -ne 1 ]; then
  echo "Usage oc-setup.sh [project-name]"
  exit 1
fi

USER=$(oc whoami 2>/dev/null)
if [ "$?" -ne 0 ]; then
  echo "You are not logged in to openshift console, please login before executing this $0"
  exit 2
else
  echo "Authenticate to openshift console as $USER"
fi

if [ "$(oc get project | grep $1 | wc -l)" -ne 0 ]; then
  echo "Project with name $1 already exists!"
  printf "Do you want to delete it [Y/n]? "
  read answer
  if [ "$answer" == "n" -o "$answer" == "N" -o "$answer" == "No" -o "$answer" == "NO" -o "$answer" == "no" ]; then
    echo "Aborting!"
    exit 0
  else
    echo "Deleting project $1"
    oc delete project $1
    sleep 2 # Allow time for the OpenShift to update it's project registry
  fi
fi

oc new-project $1 --display-name="JBoss EAP Demo Project" --description="This is a simple JBoss EAP demo project"
oc create -f $DIRNAME/scripts/jboss-eap-imagestream.json
oc create -f $DIRNAME/scripts/simple-eap-temlpate.json
oc new-app --template=jboss-demo-example

printf "Project is created and image stream and templated are installed, do you want to build the app [Y/n]? "
read answer
if [ "$answer" == "n" -o "$answer" == "N" -o "$answer" == "No" -o "$answer" == "NO" -o "$answer" == "no" ]; then
  echo ""
else
  echo "Building app jbossdemo"
  oc start-build jbossdemo --follow=true > /dev/null
  if [ "$?" -ne 0 ]; then
    echo "ERROR: There was an error buiding the project, to see the build run 'oc get builds' to get the latest build id and then run 'oc build-logs <build-id>'."
  fi
fi

echo "Stoping and deleting existing local containers"
if [ "$(docker ps -q | wc -w)" -ne 0 ]; then
  docker stop $(docker ps -q)
fi

if [ "$(docker ps -aq | wc -w)" -ne 0 ]; then
  docker rm $(docker ps -aq)
fi
