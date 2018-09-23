#!/usr/bin/groovy

package info.pedrocesar
import com.cloudbees.groovy.cps.NonCPS


@NonCPS
def purgeAllVersions(metarunner=null) {
  File directory = new File("${JENKINS}/.${metarunner}/versions/")

  directory.listFiles().each{
    it.deleteDir()
  } 
}

@NonCPS
def deleteVersion(metarunner=null, version=null) {
  File directory = new File("${JENKINS}/.${metarunner}/versions/${version}")
  
  directory.deleteDir()
}

@NonCPS
def installVersion(metarunner=null, version=null) {
  print "Lets install ${metarunner} ${version}!!!"
  withEnv(["PATH=${JENKINS_HOME}/.${metarunner}/bin/:$PATH"]) {
    sh "${metarunner} install ${version}"
  }
}
