app.controller('ProfileCtrl', function ($ws, $scope, $modal, $window, $rootScope) {
    $scope.tabs = {
        overview: {
            active: true
        },
        editProfile: {
            active: false
        },
        changePassword: {
            active: false
        },
        accounts: {
            active: false
        },
        settings: {
            active: false
        }
    };
    $scope.edit = $rootScope.userDetails;

    $scope.spotifyLogout = function () {
        $ws.send('/spotify/logout');
    };

    $scope.areUserAccountsEmpty = function () {
        return !angular.isDefined($rootScope.userAccounts) || Object.keys($rootScope.userAccounts).length == 0;
    };

    var changeHeight = function () {
        var element = $('.tabs-content');
        var viewHeight = $('.view').innerHeight();
        var headHeight = $('.tabs').innerHeight();
        var margT = element.outerHeight(true) - element.outerHeight();
        var paddingHeight = margT * 3 / 2;
        var height = viewHeight - headHeight - paddingHeight;
        element.css('height', height + 'px');
    };
    $scope.$applyAsync(function () {
        changeHeight();
        $('.view').bind('DOMSubtreeModified', changeHeight);
    });
});