app.controller('ProfileCtrl', ['$ws', '$scope', '$rootScope', function ($ws, $scope, $rootScope) {
    $scope.edit = $rootScope.userDetails;
}]);