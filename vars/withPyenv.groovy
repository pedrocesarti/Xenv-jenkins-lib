#!/usr/bin/env groovy

def call(version='3.7.0', cl) {

  print "Setting up Python version ${version}!"
  
  if (!fileExists("${JENKINS_HOME}/.pyenv/bin/pyenv")) {
    installPyenv()
  }

  if (!fileExists("${JENKINS_HOME}/.pyenv/versions/${version}/")) {
     installVersion("${version}")
  }

  withEnv(["PATH=${JENKINS_HOME}/.pyenv/shims:${JENKINS_HOME}/.pyenv/bin/:$PATH", "PYENV_SHELL=sh"]) {
    sh "pyenv rehash"
    sh "pyenv local ${version}"
    cl()
  }
}

def installPyenv() {
  print "Lets install Pyenv!!!"
  sh "git clone https://github.com/pyenv/pyenv.git ${JENKINS_HOME}/.pyenv"
  sh "cd ${JENKINS_HOME}/.pyenv && src/configure && make -C src"
}

def installVersion(version) {
  print "Lets install Python ${version}!!!"
  withEnv(["PATH=${JENKINS_HOME}/.pyenv/bin/:$PATH"]) {
    sh "pyenv install ${version}"
  }
}
