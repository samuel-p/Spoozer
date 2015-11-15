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

    function pad(num) {
        if (num < 10) {
            return '0' + num;
        }
        return num;
    }

    $scope.parseTime = function(timeInMillis) {
        var seconds = Math.floor(timeInMillis / 1000) % 60;
        var minutes = Math.floor(Math.floor(timeInMillis / 1000) / 60);
        return minutes + ':' + pad(seconds);
    }
});