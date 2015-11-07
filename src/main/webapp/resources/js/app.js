var app = angular.module('spoozerApp', ['ngRoute', 'ngWs']);
app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/dashboard', {
            templateUrl: 'views/dashboard.html',
            controller: 'DashboardCtrl',
            caseInsensitiveMatch: true
        }).
        otherwise({
            redirectTo: '/dashboard'
        });
}]);
app.run(['$rootScope', function ($rootScope) {
    $rootScope.$on('$routeChangeSuccess', function (next, current) {
        $rootScope.$applyAsync(function() {
            $(document).foundation('reflow');
        });
    });

    $(document).foundation({
        topbar: {
            custom_back_text: true,
            back_text: '<span class="fi-arrow-left"/> Zurück'
        },
        equalizer: {
            equalize_on_stack: true
        }
    });
}]);

app.controller('MenuCtrl', ['$ws', '$scope', function ($ws, $scope) {
    $ws.subscribe('/setUserDetails', function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.userDetails = payload.userDetails;
        });
    });

    $ws.send('/getUserDetails');
}]);

app.controller('DashboardCtrl', ['$scope', function ($scope) {

}]);