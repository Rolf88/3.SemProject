describe('myApp.factories', function () {


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

    beforeEach(module('myApp.factories'));

    describe('FlightFactory', function () {
        var flightFactory;
        
        beforeEach(inject(function (_FlightFactory_) {
            flightFactory = _FlightFactory_;
        }));

        it('Should be Hello World from a Factory', function () {
            expect(infoFactory.getInfo()).toBe("Hello World from a Factory");
        });
    });


    describe('Your Own Factory', function () {

    });
});