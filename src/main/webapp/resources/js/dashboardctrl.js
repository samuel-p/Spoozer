app.controller('DashboardCtrl', function ($scope, $ws) {
    $scope.showLoadingView();
    $ws.subscribe("/setDashboardProperties", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.properties = payload.properties;
            setTimeout(function () {
                // shuffle panels on dashboard
                var parent = $("#dashboard-list");
                var divs = parent.children();
                while (divs.length > 8) {
                    divs.splice(divs.length - 1, 1)[0].remove();
                }
                while (divs.length) {
                    parent.append(divs.splice(Math.floor(Math.random() * divs.length), 1)[0]);
                }
                $(document).foundation('reflow');
                $scope.hideLoadingView();
            }, 0);
        });
    });
    $ws.send('/getDashboardProperties');
    $ws.subscribe("/setChartTracks", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.chartTracks = payload.tracks;
        });
    });
    $ws.send('/getChartTracks');
    $ws.subscribe("/setNewReleasedTracks", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.newReleasedTracks = payload.tracks;
        });
    });
    $ws.send('/getNewReleasedTracks');
    $ws.subscribe("/setTrendingTracks", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.trendingTracks = payload.tracks;
        });
    });
    $ws.send('/getTrendingTracks');
    $ws.subscribe("/setFeaturedTracks", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.featuredTracks = payload.tracks;
        });
    });
    $ws.send('/getFeaturedTracks');
});