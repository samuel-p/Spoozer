app.controller('ProfileCtrl', function ($ws, $scope, $modal, $window, $rootScope) {
    $scope.tabs = {
        overview: {
            active: true
        },
        profile: {
            active: false
        },
        password: {
            active: false
        },
        accounts: {
            active: false
        },
        settings: {
            active: false
        }
    };
    $scope.editProfile = angular.copy($rootScope.userDetails);

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

    $scope.saveUserDetails = function() {
        $ws.send('/saveUserDetails', $scope.editProfile);
    };

    $ws.subscribe("/savedUserDetails", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $rootScope.userDetails = payload.userDetails;
            $scope.tabs.overview.active = true;
        });
    });

    $ws.subscribe("/errorSaveUserDetails", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            console.log("Errors found");
            console.log($rootScope.result.get(0));
            console.log(res.get(0));
        });
    });

    $scope.changePassword = function(){
        $ws.send("/savePassword", $scope.editPassword);
    };

    $ws.subscribe("/savedPassword", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.tabs.overview.active = true;
        });
    });
});