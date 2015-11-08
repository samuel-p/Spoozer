app.controller('SearchCtrl', ['$ws', '$scope', '$routeParams', function ($ws, $scope, $routeParams) {
    $ws.subscribe("/setSearchResult", function (payload, headers, res) {
        $scope.$applyAsync(function() {
            console.log(payload);
            $scope.searchResult = payload.searchResult;
        });
    });
    $ws.send('/getSearchResult', {
        search: decodeURIComponent($routeParams.text)
    });
}]);