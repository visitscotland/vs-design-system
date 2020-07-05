#!/bin/bash

# ==== TO-DO ====
# gp: split into functions - done
# gp: activate clean-up routine - done
# gp: update adjustatable variables to set only if they're not set already, that way the Dev can override in the Jenkinsfile - partially done
# gp: rename BASE_PORT to MIN_PORT - done
# gp: update port find to set VS_CONTAINER_BASE_PORT between MIN_PORT and MAX_PORT - done
#      - then check the + 100s right to the limit
# gp: create an array of required ports
#      - e.g. "VS_CONTAINER_BRXM_PORT VS_CONTAINER_SSR_PORT VS_CONTAINER_SSH_PORT"
#      - then do a FOR MAP_PORT in VS_CONTAINER_REQUIRED_PORTS and +100 knowing that the're available (from above)
# gp: if an existent container is found grab its base port
#     - re-use it for any new container
# gp: create routine to re-use existing container if it's there
#     - start it if stoppped - redeploy artifact if it's running
# ====/TO-DO ====

# ==== SETUP ====
# ==== ADJUSTABLE VARIABLES ====
#  == VS Variables ==
if [ -z "$VS_DEBUG" ]; then VS_DEBUG=FALSE; fi
if [ -z "$VS_DOCKER_IMAGE_NAME" ]; then VS_DOCKER_IMAGE_NAME=vs-brxm; fi
if [ -z "$VS_DOCKERFILE_PATH" ]; then VS_DOCKERFILE_PATH=/home/jenkins/vs-dockerfile; fi
if [ -z "$VS_DOCKERFILE_NAME" ]; then VS_DOCKERFILE_NAME=vs-brxm; fi
if [ -z "$VS_DOCKERFILE_LOCN" ]; then VS_DOCKERFILE_LOCN=$VS_DOCKERFILE_PATH/$VS_DOCKERFILE_NAME; fi
VS_DATESTAMP=`date +%Y%m%d`
VS_HOST_IP_ADDRESS=`/usr/sbin/ip ad sh  | egrep "global noprefixroute" | awk '{print $2}' | sed -e "s/\/.*$//"`
#  ==== Hosting Environment Variables ====
if [ -z "$VS_PROXY_SERVER_SCHEME" ]; then VS_PROXY_SERVER_SCHEME=https; fi
if [ -z "$VS_PROXY_SERVER_FQDN" ]; then VS_PROXY_SERVER_FQDN=feature.visitscotland.com; fi
#  == brXM Instance Variables ==
if [ -z "$VS_CONTAINER_BASE_PORT_OVERRIDE" ]; then unset VS_CONTAINER_BASE_PORT_OVERRIDE; fi
if [ -z "$VS_BRXM_INSTANCE_HTTP_HOST" ]; then VS_BRXM_INSTANCE_HTTP_HOST=localhost; fi
if [ -z "$VS_BRXM_TOMCAT_PORT" ]; then VS_BRXM_TOMCAT_PORT=8080; fi
#  ==== SSR Application Variables ====
if [ -z "$VS_FRONTEND_DIR" ]; then VS_FRONTEND_DIR=frontend; fi
if [ -z "$VS_SSR_PACKAGE_SOURCE" ]; then VS_SSR_PACKAGE_SOURCE="$VS_FRONTEND_DIR/ssr/server/ $VS_FRONTEND_DIR/dist/ssr/ $VS_FRONTEND_DIR/node_modules/"; fi
if [ -z "$VS_SSR_PACKAGE_TARGET" ]; then VS_SSR_PACKAGE_TARGET="./target"; fi
if [ -z "$VS_SSR_PACKAGE_NAME" ]; then VS_SSR_PACKAGE_NAME="vs-ssr-package.tar.gz"; fi
if [ -z "$VS_SSR_APP_PORT" ]; then VS_SSR_APP_PORT=8082; fi
# ====/ADJUSTABLE VARIABLES ====
# ==== TESTING VARIABLES ====
if [ -z "$BRANCH_NAME" ]; then BRANCH_NAME=feature/VS-1865-feature-environments-enhancements; fi
if [ -z "$JOB_NAME" ]; then JOB_NAME=feature.visitscotland.com-mb/feature%2FVS-1865-feature-environments-enhancements; fi
# ====/TESTING VARIABLES ====
# ====/SETUP ====

