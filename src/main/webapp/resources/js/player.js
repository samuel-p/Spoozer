angular.module('ngPlayer', []).service('$player', [function () {
    this.scope = angular.element($('.player')).scope();
    this.queue = [];
    this.index = 0;
    this.playing = null;
    this.running = null;
    this.loop = false;

    this.play = function (playlist) {
        this.queue = playlist.tracks;
        this.index = 0;
        this.playCurrent();
    };

    this.pause = function () {
        if (angular.isDefined(this.playing)) {
            this.playing.pause();
        }
    };

    this.resume = function () {
        if (angular.isDefined(this.playing)) {
            this.playing.play();
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
            format: 'mp3',
            onplay: function () {
                self.setRunning(true);
                self.showPlayerView();
                self.scope.update(track);
            },
            onpause: function () {
                self.setRunning(false);
            },
            onend: function () {
                self.setRunning(false);
                if (self.index == self.queue.length - 1 && !self.loop) {
                    self.hidePlayerView();
                } else {
                    self.next();
                }
            }
        });
        this.playing.play();
    };

    this.setRunning = function(playing) {
        if (playing) {
            var self = this;
            this.running = setInterval(function() {
                self.scope.updateBuffer(self.get());
            }, 100);
        } else {
            clearInterval(this.running);
            this.running = null;
        }
    };

    this.showPlayerView = function () {
        this.scope.show();
    };

    this.hidePlayerView = function () {
        this.scope.hide();
    };

    this.setPosition = function (value) {
        this.playing.pos(value);
    };

    this.setVolume = function (value) {
        Howler.volume(value);
        //this.playing.volume(value);
    };

    this.get = function () {
        if (this.queue.length == 0) {
            return null;
        }
        var track = this.queue[this.index];
        return {
            trackTitle: track.title,
            trackPosition: this.playing.pos(),
            trackLength: this.playing._duration,
            volume: this.playing.volume()
        };
    };
}]);