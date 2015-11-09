app.controller('SearchInputCtrl', ['$ws', '$rootScope', '$scope', '$location', function ($ws, $rootScope, $scope, $location) {
    $scope.search = function() {
        $location.path('/search/' + encodeURIComponent($scope.text));
    }
}]);