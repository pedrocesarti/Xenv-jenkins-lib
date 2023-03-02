#!/usr/bin/env groovy

def call(version='6.14.4', method=null, cl) {
  def metarunner = 'nodenv'
  def utils = new info.pedrocesar.utils()

  print "Setting up NodeJS version ${version}!"

  if (!fileExists("$HOME/.${metarunner}/bin/${metarunner}")) {
    installNodenv(metarunner)
    sh "git clone https://github.com/${metarunner}/node-build.git $HOME/.${metarunner}/plugins/node-build"
  }

  if (!fileExists("$HOME/.${metarunner}/versions/${version}/")) {
    withEnv(["PATH=$HOME/.${metarunner}/bin/:$PATH"]) {
      utils.installVersion(metarunner, version)
    }
  }

  withEnv(["PATH=$HOME/.${metarunner}/shims:$HOME/.${metarunner}/bin/:$PATH", "NODENV_SHELL=sh"]) {
    sh "if [[ ! -f $HOME/.${metarunner}/shims/.${metarunner}-shim  ]]; then ${metarunner} rehash; fi"
    sh "${metarunner} local ${version}"
    cl()
  }

  if (method == 'clean') {
    print "Removing NodeJS ${version}!!!"
    withEnv(["PATH=$HOME/.${metarunner}/bin/:$PATH"]) {
      utils.deleteVersion(metarunner, version)
    }
  }
}

def installNodenv(metarunner) {
  print "Installing ${metarunner}"
  new info.pedrocesar.utils().installMetarunner(metarunner)
}

def purgeAll(metarunner) {
  print "Removing all versions of ${metarunner}"
  new info.pedrocesar.utils().purgeAllVersions(metarunner)
}
