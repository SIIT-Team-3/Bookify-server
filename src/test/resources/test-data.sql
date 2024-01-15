
INSERT INTO users (type, is_active, time, address, city, country, zip_code, blocked, email, first_name, last_name, password, phone, profile_image_id, deleted, hash_token)
VALUES ('GUEST', true, '2023-11-30 12:30:00', 'Trg Dositeja Obradovica 6', 'Novi Sad', 'Serbia', '21000', false, 'test@example.com', 'pera', 'peric', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '+3816213421', null, false, '');

INSERT INTO accommodations (name, description, min_guest, max_guest, cancellation_deadline, status, manual, accommodation_type, price_per, address, city, country, zip_code, deleted)
VALUES
    ('Downtown Loft', 'Experience urban living at its finest in our stylish Downtown Loft. This modern loft offers contemporary design and is centrally located for easy access to city attractions and nightlife.', 2, 4, 2, 'APPROVED', true, 'HOTEL', 'PERSON', 'Øster Voldgade 4', 'Copenhagen', 'Denmark', '1350', false),
    ('Riverside Cabin', 'Escape to nature in our Riverside Cabin. Tucked away by the river, this cozy cabin provides a peaceful retreat with the soothing sounds of nature.', 2, 4, 3, 'APPROVED', true, 'HOTEL', 'PERSON', 'Øster Voldgade 5', 'Copenhagen', 'Denmark', '1350',false),
    ('Tranquil Lakehouse', 'Indulge in serenity at our Tranquil Lakehouse. This picturesque retreat is nestled by a pristine lake, offering a perfect blend of relaxation and natural beauty. With a minimum capacity of 4 guests and a maximum capacity of 8, it is an ideal choice for family vacations or a peaceful getaway with friends.', 4, 8, 1, 'APPROVED', true, 'HOTEL', 'PERSON', 'Øster Voldgade 6', 'Copenhagen', 'Denmark', '1350', false),
    ('Historic Manor', 'Step back in time at our Historic Manor. This grand estate exudes charm and elegance, offering a unique experience for those who appreciate the beauty of the past. With a minimum capacity of 10 guests and a maximum capacity of 15, it is perfect for special occasions, events, or a luxurious retreat.', 3, 5, 2, 'APPROVED', true, 'HOTEL', 'PERSON', 'Øster Voldgade 7', 'Copenhagen', 'Denmark', '1350', false),
    ('Luxury Penthouse Suite', 'Elevate your stay in the city with our Luxury Penthouse Suite. This opulent retreat boasts panoramic city views, exquisite furnishings, and premium amenities. With a minimum capacity of 2 guests and a maximum capacity of 4, it is the epitome of urban sophistication.', 2, 4, 1, 'APPROVED', true, 'HOTEL', 'PERSON', 'Dag Hammarskjölds torg 2', 'Malmö', 'Sweden', '211 18', false),
    ('Secluded Mountain Chalet', 'Discover tranquility in our Secluded Mountain Chalet. Tucked away in the hills, this charming chalet offers a cozy escape surrounded by the beauty of nature. With a minimum capacity of 6 guests and a maximum capacity of 10, it is perfect for family gatherings or a peaceful mountain retreat.', 1, 1, 3, 'APPROVED', true, 'ROOM', 'PERSON', 'Dag Hammarskjölds torg 1', 'Malmö', 'Sweden', '211 18', false),
    ('Beachfront Bungalow', 'Experience the epitome of coastal living in our Beachfront Bungalow. This charming abode is just steps away from the sandy shores, offering a perfect retreat for beach lovers. With a minimum capacity of 2 guests and a maximum capacity of 4, it is an ideal getaway for couples or a small family.', 2, 4, 2, 'APPROVED', true, 'ROOM', 'PERSON', 'Dag Hammarskjölds torg 3', 'Malmö', 'Sweden', '211 18', false),
    ('Urban Oasis Apartment', 'Discover an urban oasis in our stylish apartment. Centrally located, this modern space provides a peaceful retreat in the heart of the city. With a minimum capacity of 1 guest and a maximum capacity of 2, it is perfect for solo travelers or a cozy couple’s getaway.', 1, 2, 1, 'APPROVED', true, 'APARTMENT', 'PERSON', 'Üllői út 4', ' Budapest', 'Hungary', '1091', false),
    ('Charming Vineyard Cottage', 'Escape to the countryside in our Charming Vineyard Cottage. Surrounded by vineyards and rolling hills, this quaint cottage offers a peaceful retreat for wine enthusiasts. With a minimum capacity of 4 guests and a maximum capacity of 6, it is an ideal getaway for a small group or family.', 4, 5, 2, 'APPROVED', true, 'ROOM', 'PERSON', 'Üllői út 5', ' Budapest', 'Hungary', '1091', false),
    ('Serenity Beach House', 'Unwind at our Serenity Beach House, a hidden gem on the coast. This spacious beachfront property offers stunning ocean views, making it an ideal retreat for those seeking relaxation by the sea. With a minimum capacity of 6 guests and a maximum capacity of 10, it is perfect for family vacations or a gathering of friends.', 4, 6, 3, 'CREATED', true, 'ROOM', 'PERSON', 'Üllői út 6', ' Budapest', 'Hungary', '1091', false),
    ('Mountain View Lodge', 'Experience the beauty of nature at our Mountain View Lodge. Surrounded by majestic peaks, this cozy lodge offers a warm and inviting atmosphere. With a minimum capacity of 8 guests and a maximum capacity of 12, it is an excellent choice for group retreats or family gatherings in the mountains.', 2, 2, 4, 'APPROVED', true, 'ROOM', 'PERSON', 'Üllői út 7', ' Budapest', 'Hungary', '1091', false),
    ('Rustic Farmhouse Retreat', 'Escape to the countryside in our Rustic Farmhouse Retreat. This charming farmhouse, surrounded by rolling fields, offers a peaceful escape from the hustle and bustle. With a minimum capacity of 4 guests and a maximum capacity of 8, it is perfect for a family getaway or a quiet retreat with friends.', 4, 6, 4, 'APPROVED', true, 'ROOM', 'PERSON', 'Üllői út 8', ' Budapest', 'Hungary', '1091', false),
    ('Elegant City Townhouse', 'Discover sophistication in our Elegant City Townhouse. Located in a historic district, this beautifully appointed townhouse offers a luxurious urban escape. With a minimum capacity of 4 guests and a maximum capacity of 6, it is perfect for a stylish city retreat with friends or family.', 4, 6, 3, 'APPROVED', true, 'ROOM', 'PERSON', 'Üllői út 9', ' Budapest', 'Hungary', '1091', false),
    ('Oceanfront Paradise Villa', 'Experience the epitome of luxury at our Oceanfront Paradise Villa. This exclusive villa boasts breathtaking ocean views, private amenities, and impeccable design. With a minimum capacity of 8 guests and a maximum capacity of 12, it is an ideal choice for a lavish seaside retreat or special occasions.', 3, 4, 2, 'APPROVED', true, 'APARTMENT', 'PERSON', 'Üllői út 10', ' Budapest', 'Hungary', '1091', false),
    ('Enchanted Forest Cabin', 'Immerse yourself in nature at our Enchanted Forest Cabin. Surrounded by ancient trees and wildlife, this cozy cabin offers a magical escape from the everyday. With a minimum capacity of 2 guests and a maximum capacity of 4, it is perfect for a romantic getaway or a peaceful retreat for nature lovers.', 2, 4, 2, 'APPROVED', true, 'APARTMENT', 'PERSON', 'Dag Hammarskjölds torg 10', 'Malmö', 'Sweden', '211 18', false),
    ('Skyline View Penthouse', 'Indulge in luxury with our Skyline View Penthouse. Perched high above the city, this penthouse offers stunning panoramic views, modern amenities, and a sophisticated ambiance. With a minimum capacity of 4 guests and a maximum capacity of 6, it is perfect for a stylish urban getaway with friends or family.', 4, 6, 1, 'APPROVED', true, 'APARTMENT', 'PERSON', 'Dag Hammarskjölds torg 13', 'Malmö', 'Sweden', '211 18', false),
    ('Coastal Clifftop Cottage', 'Perched on a coastal cliff, our Clifftop Cottage offers unrivaled views of the ocean. This charming cottage provides a tranquil and romantic escape, ideal for couples seeking a secluded getaway. With a minimum capacity of 2 guests and a maximum capacity of 3, it is a perfect retreat for those desiring privacy and breathtaking scenery.', 2, 3, 1, 'APPROVED', true, 'ROOM', 'ROOM', 'Dag Hammarskjölds torg 12', 'Malmö', 'Sweden', '211 18', false),
    ('Oceanfront Paradise Villa', 'Experience the epitome of luxury at our Oceanfront Paradise Villa. This exclusive villa boasts breathtaking ocean views, private amenities, and impeccable design. With a minimum capacity of 8 guests and a maximum capacity of 12, it is an ideal choice for a lavish seaside retreat or special occasions.', 1, 3, 1, 'APPROVED', true, 'APARTMENT', 'ROOM', 'Dag Hammarskjölds torg 14', 'Malmö', 'Sweden', '211 18', false),
    ('Enchanted Forest Cabin', 'Immerse yourself in nature at our Enchanted Forest Cabin. Surrounded by ancient trees and wildlife, this cozy cabin offers a magical escape from the everyday. With a minimum capacity of 2 guests and a maximum capacity of 4, it is perfect for a romantic getaway or a peaceful retreat for nature lovers.', 2, 4, 1, 'APPROVED', true, 'ROOM', 'ROOM', 'Øster Voldgade 8', 'Copenhagen', 'Denmark', '1350', false),
    ('Skyline View Penthouse', 'Indulge in luxury with our Skyline View Penthouse. Perched high above the city, this penthouse offers stunning panoramic views, modern amenities, and a sophisticated ambiance. With a minimum capacity of 4 guests and a maximum capacity of 6, it is perfect for a stylish urban getaway with friends or family.', 4, 5, 3, 'APPROVED', true, 'APARTMENT', 'ROOM', 'Øster Voldgade 9', 'Copenhagen', 'Denmark', '1350', false),
    ('Coastal Clifftop Cottage', 'Perched on a coastal cliff, our Clifftop Cottage offers unrivaled views of the ocean. This charming cottage provides a tranquil and romantic escape, ideal for couples seeking a secluded getaway. With a minimum capacity of 2 guests and a maximum capacity of 3, it is a perfect retreat for those desiring privacy and breathtaking scenery.', 2, 3, 4, 'APPROVED', true, 'ROOM', 'ROOM', 'Øster Voldgade 10', 'Copenhagen', 'Denmark', '1350', false),
    ('Sunny Beachfront Villa', 'Bask in the sun at our Sunny Beachfront Villa. This luxurious villa offers direct access to the beach, private pool, and stunning ocean views. With a minimum capacity of 8 guests and a maximum capacity of 12, it is perfect for a tropical getaway or a seaside celebration.', 3, 5, 5, 'APPROVED', true, 'APARTMENT', 'ROOM', 'Øster Voldgade 11', 'Copenhagen', 'Denmark', '1350', false),
    ('Urban Loft Living', 'Embrace city life in our Urban Loft Living space. Located in the heart of downtown, this modern loft offers stylish design, convenience, and easy access to the vibrant urban scene. With a minimum capacity of 2 guests and a maximum capacity of 4, it is perfect for urban explorers or a chic city retreat.', 2, 4, 2, 'APPROVED', true, 'APARTMENT', 'PERSON', 'Øster Voldgade 12', 'Copenhagen', 'Denmark', '1350', false),
    ('Majestic Mountain Manor', 'Experience grandeur at our Majestic Mountain Manor. Perched high in the mountains, this elegant manor offers opulent interiors, breathtaking views, and a luxurious escape. With a minimum capacity of 10 guests and a maximum capacity of 15, it is an ideal choice for special occasions or an extravagant mountain retreat.', 1, 5, 3, 'APPROVED', true, 'ROOM', 'ROOM', 'Üllői út 1', ' Budapest', 'Hungary', '1091', false),
    ('Riverside Haven Cottage', 'Escape to a Riverside Haven in our charming cottage by the river. Surrounded by nature, this cozy retreat offers tranquility and a connection to the outdoors. With a minimum capacity of 4 guests and a maximum capacity of 6, it is perfect for a family getaway or a peaceful retreat with friends.', 4, 7, 2, 'EDITED', true, 'ROOM', 'ROOM', 'Üllői út 2', ' Budapest', 'Hungary', '1091', false),
    ('Artisanal Village House', 'Discover the charm of village life in our Artisanal Village House. This rustic yet stylish house offers a unique blend of tradition and comfort. With a minimum capacity of 6 guests and a maximum capacity of 8, it is perfect for those seeking an authentic village experience.', 2, 5, 1, 'APPROVED', true, 'APARTMENT', 'PERSON', 'Üllői út 3', ' Budapest', 'Hungary', '1091', false),
    ('Sunny Beachfront Villa', 'Bask in the sun at our Sunny Beachfront Villa. This luxurious villa offers direct access to the beach, private pool, and stunning ocean views. With a minimum capacity of 8 guests and a maximum capacity of 12, it is perfect for a tropical getaway or a seaside celebration.', 1, 4, 1, 'APPROVED', true, 'ROOM', 'ROOM', 'Üllői út 12', ' Budapest', 'Hungary', '1091', false),
    ('Urban Loft Living', 'Embrace city life in our Urban Loft Living space. Located in the heart of downtown, this modern loft offers stylish design, convenience, and easy access to the vibrant urban scene. With a minimum capacity of 2 guests and a maximum capacity of 4, it is perfect for urban explorers or a chic city retreat.', 2, 4, 2, 'APPROVED', true, 'ROOM', 'PERSON', 'Øster Voldgade 1', 'Copenhagen', 'Denmark', '1350', false),
    ('Majestic Mountain Manor', 'Experience grandeur at our Majestic Mountain Manor. Perched high in the mountains, this elegant manor offers opulent interiors, breathtaking views, and a luxurious escape. With a minimum capacity of 10 guests and a maximum capacity of 15, it is an ideal choice for special occasions or an extravagant mountain retreat.', 1, 5, 3, 'APPROVED', true, 'APARTMENT', 'ROOM', 'Øster Voldgade 2', 'Copenhagen', 'Denmark', '1350', false),
    ('Riverside Haven Cottage', 'Escape to a Riverside Haven in our charming cottage by the river. Surrounded by nature, this cozy retreat offers tranquility and a connection to the outdoors. With a minimum capacity of 4 guests and a maximum capacity of 6, it is perfect for a family getaway or a peaceful retreat with friends.', 3, 6, 2, 'APPROVED', true, 'APARTMENT', 'ROOM', 'Øster Voldgade 3', 'Copenhagen', 'Denmark', '1350', false),
    ('Artisanal Village House', 'Discover the charm of village life in our Artisanal Village House. This rustic yet stylish house offers a unique blend of tradition and comfort. With a minimum capacity of 6 guests and a maximum capacity of 8, it is perfect for those seeking an authentic village experience.', 5, 6, 1, 'APPROVED', true, 'HOTEL', 'ROOM', 'Øster Voldgade 13', 'Copenhagen', 'Denmark', '1350', false),
    ('Sunset Cliff Retreat', 'Unwind at our Sunset Cliff Retreat, a hidden gem perched on the cliffs. This serene retreat offers panoramic views of the ocean and a tranquil atmosphere. With a minimum capacity of 6 guests and a maximum capacity of 10, it is perfect for family vacations or a peaceful escape with friends.', 2, 4, 2, 'APPROVED', true, 'HOTEL', 'PERSON', 'Dag Hammarskjölds torg 5', 'Malmö', 'Sweden', '211 18', false),
    ('Rustic Mountain Cabin', 'Escape to nature in our Rustic Mountain Cabin. Tucked away in the hills, this charming cabin provides a cozy retreat surrounded by the beauty of the mountains. With a minimum capacity of 2 guests and a maximum capacity of 4, it is perfect for a romantic getaway or a peaceful mountain escape.', 2, 4, 3, 'APPROVED', true, 'APARTMENT', 'PERSON', 'Dag Hammarskjölds torg 6', 'Malmö', 'Sweden', '211 18', false),
    ('Modern City Apartment', 'Immerse yourself in city living at our Modern City Apartment. Centrally located, this contemporary space offers convenience and comfort. With a minimum capacity of 1 guest and a maximum capacity of 2, it is perfect for solo travelers or a stylish city retreat.', 1, 2, 2, 'APPROVED', true, 'APARTMENT', 'ROOM', 'Dag Hammarskjölds torg 7', 'Malmö', 'Sweden', '211 18', false),
    ('Coastal Cottage Getaway', 'Discover charm by the sea in our Coastal Cottage Getaway. This idyllic cottage offers a peaceful retreat with ocean breezes and sandy shores. With a minimum capacity of 4 guests and a maximum capacity of 6, it is perfect for a family beach vacation or a romantic coastal escape.', 4, 5, 1, 'CREATED', true, 'HOTEL', 'PERSON', 'Dag Hammarskjölds torg 8', 'Malmö', 'Sweden', '211 18', false),
    ('Luxury Riverside Villa', 'Indulge in luxury at our Riverside Villa. This exquisite villa offers a private oasis by the river with upscale amenities and serene surroundings. With a minimum capacity of 8 guests and a maximum capacity of 12, it is perfect for special occasions or a lavish riverside retreat.', 1, 3, 1, 'APPROVED', true, 'HOTEL', 'ROOM', 'Dag Hammarskjölds torg 9', 'Malmö', 'Sweden', '211 18', false);