# ==== PARSE COMMAND LINE ARGUMENTS ====
while [[ $# -gt 0 ]]; do
  argument="$1"
  THIS_VAR=`echo $argument | sed -e "s/=.*//g"`; #echo $THIS_VAR
  THIS_VAL=`echo $argument | sed -e "s/.*=//g" | sed -s "s/--.*//g"`; #echo $THIS_VAL
  if [ ! -z "$THIS_VAL" ]; then THIS_RESULT="$THIS_VAL"; elif [ ! "${2:0:2}" = "--" ]; then THIS_RESULT="$2"; else THIS_RESULT=""; fi
  if [ "$VS_DEBUG" == "TRUE" ]; then echo -en "\nread \"$THIS_VAR\" from command line"; fi
  case $THIS_VAR in
    --debug) if [ ! -z "$THIS_RESULT" ]; then VS_DEBUG=$THIS_RESULT; else VS_DEBUG=TRUE; fi;;
    --tidy-containers) if [ ! -z "$THIS_RESULT" ]; then VS_TIDY_CONTAINERS=$THIS_RESULT; else VS_TIDY_CONTAINERS=TRUE; fi;;
    *)
      if [ "$DEBUG" == "TRUE" ]; then echo -en " - no match found - SKIPPING"; fi
    ;;
  esac
  shift
  done
  echo -en "\n"
# ====/PARSE COMMAND LINE ARGUMENTS ====

# ==== FUNCTIONS ====
defaultSettings() {
  # unset variables
  VS_CONTAINER_LIST=
  VS_PARENT_JOB_NAME=
  RESERVED_PORT_LIST=
  # set container name from branch name - removing / characters
  VS_CONTAINER_NAME=`echo $JOB_NAME | sed -e "s/\/.*//g"`"_"`basename $BRANCH_NAME`
  if [ -z "$NODE_NAME" ]; then THIS_SERVER=$HOSTNAME; else THIS_SERVER=$NODE_NAME; fi
  VS_COMMIT_AUTHOR=`git show -s --pretty="%ae" ${GIT_COMMIT}`
  VS_PARENT_JOB_NAME=`echo $JOB_NAME | sed -e "s/\/.*//g"`
}

reportSettings() {
  clear
  echo ""
  echo "================================================================================"
  echo "== RUNNING JENKINS SHELL COMMANDS on $THIS_SERVER" as $USER
  echo "================================================================================"
  echo ""
  if [ "$VS_DEBUG" = "TRUE" ]; then echo "==== printenv ===="; printenv; echo "====/printenv ===="; echo ""; fi
  #if [ "$VS_DEBUG" = "TRUE" ]; then echo "==== set ===="; set; echo "====/set ====";  echo ""; fi
  echo "==== selected Jenkins environment variables ===="
  set | egrep "^(BRANCH|BUILD|CHANGE|GIT|JENKINS|JOB|RUN|WORKSPACE)"
  echo "====/selected Jenkins environment variables ===="
  echo ""
  echo "==== selected VS environment variables ===="
  set | egrep "^(VS_)"
  echo "====/selected VS environment variables ===="
  echo ""
}

checkContainers() {
  # check to see if a container called $VS_CONTAINER_NAME exists, if so set $CONTAINER_ID to Docker's CONTAINER ID
  echo "checking for containers with name $VS_CONTAINER_NAME"
  CONTAINER_ID=`docker ps -aq --filter "name=$VS_CONTAINER_NAME"`
  if [ ! -z "$CONTAINER_ID" ]; then
    echo " - container found, ID:$CONTAINER_ID, with name $VS_CONTAINER_NAME"
    echo " - checking status of container $CONTAINER_ID"
    CONTAINER_STATUS=`docker inspect --format "{{.State.Status}}" $CONTAINER_ID`
    echo " - $CONTAINER_STATUS container found with ID:$CONTAINER_ID and name $VS_CONTAINER_NAME"
    # GRAB BASE PORT
  else
    echo " - no container found with name $VS_CONTAINER_NAME"
  fi
  echo ""
}

