'use strict';

angular.module('myApp.reservation', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/reservation', {
                    templateUrl: 'reservation/reservationView.html',
                    controller: 'reservationCtrl'
                });
            }])
        .controller('reservationCtrl', ['UserReservationFactory', function (UserReservationFactory) {
                var self = this;

                UserReservationFactory.getReservations().then(function (response) {
                    var data = response.data;
                    self.reservations = data;
                    console.log(data);
                }, function (error) {
                    alert("Could not anything: " + error);
                });
            }]);