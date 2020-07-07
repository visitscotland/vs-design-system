def DS_BRANCH = "feature/VS-955-ui-itineraries-itinerary-stops-changes-built-products"
def MAIL_TO = "jose.calcines@visitscotland.com, juanluis.hurtado@visitscotland.com, webops@visitscotland.net"

def thisAgent
if (BRANCH_NAME == "develop" && JOB_NAME == "develop.visitscotland.com-mb/develop") {
  thisAgent = "op-dev-brxwvcapp-01"
} else if (BRANCH_NAME == "develop" && JOB_NAME == "develop-nightly.visitscotland.com/develop") {
  thisAgent = "op-dev-brxwvcapp-02"
} else if (BRANCH_NAME == "develop" && JOB_NAME == "develop-stable.visitscotland.com/develop") {
  thisAgent = "op-dev-brxwvcapp-03"
} else {
  thisAgent = "docker-02"
}

import groovy.json.JsonSlurper

pipeline {
  options {buildDiscarder(logRotator(numToKeepStr: '5'))}

  agent {label thisAgent}

  environment {
    VS_SSR_PROXY_ON = 'FALSE'
    brc_stack = 'visitscotland'
    brc_environment = 'demo'
    brc_url = "https://api-${brc_stack}.onehippo.io"
  }

  tools {
    maven 'Maven 3.3.9'
    jdk 'jdk1.8.0'
  }

  stages {

//  "Checkout Design System". This stage now commented out as it's no longer required since VS-1081 - please merge this change as required but leave the block for reference
//  stage ('Checkout Design System') {
//    steps {
//      // create a directory for the checkout then run the Git command within that directory, the package.json file must be aware of this location which introduces fragility/cross-dependency, could this be improved?
//      sh 'mkdir -p design-system'
//      dir('design-system') {
//        //git branch: '${DS_BRANCH}', credentialsId: '12a55ebf-608d-4b3e-811c-e4ad04f61f43', url: 'https://bitbucket.visitscotland.com/scm/vscom/design-system.git'
//        checkout([$class: 'GitSCM', branches: [[name: "*/${DS_BRANCH}"]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'SparseCheckoutPaths', sparseCheckoutPaths:[[$class:'SparseCheckoutPath', path:'dist/']]]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '12a55ebf-608d-4b3e-811c-e4ad04f61f43',url: 'https://bitbucket.visitscotland.com/scm/vscom/design-system.git']]])
//      }
//    }
//  }

    stage ('Pre-build') {
      steps {
        sh 'printenv'
      }
    }

    stage ('Build Application') {
      when {
          expression {
            return env.BRANCH_NAME != 'feature/VS-1865-feature-environments-enhancements';
          }
      }
      steps {
        sh 'mvn -f pom.xml clean package'
      }
      post {
        success {
          //sh 'mvn -f pom.xml install -P !default'
          sh 'mvn -f pom.xml install -P dist'
          mail bcc: '', body: "<b>Notification</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> build URL: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "SUCCESS CI: Project name -> ${env.JOB_NAME}", to: "${MAIL_TO}";
        }
        failure {
          mail bcc: '', body: "<b>Notification</b><br>Project: ${env.JOB_NAME} <br>Build Number: ${env.BUILD_NUMBER} <br> build URL: ${env.BUILD_URL}", cc: '', charset: 'UTF-8', from: '', mimeType: 'text/html', replyTo: '', subject: "ERROR CI: Project name -> ${env.JOB_NAME}", to: "${MAIL_TO}";
        }
      }
    }

    stage ('Build environment'){
      steps{
        script{
          //sh 'sh ./infrastructure/scripts/docker.sh'
          sh 'sh ./infrastructure/scripts/infrastructure.sh --debug'
        }
      }
    }

    stage ('Availability notice'){
    //"input" section commented out for now - useful for when there is genuinely a need to pause for an answer
    //input{
    //  message "This environment will run until the next push is made the bitbucket repo."
    //}
      steps {
        sh 'echo "This environment will run until the next commit to bitbucket is detected."'
      }
    }

    stage ('Test brCloud Connection'){
      steps {
        script {
          // Login to get the access token
          echo "Login to brc and obtain token:"
          withCredentials([usernamePassword(credentialsId: 'brCloud', passwordVariable: 'brc_password', usernameVariable: 'brc_username')]) {
            def json = "{\"username\": \"${brc_username}\", \"password\": \"${brc_password}\"}"
            loginResult = post("${brc_url}/v3/authn/access_token", json)
          }
          echo "Login result ${loginResult}"
          String access_token = "Bearer " + parseJson(loginResult).access_token

          // Get the environment ID
          echo "Get the environments"
          environments = get("${brc_url}/v3/environments/", access_token)

          // We require an existing environment. Alternative is to delete/create one
          def environmentID = getEnvironmentID(environments, brc_environment)
          echo "Environments result: ${environments}"
          echo "Environment ID: ${environmentID}"
        }
      }
    } //end stage

  } //end stages

  post{
    aborted{
      script{
        try{
          sh ' '
        }catch(err){
          sh 'echo "an error occurred in abort script"'
        }
      }
    }
  } //end post
} //end pipeline

@NonCPS
private String get(url, access_token = null) {
   return curl("GET", url, access_token)
}

@NonCPS
private String post(url, json, access_token = null) {
   return curl("POST", url, access_token, json)
}

@NonCPS
private String curl(method, url, access_token, json = null, fileName = null, file = null, extraParams = null, contentType = "application/json") {
   return sh(script: "curl ${extraParams?:""} \
           -X ${method} '${url}' \
           ${access_token?"-H 'Authorization: ${access_token}'":""} \
           -H 'Content-Type: ${contentType}' \
           ${json?"-d '${json}'":""} \
           ${(fileName && file)?"-F '${fileName}=@${file}'":""}",
           returnStdout: true)
}

@NonCPS
def parseJson(text) {
   return new JsonSlurper().parseText(text)
}

@NonCPS
def getEnvironmentID(environments, brc_environment) {
   result = null
   parseJson(environments).items.each() { env ->
       if(env.name.toString() == brc_environment) {
           result = env.id
       }
   }
   return result
}
