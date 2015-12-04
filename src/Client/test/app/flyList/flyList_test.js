'use strict';

describe('myApp.flyList module', function () {

    beforeEach(module('myApp.flyList'));

    var SEARCH_ITEMS = {
        "airline": "MyCoolAirline",
        "flights": [
            {
                "date": "2016-02-25T11:30:00.000Z",
                "numberOfSeats": 2,
                "totalPrice": 400,
                "flightID": "MCA2345",
                "traveltime": 190,
                "destination": "CPH",
                "origin": "BCN"
            },
            {
                "date": "2016-02-25T11:30:00.000Z",
                "numberOfSeats": 2,
                "totalPrice": 400,
                "flightID": "MCA2347",
                "traveltime": 190,
                "destination": "CPH",
                "origin": "BCN"
            }
        ]
    };

    var FlightFactoryMock = null;

    //Mocks for the test
    beforeEach(module(function ($provide) {
        $provide.factory("FlightFactory", function ($q) {
            var searchImpl = jasmine.createSpy("search").and.callFake(function () {
                return $q.when(SEARCH_ITEMS);
            });

            return {
                search: searchImpl
            }
        });
    }));

    beforeEach(inject(function (FlightFactory) {
        FlightFactoryMock = FlightFactory;
    }));

    describe('FlightListController controller', function () {

        it('should use FlightListController', inject(function ($controller) {
            var ctrl = $controller('FlightListController');

            expect(ctrl).toBeDefined();
        }));

        it('should have a empty list of flight when initialize', inject(function ($controller) {
            var ctrl = $controller('FlightListController');

            expect(ctrl.flights).toEqual([]);
        }));

        it('should have default values when initialize', inject(function ($controller) {
            var ctrl = $controller('FlightListController');

            expect(ctrl.origin).toEqual("");
            expect(ctrl.destination).toEqual("");
            expect(ctrl.date).toEqual("");
            expect(ctrl.numberOfPassengers).toEqual(2);
        }));

        it('should call FlightFactory on search', inject(function ($controller) {
            var ctrl = $controller('FlightListController');
            ctrl.origin = "BCN";
            ctrl.destination = "CPH";
            ctrl.date = new Date("2016-02-25T11:30:00.000Z");

            ctrl.searchFlight();

            expect(FlightFactoryMock.search).toHaveBeenCalled();
        }));

    });
});