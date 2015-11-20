app.controller('SearchResultCtrl', function ($ws, $scope, $rootScope, $routeParams) {
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

    $scope.isSearchResultEmpty = function() {
        if (!angular.isDefined($scope.searchResult)) {
            return false;
        }
        return Object.keys($scope.searchResult).length == 0;
    };
});