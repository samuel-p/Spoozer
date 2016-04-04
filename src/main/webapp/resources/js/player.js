angular.module('ngPlayer', []).service('$player', [function () {
    this.listeners = {
        'change': [],
        'update': [],
        'start': [],
        'stop': [],
        'changeTrack':[]
    };
    this.queue = [];
    this.index = 0;
    this.playing = null;
    this.running = null;
    this.loop = false;
    this.volume = 1;

    this.play = function (playlist) {
        this.queue = playlist.tracks;
        this.index = 0;
        this.playCurrent();
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

    this.next = function () {
        this.index = (this.index + 1) % this.queue.length;
        this.playCurrent();
    };

    this.previous = function () {
        this.index = (this.index - 1) % this.queue.length;
        this.playCurrent();
    };

    this.isPlaying = function () {
        return this.running != null;
    };

    this.playCurrent = function () {
        if (this.playing) {
            this.playing.stop();
        }
        console.log(this.index);
        var track = this.queue[this.index];
        if (track.service == 'SOUNDCLOUD') {
            this.playing = new SoundcloudPlayer(track, this.volume);
        } else {
            this.playing = new HowlPlayer(track, this.volume);
        }
        var self = this;
        this.playing.setOnStart(function () {
            if (this.running == null) {
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
        return null;
    };
}]);