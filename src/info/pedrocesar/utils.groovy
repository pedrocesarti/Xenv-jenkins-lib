#!/usr/bin/groovy
package info.pedrocesar
import com.cloudbees.groovy.cps.NonCPS

@NonCPS
def installMetarunner(String metarunner){
  sh "git clone https://github.com/${metarunner}/${metarunner}.git ${JENKINS_HOME}/.${metarunner}"

  if (metarunner != 'pyenv') {
    if (metarunner == 'nodenv') {
      sh "git clone https://github.com/${metarunner}/node-build.git ${JENKINS_HOME}/.${metarunner}/plugins/node-build"
    } else if (metarunner == 'rbenv') {
      sh "git clone https://github.com/${metarunner}/ruby-build.git ${JENKINS_HOME}/.${metarunner}/plugins/ruby-bu    ild"
    }
  }

  dir ("${JENKINS_HOME}/.${metarunner}") {
    sh "src/configure --without-ssl && make -C src"
  }
}

@NonCPS
def installVersion(String metarunner, String version) {
  print "${metarunner} install ${version}"
  withEnv(["PATH=${JENKINS_HOME}/.${metarunner}/bin/:$PATH"]) {
    sh "env"
    sh "${metarunner} install ${version}"
  }
}

@NonCPS
def deleteVersion(String metarunner, String version) {
  File directory = new File("${JENKINS_HOME}/.${metarunner}/versions/${version}")
  
  directory.deleteDir()
}

@NonCPS
def purgeAllVersions(String metarunner) {
  File directory = new File("${JENKINS_HOME}/.${metarunner}/versions/")

  directory.listFiles().each{
    it.deleteDir()
  }
}
