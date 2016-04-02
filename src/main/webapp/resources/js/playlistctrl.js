app.controller('PlaylistCtrl', function ($ws, $scope, $rootScope, $player, $routeParams, $location) {
    var playAfterSet = false;
    $scope.showLoadingView();
    $ws.subscribe("/setPlaylists", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.hideLoadingView();
            $scope.playlists = payload.playlists;
        });
    });
    $ws.send('/getPlaylists');

    $scope.showAddPlaylist = function () {
        $scope.showAddPlaylistInput = true;
        setTimeout(function () {
            $('#add-playlist-input').focus();
        }, 20);
    };

    $scope.addPlaylist = function () {
        if (angular.isDefined($scope.name)) {
            $ws.send('/addPlaylist', {
                name: $scope.name
            });
        }
        $scope.name = '';
        $scope.showAddPlaylistInput = false;
    };
    $scope.deletePlaylist = function (event, list) {
        event.stopPropagation();
        $ws.send('/deletePlaylist', list);
    };
    $scope.showPlaylist = function (list) {
        $location.path('/playlists/' + list.id, false);
        $routeParams.playlist = list.id;
        $ws.send('/getPlaylist', {
            id: list.id
        });
    };
    if ($routeParams.playlist) {
        $ws.send('/getPlaylist', {
            id: $routeParams.playlist
        });
    }

    $ws.subscribe("/setPlaylist", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            if (playAfterSet) {
                playAfterSet = false;
                $player.play(payload.playlist);
            } else {
                $scope.playlist = payload.playlist;
                if (Foundation.utils.is_small_only()) {
                    $('.tracklist-container').addClass('small-12');
                    $('.tracklist-container').removeClass('hide-for-small-only');
                    $('.playlist-container').addClass('hide-for-small-only');
                    $('.playlist-container').removeClass('small-12');
                }
            }
        });
    });

    $scope.back = function () {
        $location.path('/playlists', false);
        $('.tracklist-container').removeClass('small-12');
        $('.tracklist-container').addClass('hide-for-small-only');
        $('.playlist-container').removeClass('hide-for-small-only');
        $('.playlist-container').addClass('small-12');
    };

    $scope.arePlaylistsEmpty = function () {
        return !angular.isDefined($scope.playlists) || $scope.playlists.length == 0;
    };

    $scope.playPlaylist = function (event, list) {
        event.stopPropagation();
        playAfterSet = true;
        $ws.send('/getPlaylist', {
            id: list.id
        });
    };

    $scope.deleteTrackFromPlaylist = function (event, track) {
        event.stopPropagation();
        $ws.send('/deleteSongFromPlaylist', {
            playlistId: $scope.playlist.id,
            trackId: track.id,
            service: track.service
        });
    }
});