'use strict';

angular.module('myApp.flyList', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/flyList', {
                    templateUrl: 'flyList/flyListView.html',
                    controller: 'flyListCtrl'
                });
            }])
        .controller('flyListCtrl', function ($scope, flyListFactory) {
            $scope.searchFlight = function () {
                flyListFactory.searchflights($scope.from, $scope.date + "T00:00:00.000Z", $scope.numSeats).then(function (data) {
                    var data = data.data;
                    console.log(data);
                }, function (error) {
                    console.log(error);
                });
            };
        })
        .factory('flyListFactory', ['$http', function ($http) {
                var urlBase = 'api/internal';
                var flyListFactory = {};

                flyListFactory.searchflights = function (from, date, seats) {
                    console.log("URL: " + urlBase + '/' + from + "/" + date + "/" + seats);
                    return $http.get(urlBase + '/' + from + "/" + date + "/" + seats);
                };

                return flyListFactory;
            }]);
;