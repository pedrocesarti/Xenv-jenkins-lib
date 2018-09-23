#!/usr/bin/groovy
package info.pedrocesar
import com.cloudbees.groovy.cps.NonCPS

@NonCPS
def installMetarunner(String metarunner){

  if (metarunner == 'nodenv') {
    def buildname = 'node'
  } else if (metarunner == 'rbenv') {
    def buildname = 'ruby'
  }

  sh """ 
  git clone https://github.com/${metarunner}/${metarunner}.git ${JENKINS_HOME}/.${metarunner}
  cd ${JENKINS_HOME}/.${metarunner}
  src/configure --without-ssl && make -C src
  """
  // git clone https://github.com/${metarunner}/${buildname}-build.git ${JENKINS_HOME}/.${metarunner}/plugins/${buildname}-build
}

@NonCPS
def installVersion(String metarunner, String version) {
    sh "${metarunner} install ${version}"
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
