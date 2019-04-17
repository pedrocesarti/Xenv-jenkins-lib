#!/usr/bin/groovy
package info.pedrocesar
import com.cloudbees.groovy.cps.NonCPS

@NonCPS
def installMetarunner(String metarunner){
  sh """ 
  git clone https://github.com/${metarunner}/${metarunner}.git $HOME/.${metarunner}
  cd $HOME/.${metarunner}
  src/configure --without-ssl && make -C src
  """
}

@NonCPS
def installVersion(String metarunner, String version) {
    sh "${metarunner} install ${version}"
}

@NonCPS
def deleteVersion(String metarunner, String version) {
  File directory = new File("$HOME/.${metarunner}/versions/${version}")
  
  directory.deleteDir()
}

@NonCPS
def purgeAllVersions(String metarunner) {
  File directory = new File("$HOME/.${metarunner}/versions/")

  directory.listFiles().each{
    it.deleteDir()
  }
}
