app.controller('SearchResultCtrl', function ($ws, $scope, $rootScope, $routeParams, $modal, $player) {
    $rootScope.text = decodeURIComponent($routeParams.text);
    $scope.showLoadingView();
    // hide keyboard on mobile
    document.activeElement.blur();
    $ws.subscribe("/setSearchResult", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.hideLoadingView();
            $scope.searchResult = payload.searchResult;
        });
    });
    $ws.send('/getSearchResult', {
        search: decodeURIComponent($routeParams.text)
    });

    $scope.isSearchResultEmpty = function () {
        if (!angular.isDefined($scope.searchResult)) {
            return false;
        }
        return Object.keys($scope.searchResult).length == 0;
    };

    $scope.playTrack = function (track) {
        $player.play({
            name: track.title,
            tracks: [track]
        });
    };
    $scope.addToPlaylist = function (track) {
        var modalInstance = $modal.open({
            templateUrl: 'AddTrackToPlaylistContent.html',
            controller: 'AddTrackToPlaylistCtrl',
        });

        modalInstance.result.then(function (playlist) {
            $ws.send('/addSongToPlaylist', {
                playListID: playlist.id,
                trackID: track.id,
                streamingService: track.service
            });
        });
    };

    $ws.subscribe("/setPlaylists", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.playlists = payload.playlists;
        });
    });
})
;
app.controller('AddTrackToPlaylistCtrl', function ($scope, $modalInstance, $ws) {
    $scope.selected = {
        playlist: null
    };
    var updatePlaylists = function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.playlists = payload.playlists;
            $modalInstance.reposition();
        });
    };
    $ws.subscribe('/setPlaylists', updatePlaylists);
    $ws.send('/getPlaylists');

    $scope.ok = function () {
        $modalInstance.close($scope.selected.playlist);
        $ws.unsubscribe('/setPlaylists', updatePlaylists);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
        $ws.unsubscribe('/setPlaylists', updatePlaylists);
    };
});