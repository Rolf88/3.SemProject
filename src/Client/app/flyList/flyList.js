'use strict';

angular.module('myApp.flyList', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/flyList', {
                    templateUrl: 'flyList/flyListView.html',
                    controller: 'flyListCtrl'
                });
            }])
        .controller('flyListCtrl', function ($scope) {
            $scope.searchFlight = function () {
                console.log("From " + $scope.from + " to " + $scope.to + " at d. " + $scope.date + " with " + $scope.numSeats);
            };
        });