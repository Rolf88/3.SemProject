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
            
            self.searchFlight = function () {
                FlightFactory.searchEverywhere(self.origin, self.departureDate + "T00:00:00.000Z", self.numberOfPassengers).then(function (data) {
                    var data = data.data;
                    self.flights = [];
                    
                    for (var i = 0; i < data.length; i++){
                        var flights = data[i].flights;
                        
                        for (var j = 0; j < flights.length; j++) {
                            flights[j].airline = data[i].airline;
                            self.flights.push(flights[j]);
                        }
                    }
                    console.log(self.flights);
                }, function (error) {
                    console.log(error);
                });
            };
        }]);