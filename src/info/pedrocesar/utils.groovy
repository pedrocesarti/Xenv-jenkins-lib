#!/usr/bin/groovy
package info.pedrocesar
import com.cloudbees.groovy.cps.NonCPS

@NonCPS
def installMetarunner(String metarunner){

  if (metarunner == 'nodenv') {
    def builder = 'node'
  } else if (metarunner == 'rbenv') {
    def builder = 'ruby'
  }

  sh """ 
  git clone https://github.com/${metarunner}/${metarunner}.git ${JENKINS_HOME}/.${metarunner}
  cd ${JENKINS_HOME}/.${metarunner}
  src/configure --without-ssl && make -C src
  """
  sh "git clone https://github.com/${metarunner}/${builder}-build.git ${JENKINS_HOME}/.${metarunner}/plugins/${builder}-build"
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