stopContainers() {
  # TO-DO - maybe undeploy application first?
  echo "stopping containers with ID $CONTAINER_ID"
  for CONTAINER in $CONTAINER_ID; do
    echo "stopping $CONTAINER"
    docker stop $CONTAINER
  done
  echo ""
}

startContainers() {
  echo "starting containers with ID $CONTAINER_ID"
  for CONTAINER in $CONTAINER_ID; do
    echo "starting $CONTAINER"
    docker start $CONTAINER
  done
  echo ""
}

deleteContainers() {
  echo "deleting containers with name $CONTAINER_ID"
  for CONTAINER in $CONTAINER_ID; do
    echo "deleting $CONTAINER"
    docker container rm -f $CONTAINER
  done
  echo ""
}

deleteImages() {
  #delete existing images - does this have a purpose? will there ever be an image with the name $VS_CONTAINER_NAME?
  echo "deleting any docker images with name $VS_CONTAINER_NAME"
  docker images | egrep "$VS_CONTAINER_NAME"
  for IMAGE in `docker images | egrep "$VS_CONTAINER_NAME" | awk '{print $3}'`; do
    echo "deleting $IMAGE"
    docker image rm -f $IMAGE
  done
  echo ""
}

# check all branches to see what ports are "reserved" by existing containers
getChildBranchesViaCurl() {
  echo "checking for ports reserved by other branches in $VS_PARENT_JOB_NAME"
  #for CONTAINER in `curl -s $JENKINS_URL/job/$VS_PARENT_JOB_NAME/rssLatest | sed -e "s/type=\"text\/html\" href=\"/\n/g" | egrep "^https" | sed -e "s/%252F/\//g" | sed "s/\".*//g" | sed -e "s/htt.*\/\(.*\)\/[0-9]*\//\1/g" | egrep -v "http"`; do
  #  VS_CONTAINER_LIST="$VS_CONTAINER_LIST $CONTAINER"
  #  RESERVED_PORT=`docker inspect --format='{{(index (index .NetworkSettings.Ports "8080/tcp") 0).HostPort}}' $VS_PARENT_JOB_NAME\_$CONTAINER 2>/dev/null`
  #  if [ ! -z "$RESERVED_PORT" ]; then
  #    RESERVED_PORT_LIST="$RESERVED_PORT_LIST $RESERVED_PORT"
  #    echo "$RESERVED_PORT is reserved by $VS_PARENT_JOB_NAME\_$CONTAINER"
  #  fi
  #done
}

getBranchListViaCurl() {
  for CONTAINER in `curl -s $JENKINS_URL/job/$VS_PARENT_JOB_NAME/rssLatest | sed -e "s/type=\"text\/html\" href=\"/\n/g" | egrep "^https" | sed -e "s/%252F/\//g" | sed "s/\".*//g" | sed -e "s/htt.*\/\(.*\)\/[0-9]*\//$VS_PARENT_JOB_NAME\_\1/g" | egrep -v "http"`; do
    BRANCH_CONTAINER_LIST="$BRANCH_CONTAINER_LIST $CONTAINER"
    RESERVED_PORT=`docker inspect --format='{{(index (index .HostConfig.PortBindings "8080/tcp") 0).HostPort}}' $CONTAINER 2>/dev/null`
    if [ ! -z "$RESERVED_PORT" ]; then
      RESERVED_PORT_LIST="$RESERVED_PORT_LIST $RESERVED_PORT"
      echo "$RESERVED_PORT is reserved by $CONTAINER"
    fi
  done
  echo ""
}

getPullRequestListViaCurl() {
  echo "checking for ports reserved by pull requests in $VS_PARENT_JOB_NAME"
  for CONTAINER in `curl -s $JENKINS_URL/job/$VS_PARENT_JOB_NAME/view/change-requests/rssLatest | sed -e "s/type=\"text\/html\" href=\"/\n/g" | egrep "^https" | sed -e "s/%252F/\//g" | sed "s/\".*//g" | sed -e "s/htt.*\/\(.*\)\/[0-9]*\//$VS_PARENT_JOB_NAME\_\1/g" | egrep -v "http"`; do
    BRANCH_CONTAINER_LIST="$BRANCH_CONTAINER_LIST $CONTAINER"
    RESERVED_PORT=`docker inspect --format='{{(index (index .HostConfig.PortBindings "8080/tcp") 0).HostPort}}' $CONTAINER 2>/dev/null`
    if [ ! -z "$RESERVED_PORT" ]; then
      RESERVED_PORT_LIST="$RESERVED_PORT_LIST $RESERVED_PORT"
      echo "$RESERVED_PORT is reserved by $CONTAINER"
    fi
  done;
}

