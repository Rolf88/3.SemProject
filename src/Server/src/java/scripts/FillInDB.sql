-- inserts an admin user
INSERT INTO userprofile (email, firstname, lastname, password, phone) VALUES ('test', 'tester', 'testersen', '1000:8abb5b9475c48697a75e9370b4c401b523ebbeb2d29f2b03:4f6ce16476f161a0eb60eac41efc2bbc5c395f076083d1e7', '45875478');

SET @UserId = LAST_INSERT_ID();

INSERT INTO userprofile_roles (userprofile_id, roles) VALUES (@UserId,'User');
INSERT INTO userprofile_roles (userprofile_id, roles) VALUES (@UserId,'Admin');

-- insert default user

INSERT INTO userprofile (email, firstname, lastname, password, phone) VALUES ('test2', 'tester', 'testersen', '1000:8abb5b9475c48697a75e9370b4c401b523ebbeb2d29f2b03:4f6ce16476f161a0eb60eac41efc2bbc5c395f076083d1e7', '45875478');

SET @UserId = LAST_INSERT_ID();

INSERT INTO userprofile_roles (userprofile_id, roles) VALUES (@UserId,'User');
-- inserts flight_api_urls

INSERT INTO flightapiurls(url) VALUES ('http://angularairline-plaul.rhcloud.com/'); 
INSERT INTO flightapiurls(url) VALUES ('http://timetravel-tocvfan.rhcloud.com/'); 
INSERT INTO flightapiurls(url) VALUES ('http://flightairline-cphol24.rhcloud.com/AirlineSystem/'); 