INSERT INTO pricelist_items (start_date, end_date, price)
VALUES
    ('2024-03-01', '2024-03-20', 10.99), ('2024-03-25', '2024-03-30', 28.99), ('2024-03-01', '2024-03-10', 39.99), ('2024-03-12', '2024-03-20', 31.99),
    ('2024-03-05', '2024-03-07', 19.99), ('2024-03-10', '2024-03-13', 26.99), ('2024-03-05', '2024-03-12', 31.99), ('2024-03-17', '2024-03-20', 12.99),
    ('2024-03-01', '2024-03-03', 19.99), ('2024-03-06', '2024-03-10', 28.99), ('2024-03-02', '2024-03-05', 37.99), ('2024-03-12', '2024-03-22', 32.99),
    ('2024-03-02', '2024-03-09', 11.99), ('2024-03-11', '2024-03-20', 22.99), ('2024-03-04', '2024-03-08', 34.99), ('2024-03-14', '2024-03-27', 37.99),
    ('2024-03-05', '2024-03-07', 18.99), ('2024-03-10', '2024-03-21', 27.99), ('2024-03-01', '2024-03-09', 32.99), ('2024-03-11', '2024-03-24', 39.99),
    ('2024-03-02', '2024-03-08', 13.99), ('2024-03-12', '2024-03-22', 28.99), ('2024-03-02', '2024-03-05', 33.99), ('2024-03-09', '2024-03-23', 33.99),
    ('2024-03-07', '2024-03-10', 18.99), ('2024-03-13', '2024-03-25', 26.99), ('2024-03-01', '2024-03-03', 33.99), ('2024-03-07', '2024-03-21', 38.99),
    ('2024-03-06', '2024-03-09', 12.99), ('2024-03-11', '2024-03-27', 28.99), ('2024-03-07', '2024-03-10', 35.99), ('2024-03-14', '2024-03-22', 30.99),
    ('2024-03-04', '2024-03-10', 14.99), ('2024-03-13', '2024-03-21', 22.99), ('2024-03-03', '2024-03-09', 30.99), ('2024-03-13', '2024-03-15', 38.99),
    ('2024-03-06', '2024-03-08', 16.99), ('2024-03-11', '2024-03-19', 27.99), ('2024-03-02', '2024-03-09', 32.99), ('2024-03-12', '2024-03-18', 39.99),
    ('2024-03-01', '2024-03-09', 13.99), ('2024-03-12', '2024-03-17', 22.99), ('2024-03-02', '2024-03-07', 36.99), ('2024-03-11', '2024-03-20', 33.99),
    ('2024-03-04', '2024-03-10', 14.99), ('2024-03-13', '2024-03-14', 20.99), ('2024-03-01', '2024-03-06', 39.99), ('2024-03-10', '2024-03-19', 32.99),
    ('2024-03-03', '2024-03-09', 16.99), ('2024-03-12', '2024-03-15', 23.99), ('2024-03-03', '2024-03-09', 37.99), ('2024-03-14', '2024-03-17', 38.99),
    ('2024-03-01', '2024-03-05', 13.99), ('2024-03-10', '2024-03-21', 24.99), ('2024-03-04', '2024-03-09', 30.99), ('2024-03-11', '2024-03-15', 33.99),
    ('2024-03-04', '2024-03-10', 12.99), ('2024-03-13', '2024-03-14', 20.99), ('2024-03-01', '2024-03-06', 35.99), ('2024-03-10', '2024-03-19', 32.99),
    ('2024-03-03', '2024-03-09', 16.99), ('2024-03-12', '2024-03-15', 23.99), ('2024-03-03', '2024-03-09', 32.99), ('2024-03-14', '2024-03-17', 36.99),
    ('2024-03-01', '2024-03-05', 18.99), ('2024-03-10', '2024-03-21', 25.99), ('2024-03-04', '2024-03-09', 32.99), ('2024-03-11', '2024-03-15', 39.99),
    ('2024-03-01', '2024-03-05', 11.99), ('2024-03-10', '2024-03-21', 23.99), ('2024-03-04', '2024-03-09', 39.99), ('2024-03-11', '2024-03-15', 30.99);

