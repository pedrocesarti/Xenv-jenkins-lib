#!/usr/bin/env groovy

def call(version='2.5.1', cl) {

  print "Setting up Ruby version ${version}!"
  
  if (!fileExists("${JENKINS_HOME}/.rbenv/bin/rbenv")) {
    installRbenv()
  }

  if (!fileExists("${JENKINS_HOME}/.rbenv/versions/${version}/")) {
     installVersion("${version}")
  }

  withEnv(["PATH=${JENKINS_HOME}/.rbenv/shims:${JENKINS_HOME}/.rbenv/bin/:$PATH", "RBENV_SHELL=sh"]) {
    sh "rbenv rehash"
    sh "rbenv local ${version}"
    cl()
  }
}

def installRbenv() {
  print "Lets install Rbenv!!!"
  sh "git clone https://github.com/rbenv/rbenv.git ${JENKINS_HOME}/.rbenv"
  sh "cd ${JENKINS_HOME}/.rbenv && src/configure && make -C src"
  sh "git clone https://github.com/rbenv/ruby-build.git ${JENKINS_HOME}/.rbenv/plugins/ruby-build"
}

def installVersion(version) {
  print "Lets install Ruby ${version}!!!"
  withEnv(["PATH=${JENKINS_HOME}/.rbenv/bin/:$PATH"]) {
    sh "rbenv install ${version}"
  }
}
