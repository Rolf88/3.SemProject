'use strict';

angular.module('myApp.admin', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/admin', {
                    templateUrl: 'admin/adminView.html',
                    controller: 'adminCtrl as ctrl'
                });
            }])
        .controller('adminCtrl', ['AdminFactory', function (AdminFactory) {
                var self = this;

                AdminFactory.getResevasions().then(function (response) {
                    var data = response.data;
                    console.log(data);
                }, function (error) {
                    alert("Could not anything: " + error);
                });
            }]);