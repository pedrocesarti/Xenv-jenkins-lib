#!/bin/bash

git clone https://github.com/nodenv/nodenv.git ~/.nodenv
cd ~/.nodenv && src/configure && make -C src
export PATH="$HOME/.nodenv/bin:$PATH"
eval "$(nodenv init -)"
git clone https://github.com/nodenv/node-build.git $(nodenv root)/plugins/node-build
nodenv install 0.12.14 -s
nodenv local 0.12.14
node --version && npm --version

git clone https://github.com/rbenv/rbenv.git ~/.rbenv
cd ~/.rbenv && src/configure && make -C src
export PATH="$HOME/.rbenv/bin:$PATH"
eval "$(rbenv init -)"
git clone https://github.com/rbenv/ruby-build.git "$(rbenv root)"/plugins/ruby-build
rbenv install 2.3.0
rbenv local 2.3.0
ruby --version && gem --version

git clone https://github.com/pyenv/pyenv.git ~/.pyenv
cd ~/.pyenv && src/configure && make -C src
export PATH="$HOME/.pyenv/bin:$PATH"
eval "$(pyenv init -)"
pyenv install 3.6.0
pyenv local 3.6.0
python --version && pip --version

