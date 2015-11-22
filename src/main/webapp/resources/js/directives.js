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
        player.bind('DOMSubtreeModified', changeHeight);
    }
});