#!/usr/bin/env groovy

def call(version='6.14.4', method=null, cl) {
  def metarunner = 'nodenv'
  def utils = new info.pedrocesar.utils()

  print "Setting up NodeJS version ${version}!"
  
  if (!fileExists("${JENKINS_HOME}/.${metarunner}/bin/${metarunner}")) {
    installNodenv()
  }

  if (!fileExists("${JENKINS_HOME}/.${metarunner}/versions/${version}/")) {
     print "Installing NodeJS ${version}!!!"
     withEnv(["PATH=${JENKINS_HOME}/.${metarunner}/bin/:$PATH"]) {
       utils.installVersion(metarunner, version)
     }
  }

  withEnv(["PATH=${JENKINS_HOME}/.${metarunner}/shims:${JENKINS_HOME}/.${metarunner}/bin/:$PATH", "NODENV_SHELL=sh"]) {
    sh "${metarunner} rehash && ${metarunner} local ${version}"
    cl()
  }

  if (method == 'clean') {
    print "Removing NodeJS ${version}!!!"
    withEnv(["PATH=${JENKINS_HOME}/.${metarunner}/bin/:$PATH"]) {
      utils.deleteVersion(metarunner, version)
    }
  } 
}

def installNodenv() {
  new info.pedrocesar.utils().installMetarunner(metarunner)
}

def purgeAll() {
  utils.purgeAllVersions(metarunner)
}
