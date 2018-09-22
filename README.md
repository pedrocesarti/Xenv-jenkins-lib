# Xenv-jenkins-lib

For those who are:
1. Sick managing different versions of NodeJS, Ruby or Python in your Jenkins Pipelines
2. Struggling getting NVM, RVM and virtualenv working in your pipeline
3. Or just having a bad time setting up a bunch of plugins

I have exciting news for you!!! Using the power of [Jenkins Shared Libraries](https://jenkins.io/doc/book/pipeline/shared-libraries/), [Nodenv](https://github.com/nodenv/nodenv), [Rbenv](https://github.com/rbenv/rbenv) and [Pyenv](https://github.com/pyenv/pyenv) I have created small library that allow you to import small methods to your pipeline that does all the heavy lifting for you installing the tools and different versions NodeJS, Ruby and Python (like meta-runners).

Something simple like this:
```groovy
script {
    withNodenv('0.12.14') {
      sh "node --version"     \\ Output:  v0.12.14
    }
}
```