getBranchListFromWorkspace() {
  echo "checking for branches and PRs for $VS_PARENT_JOB_NAME listed in workspaces.txt"
  for BRANCH in `cat $JENKINS_HOME/workspace/workspaces.txt | grep "$VS_PARENT_JOB_NAME" | sed -e "s/%2F/\//g" | sed "s/.*\//$VS_PARENT_JOB_NAME\_/g"`; do
    if [ "$VS_DEBUG" = "TRUE" ]; then echo " - found branch $BRANCH"; fi
    BRANCH_LIST="$BRANCH_LIST $BRANCH"
  done
  echo ""
}

getReservedPortList() {
  echo "checking for ports reserved by containers in BRANCH_LIST"
  for BRANCH in $BRANCH_LIST; do
    RESERVED_PORT=`docker port $BRANCH 2>/dev/null| awk '{gsub(/.*:/,"");}1'`
    if [ ! -z $RESERVED_PORT ]; then
      RESERVED_PORT_LIST="$RESERVED_PORT_LIST $RESERVED_PORT"
      if [ "$VS_DEBUG" = "TRUE" ]; then echo " - $RESERVED_PORT is reserved by $BRANCH"; fi
    fi
  done
  echo ""
  if [ ! -z "$RESERVED_PORT_LIST" ]; then echo "Ports $RESERVED_PORT_LIST are reserved"; echo ""; fi
}

tidyContainers() {
  # tidy containers when building the "develop" branch
  if [ "$GIT_BRANCH" == "develop" ]||[ "$VS_TIDY_CONTAINERS" == "TRUE" ]; then
    echo "checking all containers on $NODE_NAME matching $VS_PARENT_JOB_NAME*"
    for CONTAINER in `docker ps -a --filter "name=$VS_PARENT_JOB_NAME*" --format "table {{.Names}}" | tail -n +2`; do
      CONTAINER_MATCHED=
      ALL_CONTAINER_LIST="$ALL_CONTAINER_LIST $CONTAINER"
      #echo "checking to see if there's a branch for $CONTAINER"
      for BRANCH_CONTAINER in $BRANCH_LIST; do
        if [ "$CONTAINER" = "$BRANCH_CONTAINER" ]; then
          echo "there is a branch $BRANCH_CONTAINER associated with container $CONTAINER"
          CONTAINER_MATCHED="TRUE"
          break
        fi
      done
      if [ ! "$CONTAINER_MATCHED" = "TRUE" ]; then
        echo "no branch $BRANCH_CONTAINER was found matching container $CONTAINER - deleting"
        echo docker container rm -f $CONTAINER
      fi
      done
    echo ""
  fi
}

setPortRange() {
  # gp:DONE - even if override is set we must still check to ensure it's free, move the while loop to after the if block and just add PORT/MAXPORT values into the if. If the override port if in use the job must fail
  if [ "$GIT_BRANCH" == "develop" ]; then
    echo "GIT_BRANCH is $GIT_BRANCH, OVERRIDE PORT will be set to 8100"
    VS_CONTAINER_BASE_PORT_OVERRIDE=8100
  fi
  if [ -z "$VS_CONTAINER_BASE_PORT_OVERRIDE" ]; then
    MIN_PORT=8000
    MAX_PORT=8099
  else
    MIN_PORT=$VS_CONTAINER_BASE_PORT_OVERRIDE
    MAX_PORT=$VS_CONTAINER_BASE_PORT_OVERRIDE
    echo "MIN_PORT will be set to $VS_CONTAINER_BASE_PORT_OVERRIDE due to VS_CONTAINER_BASE_PORT_OVERRIDE"
    echo ""
  fi
}

