app.controller('PlayerCtrl', function ($player, $scope, $modal, $window, $ws) {
    var volumeSlider = new PlayerSlider($('#player-volume-slider'));
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
    $scope.show = function () {
        $('.player').trigger('beforeshow');
        $('.player').animate({
            height: '75px'
        }, 1000, function () {
            $scope.$applyAsync(function () {
                $(document).foundation('reflow');
                $('.player').trigger('aftershow');
            });
        });
    };
    $scope.hide = function () {
        $('.player').trigger('beforehide');
        $('.player').animate({
            height: '0px'
        }, 1000, function () {
            $('.player').trigger('afterhide');
        });
    };
    $scope.hide();

    $scope.shouldOpenSmallPlayer = function () {
        return Foundation.utils.is_small_only();
    };

    $scope.shouldOpenMediumPlayer = function () {
        return Foundation.utils.is_medium_only();
    };

    $scope.openPlayer = function () {
        $modal.open({
            templateUrl: 'PlayerModalContent.html',
            controller: 'PlayerModalCtrl',
            resolve: {
                player: function () {
                    return $scope.player;
                }
            }
        });
    };
    $player.bind('start', function () {
        $scope.show();

    });
    $player.bind('stop', function () {
        $scope.hide();
    });
    $player.bind('change', function (player) {
        $scope.$applyAsync(function () {
            $scope.player = player;
            volumeSlider.set(player.volume * 100);
            new AutoScrollView($('#player-track-info'), player.track.title);

        });
    });

    $player.bind('changeTrack', function (player) {
        $ws.send("/addHTrack", {id: player.track.id, service: player.track.service});
    });

    $player.bind('update', function (player) {
        $scope.$applyAsync(function () {
            player.trackPositionInPercentage = player.trackPosition / player.trackLength * 100;
            $scope.player = player;
        });
    });
});

app.controller('PlayerModalCtrl', function ($scope, $window, $player, $modalInstance, player) {
    var volumeSlider = null;
    $scope.$applyAsync(function () {
        $(document).foundation('reflow');
        setTimeout(function () {
            volumeSlider = new PlayerSlider($('#player-modal-volume-slider'));
            volumeSlider.change(function () {
                console.log(volumeSlider.get());
                $player.setVolume(volumeSlider.get() / 100);
            });
            change(player);
        }, 20);
    });
    var change = function (player) {
        $scope.$applyAsync(function () {
            $scope.player = player;
            if (volumeSlider != null) {
                volumeSlider.set(player.volume * 100);
            }
            new AutoScrollView($('#player-modal-track-info'), player.track.title);
        });
    };
    var update = function (player) {
        $scope.$applyAsync(function () {
            player.trackPositionInPercentage = player.trackPosition / player.trackLength * 100;
            $scope.player = player;
        });
    };
    $player.bind('change', change);
    $player.bind('update', update);
    $player.bind('stop', function () {
        $scope.cancel();
    });
    $scope.cancel = function () {
        $player.unbind('change', change);
        $player.unbind('update', update);
        $modalInstance.dismiss();
    };
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
    $window.addEventListener('orientationchange', function () {
        $scope.$applyAsync(function () {
            volumeSlider.set($scope.player.volume * 100);
        });
    });
});