INSERT INTO accommodations_price_list (accommodation_id, price_list_id)
VALUES
    (1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (3, 6), (4, 7), (4, 8),
    (5, 9), (5, 10), (6, 11), (6, 12), (7, 13), (7, 14), (8, 15), (8, 16),
    (9, 17), (9, 18), (10, 19), (10, 20), (11, 21), (11, 22), (12, 23), (12, 24),
    (13, 25), (13, 26), (14, 27), (14, 28), (15, 29), (15, 30), (16, 31), (16, 32),
    (17, 33), (17, 34), (18, 35), (18, 36), (19, 37), (19, 38), (20, 39), (20, 40),
    (21, 41), (21, 42), (22, 43), (22, 44), (23, 45), (23, 46), (24, 47), (24, 48),
    (25, 49), (25, 50), (26, 51), (26, 52), (27, 53), (27, 54), (28, 55), (28, 56),
    (29, 57), (29, 58), (30, 59), (30, 60), (31, 61), (31, 62), (32, 63), (32, 64),
    (33, 65), (33, 66), (34, 67), (34, 68), (35, 69), (35, 70), (36, 71), (36, 72);


INSERT INTO availability (start_date, end_date)
VALUES
    ('2024-03-01', '2024-03-20'), ('2024-03-25', '2024-03-30'), ('2024-03-01', '2024-03-10'), ('2024-03-12', '2024-03-20'),
    ('2024-03-05', '2024-03-07'), ('2024-03-10', '2024-03-13'), ('2024-03-05', '2024-03-12'), ('2024-03-17', '2024-03-20'),
    ('2024-03-01', '2024-03-03'), ('2024-03-06', '2024-03-10'), ('2024-03-02', '2024-03-05'), ('2024-03-12', '2024-03-22'),
    ('2024-03-02', '2024-03-09'), ('2024-03-11', '2024-03-20'), ('2024-03-04', '2024-03-08'), ('2024-03-14', '2024-03-27'),
    ('2024-03-05', '2024-03-07'), ('2024-03-10', '2024-03-21'), ('2024-03-01', '2024-03-09'), ('2024-03-11', '2024-03-24'),
    ('2024-03-02', '2024-03-08'), ('2024-03-12', '2024-03-22'), ('2024-03-02', '2024-03-05'), ('2024-03-09', '2024-03-23'),
    ('2024-03-07', '2024-03-10'), ('2024-03-13', '2024-03-25'), ('2024-03-01', '2024-03-03'), ('2024-03-07', '2024-03-21'),
    ('2024-03-06', '2024-03-09'), ('2024-03-11', '2024-03-27'), ('2024-03-07', '2024-03-10'), ('2024-03-14', '2024-03-22'),
    ('2024-03-04', '2024-03-10'), ('2024-03-13', '2024-03-21'), ('2024-03-03', '2024-03-09'), ('2024-03-13', '2024-03-15'),
    ('2024-03-06', '2024-03-08'), ('2024-03-11', '2024-03-19'), ('2024-03-02', '2024-03-09'), ('2024-03-12', '2024-03-18'),
    ('2024-03-01', '2024-03-09'), ('2024-03-12', '2024-03-17'), ('2024-03-02', '2024-03-07'), ('2024-03-11', '2024-03-20'),
    ('2024-03-04', '2024-03-10'), ('2024-03-13', '2024-03-14'), ('2024-03-01', '2024-03-06'), ('2024-03-10', '2024-03-19'),
    ('2024-03-03', '2024-03-09'), ('2024-03-12', '2024-03-15'), ('2024-03-03', '2024-03-09'), ('2024-03-14', '2024-03-17'),
    ('2024-03-01', '2024-03-05'), ('2024-03-10', '2024-03-21'), ('2024-03-04', '2024-03-09'), ('2024-03-11', '2024-03-15'),
    ('2024-03-04', '2024-03-10'), ('2024-03-13', '2024-03-14'), ('2024-03-01', '2024-03-06'), ('2024-03-10', '2024-03-19'),
    ('2024-03-03', '2024-03-09'), ('2024-03-12', '2024-03-15'), ('2024-03-03', '2024-03-09'), ('2024-03-14', '2024-03-17'),
    ('2024-03-01', '2024-03-05'), ('2024-03-10', '2024-03-21'), ('2024-03-04', '2024-03-09'), ('2024-03-11', '2024-03-15'),
    ('2024-03-01', '2024-03-05'), ('2024-03-10', '2024-03-21'), ('2024-03-04', '2024-03-09'), ('2024-03-11', '2024-03-15');

INSERT INTO accommodations_availability (accommodation_id, availability_id)
VALUES
    (1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (3, 6), (4, 7), (4, 8),
    (5, 9), (5, 10), (6, 11), (6, 12), (7, 13), (7, 14), (8, 15), (8, 16),
    (9, 17), (9, 18), (10, 19), (10, 20), (11, 21), (11, 22), (12, 23), (12, 24),
    (13, 25), (13, 26), (14, 27), (14, 28), (15, 29), (15, 30), (16, 31), (16, 32),
    (17, 33), (17, 34), (18, 35), (18, 36), (19, 37), (19, 38), (20, 39), (20, 40),
    (21, 41), (21, 42), (22, 43), (22, 44), (23, 45), (23, 46), (24, 47), (24, 48),
    (25, 49), (25, 50), (26, 51), (26, 52), (27, 53), (27, 54), (28, 55), (28, 56),
    (29, 57), (29, 58), (30, 59), (30, 60), (31, 61), (31, 62), (32, 63), (32, 64),
    (33, 65), (33, 66), (34, 67), (34, 68), (35, 69), (35, 70), (36, 71), (36, 72);

INSERT INTO reservations (created, start, guest_number, end, status, accommodation_id, guest_id, price)
VALUES
    ('2024-10-01', '2027-12-05', 2, '2027-12-10', 'ACCEPTED', 1, 1, 100),
    ('2034-10-01', '2037-12-05', 2, '2037-12-10', 'ACCEPTED', 1, 1, 100);

INSERT INTO pricelist_items (start_date, end_date, price)
VALUES
    ('2027-12-04', '2027-12-10', 20);

INSERT INTO accommodations_price_list (accommodation_id, price_list_id)
VALUES
    (1, 73);

INSERT INTO availability (start_date, end_date)
VALUES
    ('2027-12-04', '2027-12-10');

INSERT INTO accommodations_availability (accommodation_id, availability_id)
VALUES
    (1, 73);
