INSERT INTO airport (id, iataCode, `name`) VALUES (1, 'CPH', 'Kastrup lufthavn');
INSERT INTO airport (id, iataCode, `name`) VALUES (2, 'BIL', 'Billund lufthavn');
INSERT INTO airline (id, `name`) VALUES (1, '42 Airlines');
INSERT INTO flight (id, flightId, `capacity`, `departure`, price, airline_id, destination_id, origin_id) VALUES (1, 'MCA2345', 100, CURRENT_TIMESTAMP, 20, 1, 1, 2);