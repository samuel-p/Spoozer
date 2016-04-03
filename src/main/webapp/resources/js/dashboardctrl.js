app.controller('DashboardCtrl', function ($scope, $ws) {
    $ws.subscribe("/setChartTracks", function (payload, headers, res) {
        console.log(payload);
    });
    $ws.send('/getChartTracks');
    $ws.subscribe("/setNewReleasedTracks", function (payload, headers, res) {
        console.log(payload);
    });
    $ws.send('/getNewReleasedTracks');
    $ws.subscribe("/setTrendingTracks", function (payload, headers, res) {
        console.log(payload);
    });
    $ws.send('/getTrendingTracks');
    $ws.subscribe("/setFeaturedTracks", function (payload, headers, res) {
        console.log(payload);
    });
    $ws.send('/getFeaturedTracks');
});