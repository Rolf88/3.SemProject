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
        .controller('FlightListController', ["FlightFactory", "$routeParams", "$location", function (FlightFactory, $routeParams, $location) {
                var self = this,
                        airports = {};
                self.origin = "";
                self.destination = "";
                self.date = "";
                self.numberOfPassengers = null;
                self.flights = [];

                self.originAirports = [];
                self.updateOriginAirports = function (typed) {
                    if (typed === "") {
                        self.originAirports = [];
                        return;
                    }

                    FlightFactory.searchAirports(typed).then(function (response) {
                        self.originAirports = response.data.map(function (airport) {
                            var name = airport.city + " (" + airport.code + ")";
                            airports[name] = airport.code;

                            return name;
                        });
                    });
                };

                self.destinationAirports = [];
                self.updateDestinationAirports = function (typed) {
                    if (typed === "") {
                        self.destinationAirports = [];
                        return;
                    }

                    FlightFactory.searchAirports(typed).then(function (response) {
                        self.destinationAirports = response.data.map(function (airport) {
                            var name = airport.city + " (" + airport.code + ")";
                            airports[name] = airport.code;

                            return name;
                        });
                    });
                };

                self.searchFlight = function () {
                    if (self.origin.length == 0) {
                        alert("Enter an origin");
                        return;
                    }


                    if (self.destination.length != 0) {
                        FlightFactory.search(airports[self.origin], airports[self.destination], self.departureDate + "T00:00:00.235Z", self.numberOfPassengers).then(function (response) {
                            var data = response.data;
                            self.flights = [];

                            for (var i = 0; i < data.length; i++) {
                                var flights = data[i].flights;

                                for (var j = 0; j < flights.length; j++) {
                                    flights[j].airline = data[i].airline;
                                    flights[j].baseApiUrl = data[i].url;
                                    self.flights.push(flights[j]);
                                }
                            }
                        }, function (error) {
                            self.flights = [];

                            alert("Could not find any flights");
                        });
                    } else {
                        console.log(airports, self.origin, airports[self.origin]);

                        FlightFactory.searchEverywhere(airports[self.origin], self.departureDate + "T00:00:00.235Z", self.numberOfPassengers).then(function (response) {
                            var data = response.data;
                            self.flights = [];
                            console.log("WYW");
                            for (var i = 0; i < data.length; i++) {
                                var flights = data[i].flights;

                                for (var j = 0; j < flights.length; j++) {
                                    flights[j].airline = data[i].airline;
                                    flights[j].baseApiUrl = data[i].url;
                                    self.flights.push(flights[j]);
                                }
                            }
                        }, function (error) {
                            alert("Could not find any flights");
                        });
                    }
                };

                if (typeof ($routeParams.flightId) !== "undefined") {
                    self.selectedFlight = $routeParams.flightId
                } else {
                    self.selectedFlight = null;
                }
            }])
        .controller("FlightReservationController", ["FlightFactory", "$location", function (FlightFactory, $location) {
                var self = this;

                self.numberOfPassengers = $location.search().passengers;
                self.passengers = [];

                self.createPassengers = function (number) {
                    var passengers = [];

                    for (var i = 0; i < number; i++) {
                        passengers[i] = i;
                    }

                    return passengers;
                }

                self.reservate = function (flightId) {
                    FlightFactory.reservate(flightId, $location.search().source, self.passengers)
                            .then(function (response) {
                                alert("Reservated!");

                                $location.path("/flyList");
                            }, function () {
                                alert("Could not reservate");
                            });
                }
            }]);