findBasePort() {
  echo "Finding a free port to map to the new container's Tomcat port - range $MIN_PORT-$MAX_PORT"
  THIS_PORT=$MIN_PORT
  while [ $THIS_PORT -le $MAX_PORT ]; do
    FREE=`netstat -an | egrep "LISTEN *$" | grep $THIS_PORT`
    if [ "$FREE" = "" ]; then
      echo " - netstat says $THIS_PORT is free, checking it's not reserved"
      for RESERVED_PORT in $RESERVED_PORT_LIST; do
        if [ "$RESERVED_PORT" = "$THIS_PORT" ]; then
          echo " - docker says $THIS_PORT is reserved"
          PORT_RESERVED="TRUE"
        else
          PORT_RESERVED="FALSE"
        fi
      done
      if [ ! "$PORT_RESERVED" = "TRUE" ]; then
        break
      else
        THIS_PORT=$((THIS_PORT+1))
        sleep 0
      fi
    else
      THIS_PORT=$((THIS_PORT+1))
      sleep 0
    fi
  done

  # testing - don't run this for develop to see what happens if port is not avaiable
  if [ $THIS_PORT -gt $MAX_PORT ]; then
    if [ ! -z "$VS_CONTAINER_BASE_PORT_OVERRIDE" ] && [ ! "$PORT_RESERVED" = "TRUE" ]; then
      FAIL_REASON="OVERRIDE PORT $VS_CONTAINER_BASE_PORT_OVERRIDE is in use, setting PORT to NULL"
    elif [ ! -z "$VS_CONTAINER_BASE_PORT_OVERRIDE" ] && [ "$PORT_RESERVED" = "TRUE" ]; then
      FAIL_REASON="OVERRIDE PORT $VS_CONTAINER_BASE_PORT_OVERRIDE is reserved, setting PORT to NULL"
    else  
      FAIL_REASON="port scan reached $MAX_PORT, no ports are free, setting PORT to NULL"
    fi
    THIS_PORT=NULL
    SAFE_TO_PROCEED=FALSE
    echo " - $FAIL_REASON"
  else
    VS_CONTAINER_BASE_PORT=$THIS_PORT
    echo "VS_CONTAINER_BASE_PORT set to $VS_CONTAINER_BASE_PORT"
  fi
  echo ""
}

# search for latest Hippo distribution files if HIPPO_LATEST is not already set
findHippoArtifact() {
  if [ ! "$SAFE_TO_PROCEED" = "FALSE" ]; then
    if [ -z $HIPPO_LATEST ]; then
      # search in $WORKSPACE/target/ for files matching "*.tar.gz"
      echo "searching for latest Hippo distribution files in $WORKSPACE/target"
      HIPPO_LATEST=`ls -alht $WORKSPACE/target/*.tar.gz | head -1 | awk '{print $9}'` 2>&1 > /dev/null
      if [ -z "$HIPPO_LATEST" ]; then
        # recursive search in $WORKSPACE/ for files matching "*.tar.gz"
        echo "no archive found in $WORKSPACE/target/, widening search"
        HIPPO_LATEST=`find $WORKSPACE/ -name "*.tar.gz" | head -1`
      fi
      if [ ! -z "$HIPPO_LATEST" ]; then
        echo " - found $HIPPO_LATEST"
      else
        HIPPO_LATEST=NULL
        SAFE_TO_PROCEED=FALSE
        FAIL_REASON="no archive found in $WORKSPACE, giving up"
        echo " - $FAIL_REASON"
      fi
    else
      echo "search for distribution files will not be run as HIPPO_LATEST was overridden to $HIPPO_LATEST"
    fi
  else
    echo ""
    echo "search for distribution files will not be run due to previous failures"
  fi
  echo ""
}

# package SSR app files
packageSSRArtifact() {
  if [ "$VS_SSR_PROXY_ON" = "TRUE" ] && [ ! "$SAFE_TO_PROCEED" = "FALSE" ]; then
    echo "packaging SSR application"
    if [ -d "$VS_FRONTEND_DIR" ]; then
      tar -zcf $VS_SSR_PACKAGE_TARGET/$VS_SSR_PACKAGE_NAME $VS_SSR_PACKAGE_SOURCE
      RETURN_CODE=$?; echo $RETURN_CODE
      if [ ! "$RETURN_CODE" = "0" ]; then
        SAFE_TO_PROCEED=FALSE
        FAIL_REASON="Failed to package SSR app from $VS_FRONTEND_DIR, command exited with $RETURN_CODE"
      fi
    fi
  fi
}

