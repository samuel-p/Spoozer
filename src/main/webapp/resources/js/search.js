app.controller('SearchResultCtrl', function ($ws, $scope, $routeParams) {
    // hide keyboard on mobile
    document.activeElement.blur();
    $ws.subscribe("/setSearchResult", function (payload, headers, res) {
        $scope.$applyAsync(function() {
            $scope.searchResult = payload.searchResult;
        });
    });
    $ws.send('/getSearchResult', {
        search: decodeURIComponent($routeParams.text)
    });
});