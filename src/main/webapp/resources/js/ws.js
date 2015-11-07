angular.module('ngWs', ['ngStomp']).service('$ws', ['$stomp', function ($stomp) {
    this.connected = false;
    this.subscriptions = {};
    this.initSubscriptions = [];
    this.initSends = [];

    this.init = function() {
        var self = this;
        $stomp.connect('/connect').then(function (frame) {
            self.connected = true;
            self.initSubscriptions.forEach(function (s) {
                self.subscribe(s.destination, s.callback, s.headers);
            });
            self.initSends.forEach(function (s) {
                self.send(s.destination, s.body, s.headers);
            })
        });
    };

    this.subscribe = function (destination, callback, headers) {
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

    this.unsubscribe = this.off = function (destination) {
        this.subscriptions[destination].forEach(function (subscription) {
            $stomp.unsubscribe(subscription);
        });
        this.subscriptions[destination] = undefined;
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

    this.init();
}]);