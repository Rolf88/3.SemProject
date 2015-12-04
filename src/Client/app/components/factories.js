'use strict';

/* Place your global Factory-service in this file */

angular.module('myApp.factories', []).
        factory('FlightFactory', ["$http", function ($http) {
                return {
                    searchEverywhere: function (origin, departureDate, numberOfPassengers) {
                        return $http({
                            method: "GET",
                            url: "api/flightinfo/" + origin + "/" + departureDate + "/" + numberOfPassengers
                        });
                    },
                    search: function (origin, destination, departureDate, numberOfPassengers) {
                        return $http({
                            method: "GET",
                            url: "api/flightinfo/" + origin + "/" + destination + "/" + departureDate + "/" + numberOfPassengers
                        });
                    }
                };
            }]);