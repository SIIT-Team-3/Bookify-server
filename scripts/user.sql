INSERT INTO users (type, is_active, time, address, city, country, zip_code, blocked, email, first_name, last_name, password, phone, profile_image_id)
VALUES 
('ADMIN', false,'2023-11-30 12:30:00', 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', '21000', false, 'test.example@uns.ac.rs', 'pera', 'peric', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+3816213421', null),
('GUEST', true, '2023-11-30 12:30:00', 'Some Street 123', 'Cityville', 'Countryland', '12345', false, 'guest@example.com', 'John', 'Doe', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+1234567890', null),
('OWNER', false,'2023-11-30 12:30:00', 'Another Road 789', 'Townsville', 'Countryland', '67890', true, 'owner@example.com', 'Alice', 'Smith', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+9876543210', null),
('GUEST', false,'2023-11-30 12:30:00', 'Test Street 42', 'Test City', 'Test Country', '54321', true, 'admin@example.com', 'Admin', 'User', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+1122334455', null),
('GUEST', true, '2023-11-28 08:00:00', 'Guest Lane 87', 'Guestville', 'Countryland', '98765', false, 'guest2@example.com', 'Jane', 'Doe', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+9988776655', null),
('OWNER', false,'2023-11-30 12:30:00', 'Owner Avenue 567', 'Ownertown', 'Countryland', '45678', false, 'owner2@example.com', 'Bob', 'Johnson', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+6655443322', null),
('OWNER', true, '2023-11-29 15:45:00', 'Admin Road 321', 'Admin City', 'Adminland', '13579', true, 'admin2@example.com', 'Admin', 'Tester', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+2233445566', null),
('GUEST', false,'2023-11-30 12:30:00', 'Another Guest Street 99', 'Guestropolis', 'Countryland', '11223', true, 'guest3@example.com', 'Sam', 'White', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+7788990011', null),
('OWNER', true, '2023-11-27 20:20:00', 'Owner Street 876', 'Owner City', 'Countryland', '554433', false, 'owner3@example.com', 'Eva', 'Brown', 'ownerpass3', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', null),
('GUEST', false,'2023-11-30 12:30:00', 'Admin Lane 765', 'Adminville', 'Adminland', '332211', false, 'admin3@example.com', 'Chris', 'Miller', 'adminpass3', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', null)
;