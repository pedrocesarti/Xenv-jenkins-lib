:rotating_light: This project is outdated/abandoned/archived and will not be updated anymore. :rotating_light:

-------------

# Xenv-jenkins-lib

For those who are:
1. Sick managing different versions of NodeJS, Python or Ruby in your Jenkins Pipelines
2. Struggling getting NVM, RVM and virtualenv working in your pipeline
3. Or just having a bad time setting up a bunch of plugins

I HAVE EXCITING NEWS FOR YOU!!! 


Using the power of:
- [Jenkins Shared Libraries](https://jenkins.io/doc/book/pipeline/shared-libraries/)
- [nodenv](https://github.com/nodenv/nodenv)
- [pyenv](https://github.com/pyenv/pyenv)
- [rbenv](https://github.com/rbenv/rbenv)

I created this small library that allow you to import to your pipeline  methods that do all the heavy lifting on installing the tools and different versions of NodeJS, Ruby and Python (like meta-runners).

The main goal is something simple like this:
```groovy
script {
    withNodenv('0.12.14') {
      sh "node --version"     \\ Output:  v0.12.14
    }
}
```

Have a look on some [Examples](./examples/).

### "Installing"
You have couple options to import the module and start using you can import dinamically on each of your pipelines or you can import globally in your Jenkins server and all pipelines can import just as a single `Library`.

#### Importing dynamically
You can dynamically import the library adding this snippet at the beginning of your Jenkins file:
```groovy
library identifier: 'xenv-jenkins-lib@master', 
        retriever: modernSCM([$class: 'GitSCMSource', 
        remote: 'https://github.com/pedrocesar-ti/Xenv-jenkins-lib.git'])
```

#### Importing via Global Configuration
With the [Global Shared Libraries](https://jenkins.io/doc/book/pipeline/shared-libraries/#global-shared-libraries), we can basically import this lib globaly to be used in any pipeline. 
<p align="center"><img src="https://dl.dropboxusercontent.com/s/c74sr7mqgyqoo9x/Screen%20Shot%202018-09-23%20at%207.04.52%20AM.png"Jenkins Global"></p>

On your _Jenkinsfile_ you only need:
```groovy
@Library('xenv-jenkins-lib') _
```

### Using
Please have a look in the [examples](./examples/) folder to see how to use the methods exported by this lib.

#### withNodenv(version, method)
* _version_ is going to specify what version of NodeJS you want to run your code, default version is _6.14.4_.
* _method_ is going to define if the version should be deleted (clean) after used, the default method is to keep the installed versions, so you don't need to re-install every time.

```groovy
  script {
    withNodenv.purgeAll('nodenv')
    withNodenv('6.14.4', 'clean') {
      sh "node --version"    // v6.14.4
    }
  }
```
> withNodenv.purgeAll('nodenv') - this method helps deleting all versions of NodeJS.

#### withPyenv(version, method)
* _version_ is going to specify what version of Python you want to run your code, default Python version is _3.7.0_.
* _method_ is going to define if the version should be deleted (clean) after used, the default method is to keep the installed versions, so you don't need to re-install every time.

```groovy
  script {
    withPyenv() {
      sh "python --version"    // Python 3.7.0
    }
  }
```
> withPyenv.purgeAll('pyenv') - this method helps deleting all versions of Python.

#### withRbenv(version, method)
* _version_ is going to specify what version of Ruby you want to run your code, default Ruby version is _2.5.1_.
* _method_ is going to define if the version should be deleted (clean) after used, the default method is to keep the installed versions, so you don't need to re-install every time.

```groovy
  script {
    withRbenv('2.3.0') {
      sh "ruby --version"    // ruby 2.3.0p0
    }
  }
```
> withRbenv.purgeAll('rbenv') - this method helps deleting all versions of Ruby.

Enjoy! :)
