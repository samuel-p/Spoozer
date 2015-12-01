app.controller('ProfileCtrl', function ($ws, $scope, $modal, $window, $rootScope) {
    $scope.edit = $rootScope.userDetails;

    $scope.spotifyLogout = function () {
        $ws.send('/spotify/logout');
    };

    $scope.areUserAccountsEmpty = function () {
        return !angular.isDefined($rootScope.userAccounts) || Object.keys($rootScope.userAccounts).length == 0;
    }

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
        angular.element($window).bind('resize', changeHeight);
    });

    $scope.saveUserDetails = function() {
        $ws.send('/saveUserDetails', $scope.edit);
    }

    $ws.subscribe("/getUserSave", function (payload, headers, res) {
        $rootScope.$applyAsync(function () {
            switchToOverview();
        });
    });

    var switchToOverview = function() {
        $scope.active = {};
        $scope.active['tabs.overview.active'] = true;
    }

    $scope.changePassword = function(){
        $ws.send("/changePassword", $scope.edit);
    }

    $ws.subscribe("/getPasswordChange", function (payload, headers, res) {
        $rootScope.$applyAsync(function () {
            switchToOverview();
        });
    });
});