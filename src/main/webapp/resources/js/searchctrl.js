app.controller('SearchResultCtrl', function ($ws, $scope, $rootScope, $routeParams, $player) {
    $rootScope.text = decodeURIComponent($routeParams.text);
    $scope.showLoadingView();
    // hide keyboard on mobile
    document.activeElement.blur();
    $ws.subscribe("/setSearchResult", function (payload, headers, res) {
        $scope.$applyAsync(function() {
            $scope.hideLoadingView();
            $scope.searchResult = payload.searchResult;
        });
    });
    $ws.send('/getSearchResult', {
        search: decodeURIComponent($routeParams.text)
    });

    $scope.isSearchResultEmpty = function() {
        if (!angular.isDefined($scope.searchResult)) {
            return false;
        }
        return Object.keys($scope.searchResult).length == 0;
    };

    $scope.playTrack = function(track) {
        $player.play({
            name: track.title,
            tracks: [track]
        });
    };
    $scope.addToPlaylist = function(track) {
        console.log(track.url);
        var e = document.getElementById("test");
        var listid = e.options[e.selectedIndex].text;
        $ws.send('/addSongToPlaylist',{
            playListID: listid,
            trackID: track.id,
            streamingService: track.service
        });
        $scope.showAddToPlaylistInput = false;
    };

    $scope.showAddToPlaylist = function (id) {
        $scope.showAddToPlaylistInput = true;
        setTimeout(function () {
            $('#add-to-playlist-input').focus();
        }, 20);
        $ws.send('/getPlaylists');
    };

    $ws.subscribe("/setPlaylists", function (payload, headers, res) {
        $scope.$applyAsync(function() {
            $scope.playlists = payload.playlists;
        });
    });
});