/**
 * ngStomp
 *
 * @version 0.0.1-alpha.1
 * @author Maik Hummel <yo@beevelop.com>
 * @license WTFPL
 */
angular
    .module('ngStomp', [])
    .service('$stomp', [
        '$rootScope', '$q',
        function ($rootScope, $q) {

            this.sock = null;
            this.stomp = null;
            this.debug = null;
            this.disconnected = null;

            this.setDebug = function (callback) {
                this.debug = callback;
            };

            this.setDisconnected = function(callback) {
                this.disconnected = callback;
            };

            this.connect = function (endpoint, headers) {
                headers = headers || {};

                var dfd = $q.defer();
                var disconnected = this.disconnected;

                this.sock = new SockJS(endpoint);
                this.stomp = Stomp.over(this.sock);
                this.stomp.debug = this.debug;
                this.stomp.connect(headers, function (frame) {
                    dfd.resolve(frame);
                }, function (err) {
                    dfd.reject(err);
                    if (angular.isDefined(disconnected)) {
                        disconnected.call(this);
                    }
                });

                return dfd.promise;
            };

            this.disconnect = function () {
                var dfd = $q.defer();
                this.stomp.disconnect(dfd.resolve);
                return dfd.promise;
            };

            this.subscribe = this.on = function (destination, callback, headers) {
                headers = headers || {};
                return this.stomp.subscribe(destination, function (res) {
                    var payload = null;
                    try {
                        payload = JSON.parse(res.body);
                    } finally {
                        if (callback) {
                            callback(payload, res.headers, res);
                        }
                    }
                }, headers);
            };

            this.unsubscribe = this.off = function (subscription) {
                subscription.unsubscribe();
            };

            this.send = function (destination, body, headers) {
                var dfd = $q.defer();
                try {
                    var payloadJson = JSON.stringify(body);
                    headers = headers || {};
                    this.stomp.send(destination, headers, payloadJson);
                    dfd.resolve();
                } catch (e) {
                    dfd.reject(e);
                }
                return dfd.promise;
            };
        }]
);