#!/bin/bash
clear
echo ""
echo ""
echo ======================================================
echo ==== RUNNING JENKINS SHELL COMMANDS on $NODE_NAME
echo ====   NOTE: Most of the commands below could be added to a script in SCM
echo ====         only the directories and port numbers need to be set in the job definition
echo ""
echo ==== selected Jenkins environment variables ====
set | egrep "BRANCH|BUILD|JENKINS|JOB|WORKSPACE"
echo ====/selected Jenkins environment variables ====

# ==== ADJUSTABLE VARIABLES ====
# we need to update these to set only if they're not set already, that way the Dev can override in the job template
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

# set container name
CONTAINER_NAME=`basename $BRANCH_NAME`

# stop running containers
echo ""
echo stopping running containers with name $CONTAINER_NAME
for CONTAINER in `docker ps | egrep "$CONTAINER_NAME" | awk '{print $1}'`; do echo stopping $CONTAINER; docker stop $CONTAINER; done

# delete stopped containers
echo ""
echo deleting containers with name $CONTAINER_NAME
docker container ls | egrep "$CONTAINER_NAME"
for CONTAINER in `docker container ls | egrep "$CONTAINER_NAME" | awk '{print $1}'`; do echo deleting $CONTAINER; docker container rm -f $CONTAINER; done; echo EXIT FOR
docker container rm $CONTAINER_NAME

# delete existing images
echo ""
echo deleting any docker images with name $CONTAINER_NAME
docker images | egrep "$CONTAINER_NAME"
for IMAGE in `docker images | egrep "$CONTAINER_NAME" | awk '{print $3}'`; do echo deleting $IMAGE; docker image rm -f $IMAGE; done

echo ""
echo "finding a free port to map to the new container's Tomcat port"
PORT=8000; MAXPORT=8099; while [ $PORT -le $MAXPORT ]; do FREE=`netstat -an | egrep "LISTEN *$" | grep $PORT`; if [ "$FREE" = "" ]; then echo $PORT is free; break; fi; PORT=$((PORT+1)); sleep 0; done; if [ $PORT -gt $MAXPORT ]; then echo reached $PORT, no ports are free; PORT=NULL; echo PORT=$PORT; fi

# create dockerfile location in $WORKSPACE
#cp -R $DOCKERFILE_LOCATION $WORKSPACE
#cd $WORKSPACE/$DOCKERFILE_NAME
#pwd

# copy latest Hippo distribution files to $WORKSPACE/$DOCKERFILE_NAME
echo ""
#echo copying latest Hippo distribution files to $WORKSPACE/$DOCKERFILE_NAME
HIPPO_LATEST=`ls -alht $WORKSPACE/target/*.tar.gz | head -1 | awk '{print $9}'` 2>&1 > /dev/null
if [ -z "$HIPPO_LATEST" ]; then
  echo no archive found in $WORKSPACE/target/, widening search
  HIPPO_LATEST=`find $WORKSPACE/ -name "*.tar.gz" | head -1`
fi
if [ ! -z "$HIPPO_LATEST" ]; then
  echo found $HIPPO_LATEST
else
  echo no archive found in $WORKSPACE, giving up
fi
#cd $WORKSPACE/$DOCKERFILE_NAME/
#echo archive found at $HIPPO_LATEST
#echo copying $HIPPO_LATEST to $WORKSPACE/$DOCKERFILE_NAME/target
#cp $HIPPO_LATEST $WORKSPACE/$DOCKERFILE_NAME/target

#docker build -t $CONTAINER_NAME .

if [ ! "$PORT" = "NULL" ]; then
sleep 5

echo ""
echo about to start Docker container with:
echo docker run -d --name $CONTAINER_NAME -p $PORT:8080 $DOCKERFILE_NAME /bin/bash -c "/usr/local/bin/vs-mysqld-start && /usr/local/bin/vs-hippo && while [ ! -f /home/hippo/tomcat_8080/logs/hippo-cms.log ]; do echo no log; sleep 2; done; tail -f /home/hippo/tomcat_8080/logs/hippo-cms.log"
docker run -d --name $CONTAINER_NAME -p $PORT:8080 $DOCKERFILE_NAME /bin/bash -c "/usr/local/bin/vs-mysqld-start && /usr/local/bin/vs-hippo && while [ ! -f /home/hippo/tomcat_8080/logs/hippo-cms.log ]; do echo no log; sleep 2; done; tail -f /home/hippo/tomcat_8080/logs/hippo-cms.log"
sleep 10

echo ""
echo about to copy $HIPPO_LATEST to container $CONTAINER_NAME:/home/hippo
docker cp $HIPPO_LATEST $CONTAINER_NAME:/home/hippo

echo ""
echo about to execute "/usr/local/bin/vs-hippo nodb" in container $CONTAINER_NAME
docker exec -d $CONTAINER_NAME /usr/local/bin/vs-hippo nodb
fi

echo ""
echo ""
echo "###############################################################################################################################"
echo The site instance for branch $GIT_BRANCH should now be available on $NODE_NAME - $VS_HOST_IP_ADDRESS at:
echo $VS_PROXY_SCHEME://$VS_PROXY_HOST/?vs_brxm_host=$VS_HOST_IP_ADDRESS&vs_brxm_port=$PORT&vs_brxm_http_host=$VS_BRXM_INSTANCE_HTTP_HOST
echo ""
echo The CMS for the instance should now be available at:
echo $VS_PROXY_SERVER_SCHEME://$VS_PROXY_SERVER_HOST/cms/?vs_brxm_host=$VS_HOST_IP_ADDRESS&vs_brxm_port=$PORT&vs_brxm_http_host=$VS_BRXM_INSTANCE_HTTP_HOST
echo and the Console at:
echo $VS_PROXY_SERVER_SCHEME://$VS_PROXY_SERVER_HOST/cms/console/?vs_brxm_host=$VS_HOST_IP_ADDRESS&vs_brxm_port=$PORT&vs_brxm_http_host=$VS_BRXM_INSTANCE_HTTP_HOST
echo ""
echo To clear the proxy server settings between sessions either close your browser or browse to:
echo $VS_PROXY_SERVER_SCHEME://$VS_PROXY_SERVER_HOST/?vs_brxm_reset
echo ""
echo ""
echo Fallback access - available only on the Web Development LAN
echo http://$VS_HOST_IP_ADDRESS:$PORT/cms/
echo http://$VS_HOST_IP_ADDRESS:$PORT/site/
echo "###############################################################################################################################"
echo ""
echo ""
# should really tidy
