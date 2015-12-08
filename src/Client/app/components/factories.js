'use strict';

/* Place your global Factory-service in this file */

angular.module('myApp.factories', []).
        factory('FlightFactory', ["$http", function ($http) {
                return {
                    searchEverywhere: function (origin, departureDate, numberOfPassengers) {
                        return $http({
                            method: "GET",
                            url: "api/internal/" + origin + "/" + departureDate + "/" + numberOfPassengers
                        });
                    },
                    search: function (origin, destination, departureDate, numberOfPassengers) {
                        return $http({
                            method: "GET",
                            url: "api/internal/" + origin + "/" + destination + "/" + departureDate + "/" + numberOfPassengers
                        });
                    },
                    reservate: function (flightId, passengers) {
                        return $http({
                            method: "POST",
                            url: "api/booking/reservate/" + flightId,
                            data: {
                                passengers: passengers
                            }
                        })
                    }
                };
            }]);