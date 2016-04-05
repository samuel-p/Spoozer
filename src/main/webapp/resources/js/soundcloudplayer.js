function SoundcloudPlayer(track, volume) {
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
        SC.stream('/tracks/' + track.id).then(function (player) {
            console.log(player);
            self.player = player;
            player.on('play', function () {
                if (self.started)
                    self.onResume.call(this);
                else
                    self.onStart.call(this);
                self.started = true;
            });
            player.on('pause', function () {
                self.onPause.call(this);
            });
            player.on('finish', function () {
                self.onStop.call(this);
            });
            player.setVolume(volume);
            player.play();
        });
    };

    this.stop = function () {
        if (this.player) {
            this.player.dispose();
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
        if (this.player)
            this.player.seek(value * 1000);
    };

    this.setVolume = function (volume) {
        if (this.player)
            this.player.setVolume(volume);
    };

    this.get = function () {
        if (this.player && this.player.isPlaying())
            return {
                track: track,
                trackPosition: this.player.currentTime() / 1000,
                trackLength: track.durationInMillis / 1000,
                volume: this.player.getVolume()
            };
        return {
            track: track,
            trackPosition: 0,
            trackLength: track.durationInMillis / 1000,
            volume: 1
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