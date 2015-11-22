app.controller('PlayerCtrl', function ($player, $scope) {
    var volumeSlider = new PlayerSlider($('.volume-slider'));
    volumeSlider.change(function() {
        $player.setVolume(volumeSlider.get() / 100);
    });
    $scope.pauseTrack = function () {
        $player.pause();
        document.activeElement.blur();
    };
    $scope.resumeTrack = function () {
        $player.resume();
        document.activeElement.blur();
    };
    $scope.nextTrack = function () {
        $player.next();
        document.activeElement.blur();
    };
    $scope.previousTrack = function () {
        $player.previous();
        document.activeElement.blur();
    };
    $scope.isPlaying = function () {
        return $player.isPlaying();
    };
    $scope.update = function (player) {
        $scope.$applyAsync(function () {
            $scope.player = player;
            volumeSlider.set(player.volume);
        });
    };
    $scope.updateBuffer = function (player) {
        $scope.$applyAsync(function () {
            player.trackPositionInPercentage = player.trackPosition / player.trackLength * 100;
            $scope.player = player;
        });
    };
    $scope.show = function () {
        $('.player').animate({
            height: '75px'
        }, 1000, function () {
            $scope.$applyAsync(function () {
                $(document).foundation('reflow');
            });
        });
    };
    $scope.hide = function () {
        $('.player').animate({
            height: '0px'
        }, 1000);
    };
    $scope.hide();
});