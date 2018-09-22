# Xenv-jenkins-lib

For those who are:
1. Sick managing different versions of NodeJS, Ruby or Python in your Jenkins Pipelines
2. Struggling getting NVM, RVM and virtualenv working in your pipeline
3. Or just having a bad time setting up a bunch of plugins

- I HAVE EXCITING NEWS FOR YOU!!! 


Using the power of [Jenkins Shared Libraries](https://jenkins.io/doc/book/pipeline/shared-libraries/), [Nodenv](https://github.com/nodenv/nodenv), [Rbenv](https://github.com/rbenv/rbenv) and [Pyenv](https://github.com/pyenv/pyenv) I have created small library that allow you to import small methods to your pipeline that does all the heavy lifting for you installing the tools and different versions NodeJS, Ruby and Python (like meta-runners).


Something simple like this:
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

#### Import in Global Configuration
With the [Global Shared Libraries](https://jenkins.io/doc/book/pipeline/shared-libraries/#global-shared-libraries), we can basically import this lib globaly to be used in any pipeline. 
<p align="center"><img src="https://dl.dropboxusercontent.com/s/c74sr7mqgyqoo9x/Screen%20Shot%202018-09-23%20at%207.04.52%20AM.png"Jenkins Global"></p>

```groovy
@Library('xenv-jenkins-lib') _
```

### Using
Please have a look in the [examples](./examples/) folder to see how to use the methods exported by this lib.







Enjoy! :)
