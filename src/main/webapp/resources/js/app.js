var app = angular.module('spoozerApp', ['ngRoute', 'ngWs', 'mm.foundation', 'ngAnimate', 'ngTouch', 'ngPlayer']);
app.config(function ($routeProvider) {
    $routeProvider.when('/dashboard', {
        templateUrl: 'views/dashboard.html',
        controller: 'DashboardCtrl',
        caseInsensitiveMatch: true
    }).when('/search/:text', {
        templateUrl: 'views/search.html',
        controller: 'SearchResultCtrl',
        caseInsensitiveMatch: true
    }).when('/profile', {
        templateUrl: 'views/profile.html',
        controller: 'ProfileCtrl',
        caseInsensitiveMatch: true
    }).when('/profile/:tab', {
        templateUrl: 'views/profile.html',
        controller: 'ProfileCtrl',
        caseInsensitiveMatch: true
    }).when('/playlists', {
        templateUrl: 'views/playlists.html',
        controller: 'PlaylistCtrl',
        caseInsensitiveMatch: true
    }).when('/playlists/:playlist', {
        templateUrl: 'views/playlists.html',
        controller: 'PlaylistCtrl',
        caseInsensitiveMatch: true
    }).when('/history', {
        templateUrl: 'views/history.html',
        controller: 'HistoryCtrl',
        caseInsensitiveMatch: true
    }).otherwise({
        redirectTo: '/dashboard'
    });
});
app.run(function ($rootScope, $window, $location, $ws, $route, $modal, $player) {
    $rootScope.$on('$routeChangeSuccess', function (next, current) {
        $rootScope.hideLoadingView();
        $rootScope.$applyAsync(function () {
            $(document).foundation('reflow');
        });
    });
    $rootScope.$on('$routeChangeStart', function (next, current) {
        $rootScope.showLoadingView();
    });

    $ws.setOnDisconnected(function () {
        $window.location.reload();
    });

    $ws.subscribe('/setUserDetails', function (payload, headers, res) {
        $rootScope.$applyAsync(function () {
            console.log(payload.userDetails);
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
            back_text: '<span class="fa-chevron-left"/> Zurück'
        },
        equalizer: {
            equalize_on_stack: true
        }
    });
    $('.off-canvas-list .has-dropdown .dropdown').hide();
    $('.off-canvas-list .has-dropdown a').click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        $(this).parent().find('.dropdown').slideToggle('fast');
        $(this).toggleClass('active');
    });

    $rootScope.showLoadingView = function () {
        $('.loading-view').show();
    };
    $rootScope.hideLoadingView = function () {
        $('.loading-view').hide();
    };

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
    $rootScope.hideLoadingView();
    function checkOrientaion() {
        var orientaion = 'landscape';
        if ($window.innerHeight > $window.innerWidth) {
            orientaion = 'portrait';
        }
        if ($window.orientation != orientaion) {
            $window.orientation = orientaion;
            $rootScope.orientation = orientaion;
            $window.dispatchEvent(new Event('orientationchange'));
        }
    }

    angular.element($window).bind('resize', checkOrientaion);
    checkOrientaion();

    var originalPath = $location.path;
    $location.path = function (path, reload) {
        if (reload === false) {
            var lastRoute = $route.current;
            var un = $rootScope.$on('$locationChangeSuccess', function () {
                $rootScope.hideLoadingView();
                $route.current = lastRoute;
                un();
            });
        }
        return originalPath.apply($location, [path]);
    };
    $ws.subscribe('/setSoundcloudClientId', function (clientId) {
        SC.initialize({
            client_id: clientId.data
        });
    });
    $ws.send('/getSoundcloudClientId');
    $rootScope.playTrack = function (track) {
        $player.play({
            name: track.title,
            tracks: [track]
        });
    };
    $rootScope.playTracks = function (title, tracks, index) {
        $player.play({
            name: title,
            tracks: tracks
        }, index);
    };
    $rootScope.playList = function (playlist, index) {
        $player.play(playlist, index);
    };
    $rootScope.addToPlaylist = function (track) {
        var modalInstance = $modal.open({
            templateUrl: 'AddTrackToPlaylistContent.html',
            controller: 'AddTrackToPlaylistCtrl',
        });

        modalInstance.result.then(function (playlist) {
            if (angular.isDefined(playlist)) {
                $ws.send('/addSongToPlaylist', {
                    playlistId: playlist.id,
                    trackId: track.id,
                    service: track.service
                });
            }
        });
    };
});
app.controller('AddTrackToPlaylistCtrl', function ($scope, $modalInstance, $ws) {
    $scope.selected = {
        playlist: null
    };
    var updatePlaylists = function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.playlists = payload.playlists;
            $modalInstance.reposition();
        });
    };
    $ws.subscribe('/setPlaylists', updatePlaylists);
    $ws.send('/getPlaylists');

    $scope.ok = function () {
        $modalInstance.close($scope.selected.playlist);
        $ws.unsubscribe('/setPlaylists', updatePlaylists);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
        $ws.unsubscribe('/setPlaylists', updatePlaylists);
    };
});
app.filter('timeFromMillisFilter', function () {
    return function (value) {
        var seconds = Math.floor(value / 1000) % 60;
        var minutes = Math.floor(value / 1000 / 60);
        return minutes + ':' + pad(seconds);
    };
});
app.filter('timeFromSecondsFilter', function () {
    return function (value) {
        var seconds = Math.floor(value) % 60;
        var minutes = Math.floor(value / 60);
        return minutes + ':' + pad(seconds);
    };
});

function pad(num) {
    if (num < 10) {
        return '0' + num;
    }
    return num;
}