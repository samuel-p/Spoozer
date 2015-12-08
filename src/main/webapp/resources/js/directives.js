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
app.directive('fitplayerheight', function ($window) {
    return function (scope, element) {
        var player = $('.player');
        var changeHeight = function () {
            element.css('bottom', player.height() + 'px');
        };
        player.bind('aftershow afterhide', changeHeight);
        var animateHeightIn = function () {
            element.animate({
                bottom: '75px'
            }, 1000);
        };
        player.bind('beforeshow', animateHeightIn);
        var animateHeightOut = function () {
            element.animate({
                bottom: '0'
            }, 1000);
        };
        player.bind('beforehide', animateHeightOut);
    }
});