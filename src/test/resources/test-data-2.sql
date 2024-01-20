INSERT INTO users (type, is_active, time, address, city, country, zip_code, blocked, email, first_name, last_name, password, phone, profile_image_id, deleted, hash_token)
VALUES ('ADMIN', true,'2023-11-30 12:30:00', 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', '21000', false, 'admin@example.com', 'pera', 'peric', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+3816213421', null, false, ""),
    ('GUEST', true, '2023-11-30 12:30:00', 'Some Street 123', 'Cityville', 'Croatia', '12345', false, 'guest@example.com', 'John', 'Doe', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+1234567890', null, false, ""),
    ('OWNER', true,'2023-11-30 12:30:00', 'Another Road 789', 'Townsville', 'Austria', '67890', false, 'owner@example.com', 'Alice', 'Smith', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+9876543210', null, false, ""),
    ('GUEST', true, '2023-11-30 12:30:00', 'Some Street 123', 'Cityville', 'Croatia', '12345', false, 'guest2@example.com', 'John', 'Doe', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+1234567890', null, false, ""),
    ('GUEST', true, '2023-11-30 12:30:00', 'Some Street 123', 'Cityville', 'Croatia', '12345', false, 'guest3@example.com', 'John', 'Doe', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+1234567890', null, false, "");


INSERT INTO accommodations (name, description, min_guest, max_guest, cancellation_deadline, status, manual, accommodation_type, price_per, address, city, country, zip_code, deleted) VALUES ('Downtown Loft', 'Experience urban living at its finest in our stylish Downtown Loft. This modern loft offers contemporary design and is centrally located for easy access to city attractions and nightlife.',2, 4, 2, 'APPROVED', true, 'HOTEL', 'PERSON', 'Øster Voldgade 4', 'Copenhagen', 'Denmark', '1350', false),
       ('Riverside Cabin', 'Escape to nature in our Riverside Cabin. Tucked away by the river, this cozy cabin provides a peaceful retreat with the soothing sounds of nature.', 2, 4, 3, 'APPROVED', false, 'HOTEL', 'PERSON', 'Øster Voldgade 5', 'Copenhagen', 'Denmark', '1350', false),
       ('Hotel na zlatiboru', 'Escape to nature in our Riverside Cabin. Tucked away by the river, this cozy cabin provides a peaceful retreat with the soothing sounds of nature.', 2, 4, 3, 'APPROVED', true, 'HOTEL', 'PERSON', 'Øster Voldgade 5', 'Copenhagen', 'Denmark', '1350', false);

INSERT INTO availability (start_date, end_date) VALUES ('2023-04-01', '2023-04-30'), ('2024-10-01', '2024-10-30'), ('2024-10-01', '2024-10-30');

INSERT INTO accommodations_availability (accommodation_id, availability_id) VALUES (1, 1), (2, 2), (3, 3);

INSERT INTO users_accommodations (owner_id, accommodations_id)
VALUES (3, 1),
       (3, 2),
       (3, 3);

INSERT INTO reservations (id, created, start, guest_number, end, status, accommodation_id, guest_id, price)
VALUES (1, '2023-10-01 10:00:00', '2023-11-05', 2, '2023-11-10', 'PENDING', 1, 2, 100),
       (2, '2023-09-02 14:30:00', '2024-03-08', 1, '2024-03-10', 'ACCEPTED', 1, 2, 200),
       (3, '2023-06-03 08:45:00', '2023-07-07', 3, '2023-08-02', 'ACCEPTED', 1, 2, 100),
       (4, '2023-05-04 12:30:00', '2024-06-06', 4, '2024-06-13', 'PENDING', 1, 2, 150),
       (5, '2023-05-04 12:30:00', '2024-06-06', 4, '2024-06-13', 'REJECTED', 1, 2, 150),
       (6, '2023-05-04 12:30:00', '2024-06-06', 4, '2024-06-13', 'CANCELED', 1, 2, 150),
       (7, '2023-05-04 12:30:00', '2024-06-06', 4, '2024-06-13', 'ACCEPTED', 1, 2, 150),
       (8, '2023-03-05 20:15:00', '2024-05-09', 2, '2024-05-14', 'PENDING', 1, 2, 120),
       (9, '2023-04-06 16:30:00', '2024-04-10', 1, '2024-04-15', 'PENDING', 1, 2, 130),
       (10, '2023-04-06 16:30:00', '2024-04-10', 1, '2024-04-15', 'PENDING', 1, 2, 130),
       (11, '2023-04-06 16:30:00', '2024-04-10', 1, '2024-04-15', 'PENDING', 1, 2, 130),
       (12, '2023-04-06 16:30:00', '2024-04-10', 1, '2024-04-15', 'PENDING', 1, 2, 130),
       (13, '2023-04-06 16:30:00', '2025-02-10', 1, '2025-02-15', 'PENDING', 1, 2, 130),
       (15, '2023-05-04 12:30:00', '2024-06-06', 4, '2024-06-13', 'PENDING', 1, 2, 150);

INSERT INTO reservations (id, created, start, guest_number, end, status, accommodation_id, guest_id, price)
VALUES
    (16, '2024-05-28 10:00:00', '2024-06-05', 2, '2024-06-15', 'PENDING', 1, 2, 100),
    (17, '2024-06-02 12:00:00', '2024-06-08', 3, '2024-06-18', 'PENDING', 1, 2, 120),
    (18, '2024-06-03 15:00:00', '2024-06-12', 1, '2024-06-20', 'PENDING', 1, 2, 150),
    (19, '2024-06-04 18:00:00', '2024-06-16', 2, '2024-06-22', 'PENDING', 1, 2, 180),
    (20, '2024-06-05 20:00:00', '2024-06-20', 3, '2024-06-25', 'PENDING', 1, 2, 200);


INSERT INTO pricelist_items (start_date, end_date, price)
VALUES ('2024-04-01', '2024-04-30', 20),
       ('2024-10-01', '2024-10-30', 30),
       ('2024-10-01', '2024-10-30', 30);

INSERT INTO accommodations_price_list (accommodation_id, price_list_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);


INSERT INTO accommodations_availability (accommodation_id, availability_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);