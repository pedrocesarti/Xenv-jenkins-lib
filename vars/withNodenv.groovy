#!/usr/bin/env groovy

def call(version='6.14.4', cl) {

  echo "Setting up NodeJS version ${version}!"
  
  if (!fileExists("${JENKINS_HOME}/.nodenv/bin/nodenv")) {
    installNodenv()
  } else {
    print "Nodenv located!"
  }

  if (!fileExists("${JENKINS_HOME}/.nodenv/versions/${version}/")) {
     installVersion()
  } else {
    print "Version already installed"    
  }

  withEnv(["PATH=${JENKINS_HOME}/.nodenv/bin/:$PATH"]) {
    sh '''
    eval "$(nodenv init -) 2>/dev/null"
    nodenv local ${version}
    '''
    cl()
  }
}

def installNodenv() {
     print "Lets install Nodenv!!!"
}

def installVersion() {
    print "Lets install required version!!!"
}
