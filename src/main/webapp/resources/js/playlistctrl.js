app.controller('PlaylistCtrl', function ($ws, $scope, $rootScope, $player) {
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
        $ws.send('/getPlaylist', {
            id: list.id
        });
    };

    $ws.subscribe("/setPlaylist", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.playlist = payload.playlist;
        });
    });

    $scope.arePlaylistsEmpty = function () {
        return !angular.isDefined($scope.playlists) || $scope.playlists.length == 0;
    };

    $scope.playPlaylist = function (event, list) {
        event.stopPropagation();
        $ws.send('/getPlaylist', list);
    };

    $ws.subscribe("/playPlaylist", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            console.log(payload);
            $player.play(payload.tracks);
        });
    });
});