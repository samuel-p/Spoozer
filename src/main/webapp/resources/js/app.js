var app = angular.module('spoozerApp', ['ngRoute', 'ngWs']);
app.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
    $routeProvider.
        when('/dashboard', {
            templateUrl: 'views/dashboard.html',
            controller: 'DashboardCtrl',
            caseInsensitiveMatch: true
        }).
        when('/search/:text', {
            templateUrl: 'views/search.html',
            controller: 'SearchCtrl',
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

app.controller('MenuCtrl', ['$ws', '$scope', '$location', function ($ws, $scope, $location) {
    $ws.subscribe('/setUserDetails', function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.userDetails = payload.userDetails;
        });
    });

    $ws.send('/getUserDetails');
    $scope.search = function() {
        $location.path('/search/' + encodeURIComponent($scope.text));
    }
}]);

app.controller('DashboardCtrl', ['$scope', function ($scope) {

}]);

app.controller('SearchCtrl', ['$ws', '$scope', '$routeParams', function ($ws, $scope, $routeParams) {
    $ws.subscribe("/setSearchResult", function (payload, headers, res) {
        $scope.$applyAsync(function() {
            console.log(payload);
            $scope.searchResult = payload.searchResult;
        });
    });
    $ws.send('/getSearchResult', {
        search: decodeURIComponent($routeParams.text)
    });
}]);