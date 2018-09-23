#!/usr/bin/groovy
package info.pedrocesar
import com.cloudbees.groovy.cps.NonCPS

@NonCPS
def installMetarunner(String metarunner){

  sh "git clone https://github.com/${metarunner}/${metarunner}.git ${JENKINS_HOME}/.${metarunner}"
  sh "git clone https://github.com/${metarunner}/node-build.git ${JENKINS_HOME}/.${metarunner}/plugins/node-build"

//if (metarunner != 'pyenv') {
//    print "It's not python"
//    if (metarunner == 'nodenv') {
//      print "its NODE"
//      sh "git clone https://github.com/${metarunner}/node-build.git ${JENKINS_HOME}/.${metarunner}/plugins/node-build"
//    } else if (metarunner == 'rbenv') {
//      print "It's Ruby"
//      sh "git clone https://github.com/${metarunner}/ruby-build.git ${JENKINS_HOME}/.${metarunner}/plugins/ruby-build"
//    } else {
//      print "no metarunner found it!"    
//    }
  }

  dir ("${JENKINS_HOME}/.${metarunner}") {
    sh "src/configure --without-ssl && make -C src"
  }
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
