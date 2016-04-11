/**
 * Created by jan-ericgaidusch on 04.04.16.
 */
app.controller('HistoryCtrl', function ($ws, $scope, $rootScope, $routeParams, $modal, $player) {
    $scope.showLoadingView();
    $ws.subscribe("/setHistory", function (payload, headers, res) {
        $scope.$applyAsync(function () {
            $scope.hideLoadingView();
            var history = [];
            for(var date in payload.history.history){
                var track = payload.history.history[date];
                history.push({date:date, track:track});
            }
            $scope.history = history;
        });
    });
    $ws.send('/getHistory');

    $scope.isHistoryEmpty = function () {
        if (!angular.isDefined($scope.history)) {
            return false;
        }
        return Object.keys($scope.history).length == 0;
    };

    $scope.clearHistory = function () {
        $ws.send('/clearHistory');
    };


});