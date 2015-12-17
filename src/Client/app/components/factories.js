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
                    reservate: function (flightId, source, passengers) {
                        return $http({
                            method: "POST",
                            url: "api/booking/reservate/" + flightId,
                            data: {
                                baseApiUrl: source,
                                passengers: passengers
                            }
                        });
                    },
                    searchAirports: function (query) {
                        return $http({
                            method: "GET",
                            url: "api/airports/search/" + query
                        });
                    }
                };
            }]).factory('AdminFactory', ["$http", function ($http) {
        return {
            getReservations: function () {
                return $http({
                    method: "GET",
                    url: "api/admin"
                });
            }
        };
    }])
        .factory("UserFactory", ["$http", function ($http) {
                return {
                    create: function (user) {
                        return $http.post("api/createuser", user);
                    }
                };
            }])
        .factory('UserReservationFactory', ["$http", function ($http) {
                return {
                    getReservations: function () {
                        return $http({
                            method: "GET",
                            url: "api/user"
                        });
                    }
                };
            }])

        .factory("HighscoreFactory", ["$http", function ($http) {
                return {
                    getDestinationHighscore: function (size) {
                        return $http({
                            method: "GET",
                            url: "api/statistics/highscores/destinations"
                        });
                    }
                }
            }]);