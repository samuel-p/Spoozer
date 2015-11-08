app.controller('MenuCtrl', ['$ws', '$rootScope', '$scope', '$location', function ($ws, $rootScope, $scope, $location) {
    $ws.subscribe('/setUserDetails', function (payload, headers, res) {
        $rootScope.$applyAsync(function () {
            $rootScope.userDetails = payload.userDetails;
        });
    });

    $ws.send('/getUserDetails');
    $scope.search = function() {
        $location.path('/search/' + encodeURIComponent($scope.text));
    }
}]);