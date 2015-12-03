'use strict';

angular.module('myApp.reservation', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/reservation', {
                    templateUrl: 'reservation/reservationView.html',
                    controller: 'reservationCtrl'
                });
            }])
        .controller('reservationCtrl', function () {

        });