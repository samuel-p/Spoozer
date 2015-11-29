app.controller('PlayerCtrl', function ($player, $scope, $modal, $window) {
    var playerModal = null;
    var volumeSlider = new PlayerSlider($('.volume-slider'));
    volumeSlider.change(function () {
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
            console.log(player);
            $scope.player = player;
            volumeSlider.set(player.volume * 100);
            new AutoScrollView($('.track-info'), player.track.title);
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
        if (playerModal != null) {
            playerModal.dismiss();
        }
    };
    $scope.hide();

    $scope.shouldOpenSmallPlayer = function () {
        return Foundation.utils.is_small_only();
    };

    $scope.shouldOpenMediumPlayer = function () {
        return Foundation.utils.is_medium_only();
    };

    $scope.openPlayer = function () {
        playerModal = $modal.open({
            templateUrl: 'PlayerModalContent.html',
            controller: 'PlayerModalCtrl'
        });
    };
    $window.addEventListener("orientationchange", function () {
        if (playerModal != null) {
            playerModal.reposition();
        }
    });
});

app.controller('PlayerModalCtrl', function ($scope, $modalInstance) {
    $scope.cancel = function () {
        $modalInstance.dismiss();
    };
});