INSERT INTO accommodations (name, description, min_guest, max_guest, cancellation_deadline, status, manual, accommodation_type, price_per, address, city, country, zip_code, deleted)
VALUES ('Downtown Loft', 'Experience urban living at its finest in our stylish Downtown Loft. This modern loft offers contemporary design and is centrally located for easy access to city attractions and nightlife.', 2, 4, 2, 'APPROVED', true, 'HOTEL', 'PERSON', 'Øster Voldgade 4', 'Copenhagen', 'Denmark', '1350', false);

INSERT INTO pricelist_items (start_date, end_date, price)
VALUES ('2024-03-01', '2024-03-30', 15);

INSERT INTO accommodations_price_list (accommodation_id, price_list_id)
VALUES (1, 1);

INSERT INTO availability (start_date, end_date)
VALUES ('2024-03-05', '2024-03-30');

INSERT INTO accommodations_availability (accommodation_id, availability_id)
VALUES (1, 1);