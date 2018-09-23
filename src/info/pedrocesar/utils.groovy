#!/usr/bin/groovy
package info.pedrocesar
import com.cloudbees.groovy.cps.NonCPS

@NonCPS
def purgeAllVersions(String metarunner) {
  File directory = new File("${JENKINS}/.${metarunner}/versions/")

  directory.listFiles().each{
    it.deleteDir()
  } 
}

@NonCPS
def deleteVersion(String metarunner, String version) {
  File directory = new File("${JENKINS}/.${metarunner}/versions/${version}")
  
  directory.deleteDir()
}

@NonCPS
def installVersion(String metarunner, String version) {
  withEnv(["PATH=${JENKINS_HOME}/.${metarunner}/bin/:$PATH"]) {
    sh "env"
    sh "${metarunner} install ${version}"
  }
}