# create Docker container
containerCreateAndStart() {
  if [ ! "$SAFE_TO_PROCEED" = "FALSE" ]; then
    sleep 5
    if [ "$VS_SSR_PROXY_ON" = "TRUE" ]; then
      VS_CONTAINER_EXPOSE_PORT=$VS_SSR_APP_PORT
      else
      VS_CONTAINER_EXPOSE_PORT=$VS_BRXM_TOMCAT_PORT
    fi
    echo ""
    echo "about to create a new Docker container with:"
    VS_DOCKER_CMD='docker run -d --name '$VS_CONTAINER_NAME' -p '$VS_CONTAINER_BASE_PORT':'$VS_CONTAINER_EXPOSE_PORT' --env VS_SSR_PROXY_ON='$VS_SSR_PROXY_ON' --env VS_SSR_PACKAGE_NAME='$VS_SSR_PACKAGE_NAME' '$VS_DOCKER_IMAGE_NAME' /bin/bash -c "/usr/local/bin/vs-mysqld-start && /usr/local/bin/vs-hippo && while [ ! -f /home/hippo/tomcat_8080/logs/cms.log ]; do echo no log; sleep 2; done; tail -f /home/hippo/tomcat_8080/logs/cms.log"'
    echo " - $VS_DOCKER_CMD"
    eval $VS_DOCKER_CMD
    RETURN_CODE=$?; echo $RETURN_CODE
    if [ ! "$RETURN_CODE" = "0" ]; then
      SAFE_TO_PROCEED=FALSE
      FAIL_REASON="Docker failed to start container $VS_CONTAINER_NAME, command exited with $RETURN_CODE"
    fi
    sleep 10
    else
    echo ""
    echo "container will not be started due to previous failures"
  fi
  echo ""
}

# copy build artefacts to container
containerCopyHippoArtifact() {
    if [ ! "$SAFE_TO_PROCEED" = "FALSE" ]; then
    echo ""
    echo "about to copy $HIPPO_LATEST to container $VS_CONTAINER_NAME:/home/hippo"
    docker cp $HIPPO_LATEST $VS_CONTAINER_NAME:/home/hippo
    RETURN_CODE=$?; echo $RETURN_CODE
    if [ ! "$RETURN_CODE" = "0" ]; then
      SAFE_TO_PROCEED=FALSE
      FAIL_REASON="Docker failed to run cp command against $VS_CONTAINER_NAME, command exited with $RETURN_CODE"
    fi
    else
    echo ""
    echo "docker cp will not be run due to previous failures"
  fi
}

containerCopySSRArtifact() {
  if [ "$VS_SSR_PROXY_ON" = "TRUE" ] && [ ! "$SAFE_TO_PROCEED" = "FALSE" ]; then
    echo ""
    echo "about to copy $VS_SSR_PACKAGE_NAME to container $VS_CONTAINER_NAME:/home/hippo"
    docker cp $VS_SSR_PACKAGE_TARGET/$VS_SSR_PACKAGE_NAME $VS_CONTAINER_NAME:/home/hippo
    RETURN_CODE=$?; echo $RETURN_CODE
    if [ ! "$RETURN_CODE" = "0" ]; then
      SAFE_TO_PROCEED=FALSE
      FAIL_REASON="Docker failed to run cp command against $VS_CONTAINER_NAME, command exited with $RETURN_CODE"
    fi
    elif [ ! "$VS_SSR_PROXY_ON" = "TRUE" ] && [ ! "$SAFE_TO_PROCEED" = "FALSE" ]; then
    echo ""
    echo "docker cp of VS_SSR_PACKAGE_NAME:$VS_SSR_PACKAGE_NAME will not be run due VS_SSR_PROXY_ON:$VS_SSR_PROXY_ON"
    else
    echo ""
    echo "docker cp will not be run due to previous failures"
  fi
}

