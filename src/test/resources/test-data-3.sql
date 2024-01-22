INSERT INTO users (type, is_active, time, address, city, country, zip_code, blocked, email, first_name, last_name, password, phone, profile_image_id, deleted, hash_token)
VALUES ('ADMIN', true,'2023-11-30 12:30:00', 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', '21000', false, 'admin@example.com', 'pera', 'peric', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+3816213421', null, false, ''),
       ('GUEST', true, '2023-11-30 12:30:00', 'Some Street 123', 'Cityville', 'Croatia', '12345', false, 'guest@example.com', 'John', 'Doe', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+1234567890', null, false, ''),
       ('OWNER', true,'2023-11-30 12:30:00', 'Another Road 789', 'Townsville', 'Austria', '67890', false, 'owner@example.com', 'Alice', 'Smith', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+9876543210', null, false, ''),
       ('GUEST', true, '2023-11-30 12:30:00', 'Some Street 123', 'Cityville', 'Croatia', '12345', false, 'guest2@example.com', 'John', 'Doe', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+1234567890', null, false, ''),
       ('GUEST', true, '2023-11-30 12:30:00', 'Some Street 123', 'Cityville', 'Croatia', '12345', false, 'guest3@example.com', 'John', 'Doe', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+1234567890', null, false, '');


INSERT INTO accommodations (name, description, min_guest, max_guest, cancellation_deadline, status, manual, accommodation_type, price_per, address, city, country, zip_code, deleted) VALUES ('Downtown Loft', 'Experience urban living at its finest in our stylish Downtown Loft. This modern loft offers contemporary design and is centrally located for easy access to city attractions and nightlife.',2, 4, 2, 'APPROVED', true, 'HOTEL', 'PERSON', 'Øster Voldgade 4', 'Copenhagen', 'Denmark', '1350', false),
                                                                                                                                                                                             ('Riverside Cabin', 'Escape to nature in our Riverside Cabin. Tucked away by the river, this cozy cabin provides a peaceful retreat with the soothing sounds of nature.', 2, 4, 3, 'APPROVED', false, 'HOTEL', 'PERSON', 'Øster Voldgade 5', 'Copenhagen', 'Denmark', '1350', false),
                                                                                                                                                                                             ('Hotel na zlatiboru', 'Escape to nature in our Riverside Cabin. Tucked away by the river, this cozy cabin provides a peaceful retreat with the soothing sounds of nature.', 2, 4, 3, 'APPROVED', true, 'HOTEL', 'PERSON', 'Øster Voldgade 5', 'Copenhagen', 'Denmark', '1350', false),
                                                                                                                                                                                             ('Test reservation repo', 'Escape to nature in our Riverside Cabin. Tucked away by the river, this cozy cabin provides a peaceful retreat with the soothing sounds of nature.', 2, 4, 3, 'APPROVED', false, 'HOTEL', 'PERSON', 'Øster Voldgade 5', 'Copenhagen', 'Denmark', '1350', false);


INSERT INTO users_accommodations (owner_id, accommodations_id)
VALUES (3, 1),
       (3, 2),
       (3, 3);

INSERT INTO pricelist_items (start_date, end_date, price)
VALUES ('2024-04-01', '2024-04-30', 20),
       ('2024-10-01', '2024-10-30', 30),
       ('2024-10-01', '2024-10-30', 30);

INSERT INTO accommodations_price_list (accommodation_id, price_list_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);

-- no overlap
-- it's intentional for every second reservation in clauses below to be ACCEPTED
-- we are checking if it will NOT get them because those are the reason we are cancelling reservations

INSERT INTO reservations (created, start, guest_number, end, status, accommodation_id, guest_id, price)
VALUES
    ('2025-03-28', '2025-03-01', 2, '2025-03-10', 'PENDING', 1, 2, 100),
    ('2025-03-02', '2025-03-15', 3, '2025-03-25', 'ACCEPTED', 1, 2, 120),
    ('2025-03-02', '2025-03-15', 3, '2025-03-25', 'CANCELED', 1, 2, 120);

-- partial overlapping
-- ending are same
INSERT INTO reservations (created, start, guest_number, end, status, accommodation_id, guest_id, price)
VALUES
    ('2025-04-01', '2025-04-01', 2, '2025-04-20', 'PENDING', 1, 2, 100),
    ('2025-04-01', '2025-04-15', 3, '2025-04-20', 'ACCEPTED', 1, 1, 120),
    ('2025-04-01', '2025-04-15', 3, '2025-04-20', 'REJECTED', 1, 1, 120);

-- start are same
INSERT INTO reservations (created, start, guest_number, end, status, accommodation_id, guest_id, price)
VALUES
    ('2025-04-01', '2025-05-01', 2, '2025-05-20', 'PENDING', 1, 2, 100),
    ('2025-04-01', '2025-05-01', 3, '2025-05-15', 'ACCEPTED', 1, 1, 120),
    ('2025-04-01', '2025-05-01', 3, '2025-05-15', 'CANCELED', 1, 1, 120);
-- e1 after s2
INSERT INTO reservations (created, start, guest_number, end, status, accommodation_id, guest_id, price)
VALUES
    ('2025-06-01', '2025-06-10', 2, '2025-06-20', 'PENDING', 1, 2, 100),
    ('2025-06-01', '2025-06-15', 3, '2025-06-30', 'ACCEPTED', 1, 1, 120),
    ('2025-06-01', '2025-06-15', 3, '2025-06-30', 'CANCELED', 1, 2, 120);

-- e2 after s1
INSERT INTO reservations (created, start, guest_number, end, status, accommodation_id, guest_id, price)
VALUES
    ('2025-07-01', '2025-07-15', 2, '2025-07-20', 'PENDING', 1, 1, 100),
    ('2025-07-01', '2025-07-01', 3, '2025-07-20', 'ACCEPTED', 1, 2, 120);

-- start and end
INSERT INTO reservations (created, start, guest_number, end, status, accommodation_id, guest_id, price)
VALUES
    ('2025-08-01', '2025-08-15', 2, '2025-08-20', 'PENDING', 1, 2, 100),
    ('2025-08-01', '2025-08-15', 3, '2025-08-20', 'ACCEPTED', 1, 2, 120);

-- complete overlap - containing one in another
INSERT INTO reservations (created, start, guest_number, end, status, accommodation_id, guest_id, price)
VALUES
    ('2025-09-01', '2025-09-01', 2, '2025-09-30', 'PENDING', 1, 2, 100),
    ('2025-09-01', '2025-09-10', 3, '2025-09-20', 'ACCEPTED', 1, 2, 120);

INSERT INTO reservations (created, start, guest_number, end, status, accommodation_id, guest_id, price)
VALUES
    ('2025-10-01', '2025-10-10', 3, '2025-10-20', 'PENDING', 1, 2, 120),
    ('2025-10-01', '2025-10-01', 2, '2025-10-30', 'ACCEPTED', 1, 2, 100);


INSERT INTO availability (start_date, end_date)
VALUES
    ('2024-03-01', '2024-03-20'), ('2024-03-25', '2024-03-30'), ('2024-03-01', '2024-03-10'), ('2024-03-12', '2024-03-20'),
    ('2024-03-05', '2024-03-07'), ('2024-03-10', '2024-03-13');

INSERT INTO accommodations_availability (accommodation_id, availability_id)
VALUES
    (1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (3, 6);

INSERT INTO availability (start_date, end_date)
VALUES
    ('2027-12-04', '2027-12-10');

INSERT INTO accommodations_availability (accommodation_id, availability_id)
VALUES
    (1, 7);