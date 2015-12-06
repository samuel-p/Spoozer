app.controller('PlaylistCtrl', function ($ws, $scope, $rootScope) {
    $scope.user = $rootScope.userDetails;
    console.log("ich heisse marvin");
    $ws.send('/getUserPlaylists',$rootScope.userDetails);
    $scope.addPlaylist = function() {
        $ws.send('/addPlaylist',{name: $scope.name}
        );
    };
    $ws.subscribe("/getPlaylists", function (payload, headers, res) {
        $scope.$applyAsync(function() {
            $scope.hideLoadingView();
            $scope.playlists = payload.playlists;
        });
    });

    $scope.arePlaylistsDefined = function() {
        if (!angular.isDefined($scope.playlists)) {
            return false;
        }
        return $scope.playlists.length == 0;
    };

    $ws.subscribe("/playlistAdded", function(payload, headers, res){
        $scope.$applyAsync(function() {
            $ws.send('/getUserPlaylists',$rootScope.userDetails);
        });
    });
});