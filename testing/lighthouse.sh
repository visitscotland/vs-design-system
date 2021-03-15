#!/bin/bash

# ==== TO-DO ====
# gp: update post-test check section to send the email - add more detail
# gp: remove lighthouse failure notification from Jenkinsfile
# gp: use VS variables exported from infrastructure script to 
# ====/TO-DO ====

VS_VS_LAST_ENV=vs-last-env
source $WORKSPACE/$VS_VS_LAST_ENV
echo "==== selected VS environment variables ===="
set | egrep "^(VS_)"
echo "====/selected VS environment variables ===="
set -x
VS_PORT=`cat env_port.txt`
VS_HOST=`cat env_host.txt`
rm -rf $(pwd)/frontend/.lighthouseci
mkdir frontend/.lighthouseci

docker pull hemanthsridhar-docker-lhci-server-center.bintray.io/lhci:4.0
docker rm -f lhci

#create a docker container whose name is LHCI and shared memory size #of 512.
docker run -v "$(pwd)/frontend":/usr/src -w /usr/src  -d --shm-size=512m -it --name lhci hemanthsridhar-docker-lhci-server-center.bintray.io/lhci:4.0

docker exec -i lhci sh -c 'cat > ./extra.json << EOF
{
    "Cookie" : "vs_brxm_host='$VS_HOST'; vs_brxm_port='$VS_PORT'; vs_brxm_http_host=localhost"
}
EOF'

docker exec -i lhci sh -c 'rm -rf frontend/.lighthouseci/*'

#Run Lighthouse CI against the container named lhci
docker exec -i lhci sh -c \
'lhci autorun --config="./lighthouse/lhci-desktop.json"' && LH_DESKTOP_RESULT=true || LH_DESKTOP_RESULT=false

docker exec -i lhci sh -c \
'lhci autorun --config="./lighthouse/lhci-mobile.json"' && LH_MOBILE_RESULT=true || LH_MOBILE_RESULT=false

if [ $LH_DESKTOP_RESULT = false ] || [ $LH_MOBILE_RESULT = false ]
then
  exit 1
fi

#teardown
docker rm -f lhci

