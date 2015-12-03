app.controller('SearchInputCtrl', function ($ws, $rootScope, $scope, $location) {
    $scope.search = function() {
        $location.path('/search/' + encodeURIComponent($scope.text));
    }
});
app.controller('UserAccountCtrl', function ($ws, $rootScope, $scope, $location) {
    $scope.areUserAccountsEmpty = function() {
        return !angular.isDefined($rootScope.userAccounts) || Object.keys($rootScope.userAccounts).length == 0;
    }
});