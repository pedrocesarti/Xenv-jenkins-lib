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
