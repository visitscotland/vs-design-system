#!/bin/bash
clear
echo ""
echo ""
echo ======================================================
echo ==== RUNNING JENKINS SHELL COMMANDS on $NODE_NAME
echo ""
echo ==== selected Jenkins environment variables ====
set | egrep "BRANCH|BUILD|JENKINS|JOB|WORKSPACE"
echo ====/selected Jenkins environment variables ====

# ==== ADJUSTABLE VARIABLES ====
# gp:to-do we need to update these to set only if they're not set already, that way the Dev can override in the Jenkinsfile
#  == Tomcat Variables ==
DOCKERFILE_PATH=/home/jenkins/vs-dockerfile/
DOCKERFILE_NAME=brx13
DOCKERFILE_LOCATION=$DOCKERFILE_PATH/$DOCKERFILE_NAME
VS_DATESTAMP=`date +%Y%m%d`
VS_HOST_IP_ADDRESS=`/usr/sbin/ip ad sh  | egrep "global noprefixroute" | awk '{print $2}' | sed -e "s/\/.*$//"`
#  == brXM Instance Variables ==
VS_BRXM_INSTANCE_HTTP_HOST=localhost
#  == Hosting Environment Variables ==
VS_PROXY_SERVER_SCHEME=https
VS_PROXY_SERVER_FQDN=feature.visitscotland.com
# the next variable "VS_BRXM_PORT_OVERRIDE" should only be used by operations for debug purposes, an available port will be found and used later in this script
#VS_BRXM_PORT_OVERRIDE=8080
# ====/ADJUSTABLE VARIABLES ====

# set container name from branch name - removing / characters
CONTAINER_NAME=`echo $JOB_NAME | sed -e "s/\/.*//g"`"_"`basename $BRANCH_NAME`
set | egrep "CONTAINER"

# stop running containers
echo ""
echo stopping running containers with name $CONTAINER_NAME
for CONTAINER in `docker ps | egrep "$CONTAINER_NAME" | awk '{print $1}'`; do echo stopping $CONTAINER; docker stop $CONTAINER; done

# delete stopped containers
echo ""
echo deleting containers with name $CONTAINER_NAME
docker container ls | egrep "$CONTAINER_NAME"
for CONTAINER in `docker container ls | egrep "$CONTAINER_NAME" | awk '{print $1}'`; do echo deleting $CONTAINER; docker container rm -f $CONTAINER; done
#docker container rm $CONTAINER_NAME

# delete existing images
echo ""
echo deleting any docker images with name $CONTAINER_NAME
docker images | egrep "$CONTAINER_NAME"
for IMAGE in `docker images | egrep "$CONTAINER_NAME" | awk '{print $3}'`; do echo deleting $IMAGE; docker image rm -f $IMAGE; done

# gp:to-do even if override is set we must still check to ensure it's free, move the while loop to after the if block and just add PORT/MAXPORT values into the if. If the override port if in use the job must fail
if [ "$GIT_BRANCH" == "develop" ]; then
  echo ""
  echo "GIT_BRANCH is $GIT_BRANCH, OVERRIDE PORT will be set to 8100"
  VS_BRXM_PORT_OVERRIDE=8100
fi

if [ -z "$VS_BRXM_PORT_OVERRIDE" ]; then
  PORT=8000
  MAXPORT=8099;
  echo ""
  echo "finding a free port to map to the new container's Tomcat port - range $PORT-$MAXPORT"
else
  PORT=$VS_BRXM_PORT_OVERRIDE
  MAXPORT=$VS_BRXM_PORT_OVERRIDE
  echo ""
  echo "PORT will be set to $VS_BRXM_PORT_OVERRIDE due to VS_BRXM_PORT_OVERRIDE"
fi

while [ $PORT -le $MAXPORT ]; do
  FREE=`netstat -an | egrep "LISTEN *$" | grep $PORT`
  if [ "$FREE" = "" ]; then
    echo $PORT is free
    break
  fi
  PORT=$((PORT+1))
  sleep 0
done

if [ $PORT -gt $MAXPORT ]; then
  PORT=NULL
  SAFE_TO_PROCEED=FALSE
  FAIL_REASON=reached $PORT, no ports are free, setting PORT to NULL
  echo $FAIL_REASON
fi

