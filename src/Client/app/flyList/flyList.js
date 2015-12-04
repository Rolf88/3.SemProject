'use strict';

angular.module('myApp.flyList', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/flyList', {
                    templateUrl: 'flyList/flyListView.html',
                    controller: 'FlightListController as ctrl'
                })
                        .when("/reservate/:flightId", {
                            templateUrl: "flyList/flyListView.html",
                            controller: "FlightListController as ctrl"
                        });
            }])
        .controller('FlightListController', ["FlightFactory", "$routeParams", function (FlightFactory, $routeParams) {
                var self = this;
                self.origin = "STN";
                self.date = "2015-01-15";
                self.numberOfPassengers = 2;
                self.flights = [];

                self.searchFlight = function () {
                    FlightFactory.searchEverywhere(self.origin, self.departureDate + "T00:00:00.235-0700", self.numberOfPassengers).then(function (response) {
                        var data = response.data;
                        self.flights = data;

                       /* for (var i = 0; i < data.length; i++) {
                            var flights = data[i].flights;

                            for (var j = 0; j < flights.length; j++) {
                                flights[j].airline = data[i].airline;
                                self.flights.push(flights[j]);
                            }
                        }*/
                    }, function (error) {
                        alert("Could not find any flights");
                    });
                };

                if (typeof ($routeParams.flightId) !== "undefined") {
                    self.selectedFlight = $routeParams.flightId;
                } else {
                    self.selectedFlight = null;
                }
            }])
        .controller("FlightReservationController", ["FlightFactory", "$location", function (FlightFactory, $location) {
                var self = this;

                self.name = "";
                self.email = "";
                self.phone = "";
                self.passengers = [];

                self.createPassengers = function (number) {
                    var passengers = [];

                    for (var i = 0; i < number; i++) {
                        passengers[i] = i;
                    }

                    return passengers;
                }

                self.reservate = function (flightId) {
                    FlightFactory.reservate(flightId, self.name, self.email, self.phone, self.passengers)
                            .then(function (response) {
                                alert("Reservated!");

                                $location.path("/flyList");
                            }, function () {
                                alert("Could not reservate");
                            });
                }
            }]);