'use strict';

angular.module('myApp.admin', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider
                        .when('/admin', {
                            templateUrl: 'admin/adminView.html',
                            controller: 'adminCtrl as ctrl'
                        })
                        .when("/admin/highscores/destination", {
                            templateUrl: 'admin/destinationHighscoreView.html',
                            controller: "HighscoreDestinationCtrl as ctrl"
                        });
            }])
        .controller('adminCtrl', ['AdminFactory', function (AdminFactory) {
                var self = this;

                AdminFactory.getReservations().then(function (response) {
                    var data = response.data;
                    self.reservations = data;
                    console.log(data);
                }, function (error) {
                    alert("Could not anything: " + error);
                });
            }])
        .controller("HighscoreDestinationCtrl", ["HighscoreFactory", function (HighscoreFactory) {
                var self = this;
                self.isLoading = true;
                self.highscores = [];

                HighscoreFactory.getDestinationHighscore(10).then(function (response) {
                    self.highscores = response.data;
                    self.isLoading = false;
                });
            }]);