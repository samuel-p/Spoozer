app.controller('ProfileCtrl', function ($ws, $scope, $modal, $rootScope) {
    $scope.edit = $rootScope.userDetails;

    $scope.spotifyLogout = function () {
        $ws.send('/spotify/logout');
    };

    $scope.areUserAccountsEmpty = function() {
        return !angular.isDefined($rootScope.userAccounts) || Object.keys($rootScope.userAccounts).length == 0;
    }
});