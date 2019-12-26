#!/usr/bin/env groovy

def call(version='3.7.0', method=null, cl) {
  def metarunner = 'pyenv'
  def utils = new info.pedrocesar.utils()

  print "Setting up Python version ${version}!"
  
  if (!fileExists("$HOME/.${metarunner}/bin/${metarunner}")) {
    installPyenv(metarunner)
  }

  if (!fileExists("$HOME/.${metarunner}/versions/${version}/")) {
    if (!fileExists("$HOME/.${metarunner}/plugins/python-build/share/python-build/${version}")) {
      sh "cd $HOME/.${metarunner} && git pull && cd -"
    }
    withEnv(["PATH=$HOME/.${metarunner}/bin/:$PATH"]) {
      utils.installVersion(metarunner, version)
    }
  }

  withEnv(["PATH=$HOME/.${metarunner}/shims:$HOME/.${metarunner}/bin/:$PATH", "NODENV_SHELL=sh"]) {
    sh "${metarunner} rehash && ${metarunner} local ${version}"
    cl()
  }

  if (method == 'clean') {
    print "Removing Python ${version}!!!"
    withEnv(["PATH=$HOME/.${metarunner}/bin/:$PATH"]) {
      utils.deleteVersion(metarunner, version)
    }
  } 
}

def installPyenv(metarunner) {
  print "Installing ${metarunner}"
  new info.pedrocesar.utils().installMetarunner(metarunner)
}

def purgeAll(metarunner) {
  print "Removing all versions of ${metarunner}"
  new info.pedrocesar.utils().purgeAllVersions(metarunner)
}
