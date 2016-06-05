angular.module('ngPlayer', []).service('$player', [function () {
    this.listeners = {
        'change': [],
        'update': [],
        'start': [],
        'stop': [],
        'changeTrack': []
    };
    this.queue = [];
    this.index = 0;
    this.playing = null;
    this.running = null;
    this.loop = true;
    this.volume = 1;
    this.random = false;
    this.repeat = false;

    this.play = function (playlist, index, loadOnly) {
        this.queue = playlist.tracks;
        this.index = index | 0;
        this.playCurrent(loadOnly);
    };

    this.pause = function () {
        if (this.playing) {
            this.playing.pause();
        }
    };

    this.resume = function () {
        if (this.playing) {
            this.playing.resume();
        }
    };

    this.stop = function () {
        if (this.playing) {
            this.playing.stop();
        }
    };

    this.changeRandom = function () {
        this.random = !this.random;
        this.firePlayerEvent('change');
    };

    this.changeRepeat = function () {
        this.repeat = !this.repeat;
        this.firePlayerEvent('change');
    };

    this.setRandomMode = function (random) {
        this.random = random;
    };

    this.setRepeatMode = function (repeat) {
        this.repeat = repeat;
    };

    this.next = function () {
        if (this.repeat) {
            // do nothing
        } else if (this.random) {
            var help = this.index;
            while (this.index == help) {
                this.index = Math.floor(Math.random() * this.queue.length);
            }
        } else {
            this.index = (this.index + 1) % this.queue.length;
        }
        this.playCurrent();
    };

    this.previous = function () {
        this.index = (this.index - 1) % this.queue.length;
        this.playCurrent();
    };

    this.isPlaying = function () {
        return this.running != null;
    };

    this.playCurrent = function (loadOnly) {
        if (this.playing) {
            this.playing.stop();
        }
        var track = this.queue[this.index];
        if (track.service == 'SOUNDCLOUD') {
            this.playing = new SoundcloudPlayer(track, this.volume, loadOnly);
        } else {
            this.playing = new HowlPlayer(track, this.volume, loadOnly);
        }
        var self = this;
        this.playing.setOnStart(function () {
            if (self.running == null) {
                self.firePlayerEvent('start');
            }
            self.firePlayerEvent('changeTrack');
            self.setRunning(true);
            self.firePlayerEvent('change');
        });
        this.playing.setOnPause(function () {
            self.setRunning(false);
        });
        this.playing.setOnResume(function () {
            self.setRunning(true);
            self.firePlayerEvent('change');
        });
        this.playing.setOnStop(function () {
            self.setRunning(false);
            if (self.index == self.queue.length - 1 && !self.loop) {
                self.firePlayerEvent('stop');
            } else {
                self.next();
            }
        });
        this.playing.start();
    };

    this.bind = function (event, listener) {
        if (event in this.listeners) {
            this.listeners[event].push(listener);
        }
    };

    this.unbind = function (event, listener) {
        if (event in this.listeners) {
            var index = this.listeners[event].indexOf(listener);
            if (index > -1) {
                this.listeners[event].splice(index, 1);
            }
        }
    };

    this.setRunning = function (playing) {
        if (playing) {
            var self = this;
            this.running = setInterval(function () {
                self.firePlayerEvent('update');
            }, 100);
        } else {
            clearInterval(this.running);
            this.running = null;
        }
    };

    this.firePlayerEvent = function (event) {
        if (event in this.listeners) {
            var self = this;
            this.listeners[event].forEach(function (listener) {
                listener.call(self, self.get());
            });
        }
    };

    this.setPosition = function (value) {
        if (this.playing)
            this.playing.setPosition(value);
    };

    this.setVolume = function (value) {
        this.volume = value;
        if (this.playing)
            this.playing.setVolume(value);
        this.firePlayerEvent('change');
    };

    this.get = function () {
        if (this.playing) {
            return this.playing.get();
        }
        return {
            volume: this.volume
        };
    };

    this.isRepeat = function () {
        return this.repeat;
    };
    this.isRandom = function () {
        return this.random;
    };
}]);