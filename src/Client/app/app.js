'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'ngAnimate',
    'ui.bootstrap',
    'myApp.security',
    'myApp.filters',
    'myApp.directives',
    'myApp.factories',
    'myApp.services',
    'myApp.flyList',
    'myApp.createUser',
    'myApp.reservation',
    'myApp.admin'
]).
        config(['$routeProvider', function ($routeProvider) {
                $routeProvider.otherwise({redirectTo: '/flyList'});
            }]).
        config(function ($httpProvider) {
            $httpProvider.interceptors.push('authInterceptor');
        });


