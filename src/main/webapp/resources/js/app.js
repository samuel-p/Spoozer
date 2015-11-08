var app = angular.module('spoozerApp', ['ngRoute', 'ngWs', 'mm.foundation']);
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
        when('/profile', {
            templateUrl: 'views/profile.html',
            controller: 'ProfileCtrl',
            caseInsensitiveMatch: true
        }).
        otherwise({
            redirectTo: '/dashboard'
        });
}]);
app.run(['$rootScope', '$location', function ($rootScope, $location) {
    $rootScope.$on('$routeChangeSuccess', function (next, current) {
        $rootScope.$applyAsync(function() {
            $(document).foundation('reflow');
        });
    });

    $rootScope.isActive = function(path) {
        if (path == '/') {
            path = '/dashboard';
        }
        return $location.path() == path;
    };

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