#!/usr/bin/env groovy

def call(version='6.14.4', cl) {

  echo "Setting up NodeJS version ${version}!"
  
  if (!fileExists("${JENKINS_HOME}/.nodenv/bin/nodenv")) {
    installNodenv()
  } else {
    print "Nodenv located!"
  }

  if (!fileExists("${JENKINS_HOME}/.nodenv/versions/${version}/")) {
     installVersion("${version}")
  } else {
    print "Version already installed"    
  }

  withEnv(["PATH=${JENKINS_HOME}/.nodenv/shims:${JENKINS_HOME}/.nodenv/bin/:$PATH", "NODENV_SHELL=sh"]) {
    sh "nodenv rehash"
    sh "nodenv local ${version}"
    cl()
  }
}

def installNodenv() {
  print "Lets install Nodenv!!!"
  sh "git clone https://github.com/nodenv/nodenv.git ${JENKINS_HOME}/.nodenv"
  sh "cd ${JENKINS_HOME}/.nodenv && src/configure && make -C src"
  sh "git clone https://github.com/nodenv/node-build.git ${JENKINS_HOME}/.nodenv/plugins/node-build"
}

def installVersion(version) {
  print "Lets install required version!!!"
  sh "nodenv install ${version} -s"
}