# search for latest Hippo distribution files
echo ""
echo searching for latest Hippo distribution files in $WORKSPACE/target
HIPPO_LATEST=`ls -alht $WORKSPACE/target/*.tar.gz | head -1 | awk '{print $9}'` 2>&1 > /dev/null
if [ -z "$HIPPO_LATEST" ]; then
  echo no archive found in $WORKSPACE/target/, widening search
  HIPPO_LATEST=`find $WORKSPACE/ -name "*.tar.gz" | head -1`
fi
if [ ! -z "$HIPPO_LATEST" ]; then
  echo found $HIPPO_LATEST
else
  HIPPO_LATEST=NULL
  SAFE_TO_PROCEED=FALSE
  FAIL_REASON=no archive found in $WORKSPACE, giving up
  echo $FAIL_REASON
fi

if [ ! "$SAFE_TO_PROCEED" = "FALSE" ]; then
  sleep 5

  echo ""
  echo about to start Docker container with:
  echo docker run -d --name $CONTAINER_NAME -p $PORT:8080 $DOCKERFILE_NAME /bin/bash -c "/usr/local/bin/vs-mysqld-start && /usr/local/bin/vs-hippo && while [ ! -f /home/hippo/tomcat_8080/logs/hippo-cms.log ]; do echo no log; sleep 2; done; tail -f /home/hippo/tomcat_8080/logs/hippo-cms.log"
  docker run -d --name $CONTAINER_NAME -p $PORT:8080 $DOCKERFILE_NAME /bin/bash -c "/usr/local/bin/vs-mysqld-start && /usr/local/bin/vs-hippo && while [ ! -f /home/hippo/tomcat_8080/logs/hippo-cms.log ]; do echo no log; sleep 2; done; tail -f /home/hippo/tomcat_8080/logs/hippo-cms.log"
  RETURN_CODE=$?; echo $RETURN_CODE
  sleep 10

  echo ""
  echo about to copy $HIPPO_LATEST to container $CONTAINER_NAME:/home/hippo
  docker cp $HIPPO_LATEST $CONTAINER_NAME:/home/hippo

  echo ""
  echo about to execute "/usr/local/bin/vs-hippo nodb" in container $CONTAINER_NAME
  docker exec -d $CONTAINER_NAME /usr/local/bin/vs-hippo nodb
else

if [ ! "$SAFE_TO_PROCEED" = "FALSE" ]; then
  echo ""
  echo ""
  echo "###############################################################################################################################"
  echo ""
  echo The site instance for branch $GIT_BRANCH should now be available in a few moments on $NODE_NAME - $VS_HOST_IP_ADDRESS at:
  echo "  - $VS_PROXY_SERVER_SCHEME://$VS_PROXY_SERVER_FQDN/?vs_brxm_host=$VS_HOST_IP_ADDRESS&vs_brxm_port=$PORT&vs_brxm_http_host=$VS_BRXM_INSTANCE_HTTP_HOST"
  echo ""
  echo The CMS for the instance should now be available at:
  echo "  - $VS_PROXY_SERVER_SCHEME://$VS_PROXY_SERVER_FQDN/cms/?vs_brxm_host=$VS_HOST_IP_ADDRESS&vs_brxm_port=$PORT&vs_brxm_http_host=$VS_BRXM_INSTANCE_HTTP_HOST"
  echo and the Console at:
  echo "  - $VS_PROXY_SERVER_SCHEME://$VS_PROXY_SERVER_FQDN/cms/console/?vs_brxm_host=$VS_HOST_IP_ADDRESS&vs_brxm_port=$PORT&vs_brxm_http_host=$VS_BRXM_INSTANCE_HTTP_HOST"
  echo ""
  echo To clear the proxy server settings between sessions either close your browser or browse to:
  echo "  - $VS_PROXY_SERVER_SCHEME://$VS_PROXY_SERVER_FQDN/?vs_brxm_reset"
  echo ""
  echo ""
  echo Direct Tomcat access - available only on the Web Development LAN
  echo "  - http://$VS_HOST_IP_ADDRESS:$PORT/cms/"
  echo "  - http://$VS_HOST_IP_ADDRESS:$PORT/site/"
  echo "    -  needs a HOST header of localhost:8080 to be passed with the request
  echo "###############################################################################################################################"
  echo ""
  echo ""
else
  echo "###############################################################################################################################
  echo ""
  echo JOB FAILED because $FAIL_REASON
  echo "###############################################################################################################################"
  echo ""
  echo ""
fi

# gp:to-do should really tidy
