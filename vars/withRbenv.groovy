#!/usr/bin/env groovy

def call(version='2.5.1', method=null, cl) {
  def metarunner = 'rbenv'
  def utils = new info.pedrocesar.utils()

  print "Setting up Ruby version ${version}!"
  
  if (!fileExists("${JENKINS_HOME}/.${metarunner}/bin/${metarunner}")) {
    installRbenv(metarunner)
    sh "git clone https://github.com/${metarunner}/ruby-build.git ${JENKINS_HOME}/.${metarunner}/plugins/ruby-build"
  }

  if (!fileExists("${JENKINS_HOME}/.${metarunner}/versions/${version}/")) {
    withEnv(["PATH=${JENKINS_HOME}/.${metarunner}/bin/:$PATH"]) {
      utils.installVersion(metarunner, version)
    }
  }

  withEnv(["PATH=${JENKINS_HOME}/.${metarunner}/shims:${JENKINS_HOME}/.${metarunner}/bin/:$PATH", "NODENV_SHELL=sh"]) {
    sh "${metarunner} rehash && ${metarunner} local ${version}"
    cl()
  }

  if (method == 'clean') {
    print "Removing Ruby ${version}!!!"
    withEnv(["PATH=${JENKINS_HOME}/.${metarunner}/bin/:$PATH"]) {
      utils.deleteVersion(metarunner, version)
    }
  } 
}

def installRbenv(metarunner) {
  print "Installing ${metarunner}"
  new info.pedrocesar.utils().installMetarunner(metarunner)
}

def purgeAll(metarunner) {
  print "Removing all versions of ${metarunner}"
  new info.pedrocesar.utils().purgeAllVersions(metarunner)
}
