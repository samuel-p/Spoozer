var app = angular.module('spoozerApp', ['ngRoute', 'ngWs', 'mm.foundation', 'ngAnimate']);
app.config(function ($routeProvider, $locationProvider) {
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
app.run(function ($rootScope, $location, $ws) {
    $rootScope.$on('$routeChangeSuccess', function (next, current) {
        $rootScope.$applyAsync(function () {
            $(document).foundation('reflow');
        });
    });

    $ws.subscribe('/setUserDetails', function (payload, headers, res) {
        $rootScope.$applyAsync(function () {
            $rootScope.userDetails = payload.userDetails;
        });
    });
    $ws.send('/getUserDetails');

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

    (function ($) {
        $.each(['show', 'hide'], function (i, ev) {
            var el = $.fn[ev];
            $.fn[ev] = function () {
                this.trigger(ev);
                el.apply(this, arguments);
                return el;
            };
        });
    })(jQuery);
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
            if ($('.top-bar').is(':visible')) {
                $('.off-canvas-wrap').foundation('offcanvas', 'hide', 'move-right');
            }
            var height = $window.innerHeight - $('.tab-bar').outerHeight();
            element.css('height', height + 'px');
        };
        changeHeight();
        angular.element($window).bind('resize', changeHeight);
    }
});