containerStartHippo() {
  if [ ! "$SAFE_TO_PROCEED" = "FALSE" ]; then
    echo ""
    echo "about to execute "/usr/local/bin/vs-hippo nodb" in container $VS_CONTAINER_NAME"
    docker exec -d $VS_CONTAINER_NAME /usr/local/bin/vs-hippo nodb
    RETURN_CODE=$?; echo $RETURN_CODE
    if [ ! "$RETURN_CODE" = "0" ]; then
      SAFE_TO_PROCEED=FALSE
      FAIL_REASON="Docker failed to run exec command in $VS_CONTAINER_NAME, command exited with $RETURN_CODE"
    fi
  else
    echo ""
    echo "docker exec will not be run due to previous failures"
  fi
}

createReport() {
  if [ ! "$SAFE_TO_PROCEED" = "FALSE" ]; then
    EXIT_CODE=0
    echo ""
    echo ""
    echo "###############################################################################################################################"
    echo ""
    echo "The site instance for branch $GIT_BRANCH should now be available in a few moments on $NODE_NAME - $VS_HOST_IP_ADDRESS at:"
    echo "  - $VS_PROXY_SERVER_SCHEME://$VS_PROXY_SERVER_FQDN/?vs_brxm_host=$VS_HOST_IP_ADDRESS&vs_brxm_port=$VS_CONTAINER_BASE_PORT&vs_brxm_http_host=$VS_BRXM_INSTANCE_HTTP_HOST"
    echo ""
    echo "The CMS for the instance should now be available at:"
    echo "  - $VS_PROXY_SERVER_SCHEME://$VS_PROXY_SERVER_FQDN/cms/?vs_brxm_host=$VS_HOST_IP_ADDRESS&vs_brxm_port=$VS_CONTAINER_BASE_PORT&vs_brxm_http_host=$VS_BRXM_INSTANCE_HTTP_HOST"
    echo "and the Console at:"
    echo "  - $VS_PROXY_SERVER_SCHEME://$VS_PROXY_SERVER_FQDN/cms/console/?vs_brxm_host=$VS_HOST_IP_ADDRESS&vs_brxm_port=$VS_CONTAINER_BASE_PORT&vs_brxm_http_host=$VS_BRXM_INSTANCE_HTTP_HOST"
    echo ""
    echo "To clear the proxy server settings between sessions either close your browser or browse to:"
    echo "  - $VS_PROXY_SERVER_SCHEME://$VS_PROXY_SERVER_FQDN/?vs_brxm_reset"
    echo ""
    echo ""
    echo "Direct Tomcat access - available only on the Web Development LAN"
    echo "  - http://$VS_HOST_IP_ADDRESS:$VS_CONTAINER_BASE_PORT/cms/"
    echo "  - http://$VS_HOST_IP_ADDRESS:$VS_CONTAINER_BASE_PORT/site/"
    echo "    -  needs a HOST header of localhost:8080 to be passed with the request"
    echo ""
    echo "###############################################################################################################################"
    echo ""
    echo ""
  else
    EXIT_CODE=127
    echo ""
    echo ""
    echo "###############################################################################################################################"
    echo ""
    echo "JOB FAILED because $FAIL_REASON"
    echo ""
    echo "###############################################################################################################################"
    echo ""
    echo ""
  fi
}
# ====/FUNCTIONS ====

# ==== RUN ====
case $METHOD in
  other)
    false
  ;;
  default)
    false
    ;;
  *)
    echo "NO METHOD SELECTED - running as docker.sh"
    defaultSettings
    reportSettings
    checkContainers
    stopContainers
    #startContainers
    deleteContainers
    deleteImages
    #getChildBranchesViaCurl
    #getBranchListViaCurl
    #getPullRequestListViaCurl
    getBranchListFromWorkspace
    getReservedPortList
    tidyContainers
    setPortRange
    findBasePort
    findHippoArtifact
    packageSSRArtifact
    containerCreateAndStart
    containerCopyHippoArtifact
    containerCopySSRArtifact
    containerStartHippo
    createReport
  ;;
esac
# ====/RUN ====
exit $EXIT_CODE
