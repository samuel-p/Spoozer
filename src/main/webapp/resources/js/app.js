var app = angular.module('spoozerApp', ['ngRoute', 'ngWs', 'mm.foundation', 'ngAnimate', 'ngTouch']);
app.config(function ($routeProvider) {
    $routeProvider.
        when('/dashboard', {
            templateUrl: 'views/dashboard.html',
            controller: 'DashboardCtrl',
            caseInsensitiveMatch: true
        }).
        when('/search/:text', {
            templateUrl: 'views/search.html',
            controller: 'SearchResultCtrl',
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
});
app.run(function ($rootScope, $window, $location, $ws) {
    $rootScope.$watch(function $locationWatch() {
        console
    });
    $rootScope.$on('$routeChangeSuccess', function (next, current) {
        $rootScope.$applyAsync(function () {
            $(document).foundation('reflow');
        });
    });

    $ws.setOnDisconnected(function () {
        $window.location.reload();
    });

    $ws.subscribe('/setUserDetails', function (payload, headers, res) {
        $rootScope.$applyAsync(function () {
            $rootScope.userDetails = payload.userDetails;
        });
    });
    $ws.send('/getUserDetails');
    $ws.subscribe('/setUserAccounts', function (payload, headers, res) {
        $rootScope.$applyAsync(function () {
            $rootScope.userAccounts = payload.userAccounts;
        });
    });
    $ws.send('/getUserAccounts');

    $rootScope.isActive = function (path) {
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
    $('.off-canvas-list .has-dropdown .dropdown').hide();
    $('.off-canvas-list .has-dropdown a').click(function(e) {
        e.preventDefault();
        e.stopPropagation();
        $(this).parent().find('.dropdown').slideToggle('fast');
        $(this).toggleClass('active');
    });

    $rootScope.showSmallMenu = function () {
        if (Foundation.utils.is_small_only()) {
            $('.off-canvas-wrap').foundation('offcanvas', 'show', 'move-right');
        }
    };
    $rootScope.hideSmallMenu = function () {
        if (Foundation.utils.is_small_only()) {
            $('.off-canvas-wrap').foundation('offcanvas', 'hide', 'move-right');
        }
    };
});
app.directive('preventclickpagination', function () {
    return function (scope, element) {
        element.bind('click touchend', function (e) {
            e.preventDefault();
            e.stopPropagation();
            element[0].focus();
        });
    };
});
app.directive('fullheight', function ($window) {
    return function (scope, element) {
        var changeHeight = function () {
            if (!Foundation.utils.is_small_only()) {
                $('.off-canvas-wrap').foundation('offcanvas', 'hide', 'move-right');
            }
            var height = $window.innerHeight - $('.tab-bar').outerHeight();
            element.css('height', height + 'px');
        };
        changeHeight();
        angular.element($window).bind('resize', changeHeight);
    }
});
app.directive('external', function () {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind('click', function(event) {
                event.stopPropagation();
            });
            if(true) {
                element.attr("target", "_self");
            }
        }
    };
});