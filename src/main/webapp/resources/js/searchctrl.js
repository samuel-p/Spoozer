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
        $player.play({
            name: track.title,
            tracks: [track]
        });
    };
});