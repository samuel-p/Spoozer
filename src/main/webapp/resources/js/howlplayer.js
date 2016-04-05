function HowlPlayer(track, volume) {
    this.player = null;
    this.onStart = function () {
    };
    this.onStop = function () {
    };
    this.onPause = function () {
    };
    this.onResume = function () {
    };
    this.started = false;

    this.start = function () {
        var self = this;
        this.player = new Howl({
            urls: [track.url],
            buffer: true,
            volume: volume,
            format: 'mp3',
            onplay: function () {
                if (self.started)
                    self.onResume.call(this);
                else
                    self.onStart.call(this);
                self.started = true;
            },
            onpause: function () {
                self.onPause.call(this);
            },
            onend: function () {
                self.onStop.call(this);
            }
        });
        this.player.play();
    };

    this.stop = function () {
        if (this.player) {
            this.player.stop();
        }
    };

    this.pause = function () {
        if (this.player) {
            this.player.pause();
        }
    };

    this.resume = function () {
        if (this.player) {
            this.player.play();
        }
    };

    this.setPosition = function (value) {
        this.playing.pos(value);
    };

    this.setVolume = function (volume) {
        this.player.volume(volume);
    };

    this.get = function () {
        return {
            track: track,
            trackPosition: this.player.pos(),
            trackLength: this.player._duration,
            volume: this.player.volume()
        };
    };

    this.setOnStart = function (onStart) {
        this.onStart = onStart;
    };

    this.setOnStop = function (onStop) {
        this.onStop = onStop;
    };

    this.setOnPause = function (onPause) {
        this.onPause = onPause;
    };

    this.setOnResume = function (onResume) {
        this.onResume = onResume;
    };
}