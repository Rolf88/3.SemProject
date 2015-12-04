'use strict';

/* Place your Global Filters in this file */

angular.module('myApp.filters', []).
  filter('checkmark', function () {
    return function(input) {
      return input ? '\u2713' : '\u2718';
    };
    
  })
    .filter('minutesToDateTime', [function() {
    return function(minutes) {
        return new Date(1970, 0, 1).setMinutes(minutes);
    };
}]);
