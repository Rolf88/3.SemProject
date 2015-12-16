'use strict';

angular.module('myApp.createUser', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/createUser', {
                    templateUrl: 'createUser/createUserView.html',
                    controller: 'createUserCtrl'
                });
            }])
        .controller('createUserCtrl', ["$scope", "$location", "UserFactory", function ($scope, $location, UserFactory) {
                $scope.error = null;
                $scope.save = function () {
                    UserFactory.create({
                        firstname: $scope.newuser.firstname,
                        lastname: $scope.newuser.lastname,
                        email: $scope.newuser.email,
                        phone: $scope.newuser.phone,
                        password: $scope.newuser.password
                    }).then(function () {
                        $scope.error = null;
                        $location.path("/flyList");
                    }, function () {
                        $scope.error = "user already exist, please find another email";
                    });
                };
            }]);