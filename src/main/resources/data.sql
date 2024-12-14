INSERT INTO location (code, name, address) VALUES ('1', 'Ozyegin University', 'Nisantepe');
INSERT INTO location (code, name, address) VALUES ('2', 'Kadikoy', 'Kadikoy, Istanbul');

INSERT INTO car (barcode, brand, model, car_type, mileage, transmission_type, daily_price, status) VALUES ('MER101', 'Mercedes', 'Maybach', 'Standard', 1000, 'Automatic', 200.0, 'AVAILABLE');
INSERT INTO car (barcode, brand, model, car_type, mileage, transmission_type, daily_price, status) VALUES ('BMW202', 'BMW', 'X5', 'SUV', 5000, 'Manual', 150.0, 'AVAILABLE');

INSERT INTO member (name, address, email, phone, drivingLicenseNumber) 
VALUES ( 'Khaled', 'Nisantepe', 'khaled.sharafeddin@ozu.edu.tr', '555-1234', 'D123456');
INSERT INTO member (name, address, email, phone, drivingLicenseNumber) 
VALUES ( 'Zohair', 'Tasdelen', 'zohair@ozu.edu.tr', '555-5678', 'D654321');
INSERT INTO member (name, address, email, phone, drivingLicenseNumber) 
VALUES ( 'Umair', 'Besiktas', 'umair@ozu.edu.tr', '555-9101', 'D789123');

INSERT INTO reservation (reservationNumber, creationDate, pickUpDateTime, dropOffDateTime, returnDate, status, member_id, car_id, pickupLocation_id, dropOffLocation_id) 
VALUES ('RES001', '2024-12-01', '2024-12-02 10:00:00', '2024-12-05 10:00:00', NULL, 'ACTIVE', 1, 1, 1, 2);
INSERT INTO reservation (reservationNumber, creationDate, pickUpDateTime, dropOffDateTime, returnDate, status, member_id, car_id, pickupLocation_id, dropOffLocation_id) 
VALUES ('RES002', '2024-12-03', '2024-12-04 09:00:00', '2024-12-06 18:00:00', '2024-12-06', 'COMPLETED', 2, 2, 3, 4);
INSERT INTO reservation (reservationNumber, creationDate, pickUpDateTime, dropOffDateTime, returnDate, status, member_id, car_id, pickupLocation_id, dropOffLocation_id) 
VALUES ('RES003', '2024-12-04', '2024-12-05 08:30:00', '2024-12-07 14:00:00', NULL, 'RESERVED', 3, 4, 5, 1);
