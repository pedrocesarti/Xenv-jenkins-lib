#!/usr/bin/env groovy

def call(version='2.5.1', method=null, cl) {
  def metarunner = 'rbenv'
  def utils = new info.pedrocesar.utils()

  print "Setting up Ruby version ${version}!"
  
  if (!fileExists("$HOME/.${metarunner}/bin/${metarunner}")) {
    installRbenv(metarunner)
    sh "git clone https://github.com/${metarunner}/ruby-build.git $HOME/.${metarunner}/plugins/ruby-build"
  }

  if (!fileExists("$HOME/.${metarunner}/versions/${version}/")) {
    if (!fileExists("$HOME/.${metarunner}/plugins/ruby-build/share/ruby-build/${version}")) {
      sh "cd $HOME/.${metarunner}/plugins/ruby-build && git pull && cd -"
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
    print "Removing Ruby ${version}!!!"
    withEnv(["PATH=$HOME/.${metarunner}/bin/:$PATH"]) {
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
