'use strict';

angular.module('myApp.flyList', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/flyList', {
                    templateUrl: 'flyList/flyListView.html',
                    controller: 'FlightListController as ctrl'
                });
            }])
        .controller('FlightListController', ["FlightFactory", function (FlightFactory) {
                var self = this;

                self.flights = [];

                self.origin = "";
                self.destination = "";
                self.date = "";
                self.numberOfPassengers = 2;

                self.searchFlight = function () {
                    FlightFactory.search(self.origin, self.destination, self.date + "T00:00:00.000Z", self.numberOfPassengers)
                            .then(function (response) {
                                self.flights = response.data;
                            });
                };
            }]);