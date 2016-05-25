#!/bin/bash
if [ "$TRAVIS_BRANCH" == "master" ]; then
  curl https://spoozer.de:12345/deploy
fi