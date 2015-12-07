angular.module('ngPlayer', []).service('$player', [function () {
    this.listeners = {
        'change': [],
        'update': [],
        'start': [],
        'stop': []
    };
    this.queue = [];
    this.index = 0;
    this.playing = null;
    this.running = null;
    this.loop = false;
    this.volume = 1;

    this.play = function (playlist) {
        this.stop();
        this.queue = playlist.tracks;
        this.index = 0;
        this.playCurrent();
    };

    this.pause = function () {
        if (this.playing != null) {
            this.playing.pause();
        }
    };

    this.resume = function () {
        if (this.playing != null) {
            this.playing.play();
        }
    };

    this.stop = function () {
        if (this.playing != null) {
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
        if (this.playing != null) {
            this.playing.stop();
        }
        var track = this.queue[this.index];
        var self = this;
        this.playing = new Howl({
            urls: [track.url],
            buffer: true,
            volume: this.volume,
            format: 'mp3',
            onplay: function () {
                if (this.running == null) {
                    self.firePlayerEvent('start');
                }
                self.setRunning(true);
                self.firePlayerEvent('change');
            },
            onpause: function () {
                self.setRunning(false);
            },
            onend: function () {
                self.setRunning(false);
                if (self.index == self.queue.length - 1 && !self.loop) {
                    self.firePlayerEvent('stop');
                } else {
                    self.next();
                }
            }
        });
        this.playing.play();
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
        this.playing.pos(value);
    };

    this.setVolume = function (value) {
        this.volume = value;
        this.playing.volume(value);
        this.firePlayerEvent('change');
    };

    this.get = function () {
        if (this.queue.length == 0) {
            return null;
        }
        var track = this.queue[this.index];
        return {
            track: track,
            trackPosition: this.playing.pos(),
            trackLength: this.playing._duration,
            volume: this.playing.volume()
        };
    };
}]);