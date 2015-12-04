INSERT INTO airport (id, iataCode, `name`) VALUES (1, 'CPH', 'Kastrup lufthavn');
INSERT INTO airport (id, iataCode, `name`) VALUES (2, 'BIL', 'Billund lufthavn');
INSERT INTO airport (id, iataCode, `name`) VALUES (3, 'VAR', 'Vanlose lufthavn');
INSERT INTO airport (id, iataCode, `name`) VALUES (4, 'INT', 'Ballerup lufthavn');
INSERT INTO airport (id, iataCode, `name`) VALUES (5, 'LIP', 'Husum lufthavn');
INSERT INTO airport (id, iataCode, `name`) VALUES (6, 'KOH', 'Herlev lufthavn');

INSERT INTO airline (id, `name`) VALUES (1, '42 Airlines');

INSERT INTO flight (id, flightId, `capacity`, `departure`, price, traveltime, airline_id, destination_id, origin_id) 
VALUES (1, 'MCA2345', 100, CURRENT_TIMESTAMP, 200, 100, 1, 2, 1);

INSERT INTO flight (id, flightId, `capacity`, `departure`, price, traveltime, airline_id, destination_id, origin_id) 
VALUES (2, 'jkl5656', 100, CURRENT_TIMESTAMP, 205, 100, 1, 3, 1);

INSERT INTO flight (id, flightId, `capacity`, `departure`, price, traveltime, airline_id, destination_id, origin_id) 
VALUES (3, 'isk4568', 100, CURRENT_TIMESTAMP, 500, 100, 1, 4, 1);

INSERT INTO flight (id, flightId, `capacity`, `departure`, price, traveltime, airline_id, destination_id, origin_id) 
VALUES (4, 'TOl4569', 100, CURRENT_TIMESTAMP, 520, 100, 1, 5, 1);

INSERT INTO flight (id, flightId, `capacity`, `departure`, price, traveltime, airline_id, destination_id, origin_id) 
VALUES (5, 'HUJ4587', 100, CURRENT_TIMESTAMP, 1020, 100, 1, 6, 1);