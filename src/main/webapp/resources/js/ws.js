angular.module('ngWs', ['ngStomp']).service('$ws', ['$stomp', '$http', function ($stomp, $http) {
    this.connected = false;
    this.subscriptions = {};
    this.initSubscriptions = [];
    this.initSends = [];

    $stomp.setDebug(function (args) {
        console.log(args);
    });

    this.setOnDisconnected = function(callback) {
        $stomp.setDisconnected(callback);

    };

    this.init = function(csrf) {
        var self = this;
        var header = {};
        header[csrf.headerName] = csrf.token;
        $stomp.connect('/connect', header).then(function (frame) {
            self.connected = true;
            self.initSubscriptions.forEach(function (s) {
                self.subscribeBroadcast(s.destination, s.callback, s.headers);
            });
            self.initSends.forEach(function (s) {
                self.send(s.destination, s.body, s.headers);
            });
        });
    };
    var self = this;
    $http.get('/csrf').then(function(csrf) {
        self.init(csrf.data);
    });

    this.subscribe = function (destination, callback, headers) {
        this.subscribeBroadcast('/user' + destination, callback, headers);
    };

    this.subscribeBroadcast = function (destination, callback, headers) {
        if (this.connected) {
            if (this.subscriptions[destination] === undefined) {
                this.subscriptions[destination] = [];
            }
            this.subscriptions[destination].push($stomp.subscribe(destination, callback, headers));
        } else {
            this.initSubscriptions.push({
                destination: destination,
                callback: callback,
                headers: headers
            });
        }
    };

    this.unsubscribe = this.off = function (destination, callback) {
        var subscription = this.subscriptions[destination];
        if (angular.isDefined(subscription)) {
            subscription.forEach(function (subscription) {
                if (!angular.isDefined(callback) || subscription == callback) {
                    $stomp.unsubscribe(subscription);
                }
            });
            this.subscriptions[destination] = undefined;
        }
    };

    this.send = function (destination, body, headers) {
        if (this.connected) {
            $stomp.send(destination, body, headers);
        } else {
            this.initSends.push({
                destination: destination,
                body: body,
                headers: headers
            });
        }
    };
}]);