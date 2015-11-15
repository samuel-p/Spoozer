app.controller('ProfileCtrl', function ($ws, $scope, $rootScope) {
    $scope.edit = $rootScope.userDetails;

    $scope.spotifyLogin = function () {
        $ws.send('/spotify/getLoginUrl');
    };

    $scope.spotifyLogout = function () {
        $ws.send('/spotify/logout');
    };

    $scope.areUserAccountsEmpty = function() {
        return !angular.isDefined($rootScope.userAccounts) || Object.keys($rootScope.userAccounts).length == 0;
    }

    $ws.subscribe('/spotify/setLoginUrl', function (payload, headers, res) {
        console.log(payload);
        var url = payload.url;
        var width = 450,
            height = 730,
            left = (screen.width / 2) - (width / 2),
            top = (screen.height / 2) - (height / 2);
        window.open(url, 'Spotify Anmeldung',
            'menubar=no,location=no,resizable=no,scrollbars=no,status=no, width=' + width + ', height=' + height + ', top=' + top + ', left=' + left
        );
    });
});