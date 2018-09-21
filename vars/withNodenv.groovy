#!/usr/bin/env groovy

def call(version='6.14.4', cl) {

  echo "Setting up NodeJS version ${version}!"
  
  if (!fileExists("${JENKINS_HOME}/.nodenv/bin/nodenv")) {
    install()
  } else {
    echo "Nodenv located!"
  }

  cl()
}

def install() {
    echo "Lets install!!!"
}
