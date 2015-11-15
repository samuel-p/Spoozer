app.controller('SearchInputCtrl', ['$ws', '$rootScope', '$scope', '$location', function ($ws, $rootScope, $scope, $location) {
    $scope.search = function() {
        $location.path('/search/' + encodeURIComponent($scope.text));
    }
}]);
app.controller('UserAccountCtrl', ['$ws', '$rootScope', '$scope', '$location', function ($ws, $rootScope, $scope, $location) {
    $scope.areUserAccountsEmpty = function() {
        return !angular.isDefined($rootScope.userAccounts) || Object.keys($rootScope.userAccounts).length == 0;
    }
}]);