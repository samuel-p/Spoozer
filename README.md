# Spoozer
[![Build Status](https://travis-ci.org/saphijaga/Spoozer.svg?branch=master)](https://travis-ci.org/saphijaga/Spoozer)
[![codecov](https://codecov.io/gh/saphijaga/Spoozer/branch/master/graph/badge.svg)](https://codecov.io/gh/saphijaga/Spoozer)
[![Quality Gate](http://sonarqube.it.dh-karlsruhe.de/api/badges/gate?key=de.saphijaga.spoozer%3ASpoozer)](http://sonarqube.it.dh-karlsruhe.de/overview?id=de.saphijaga.spoozer%3ASpoozer)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6ea8957eda914b439a893224417eef74)](https://www.codacy.com/app/samuel-philipp/Spoozer?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=saphijaga/Spoozer&amp;utm_campaign=Badge_Grade)

Project for Softwareengeneering

Installation:
Wer das Projekt lokal installieren möchte muss zunächst Docker installieren.
In Docker folgenden Befehl ausführen:
$docker run --rm -p 8888:8080 saphijaga/spoozer

Für schönere Ausgabe:
$docker run -it --rm -p 8888:8080 saphijaga/spoozer

Der Server startet sich unter folgender Adresse:
Bei Linux: localhost:8888
Bei Mac/Windows: docker-machine-ip:8888
Die IP der Docker-Machine lässt sich mit folgendem Befehl herausfinden:
docker-machine ip