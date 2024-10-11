-- CATEGORIES
INSERT INTO maintenance_service.categories (id, description, is_active, parent_category)
VALUES (1, 'Tecnología', true, NULL);
INSERT INTO maintenance_service.categories (id, description, is_active, parent_category)
VALUES (2, 'Audio', true, 1);
INSERT INTO maintenance_service.categories (id, description, is_active, parent_category)
VALUES (3, 'Parlantes Bluetooth', true, 2);
INSERT INTO maintenance_service.categories (id, description, is_active, parent_category)
VALUES (4, 'Equipos de Sonido', true, 2);
INSERT INTO maintenance_service.categories (id, description, is_active, parent_category)
VALUES (5, 'Audífonos', true, 2);
INSERT INTO maintenance_service.categories (id, description, is_active, parent_category)
VALUES (6, 'Electrohogar', true, NULL);
INSERT INTO maintenance_service.categories (id, description, is_active, parent_category)
VALUES (7, 'Refrigeradoras', true, 6);
INSERT INTO maintenance_service.categories (id, description, is_active, parent_category)
VALUES (8, 'Congeladoras', true, 7);
INSERT INTO maintenance_service.categories (id, description, is_active, parent_category)
VALUES (9, 'Frigobares', true, 7);
-- BRANDS
INSERT INTO maintenance_service.brands (id, description, is_active)
VALUES (1, 'JBL', true);
INSERT INTO maintenance_service.brands (id, description, is_active)
VALUES (2, 'HARMAN KARDON', true);
-- PRODUCTS
INSERT INTO maintenance_service.products (id, bar_code, description, is_active, is_recommended, name, price, stock, brand_id, category_id)
VALUES (1, '20019764', 'Siente la nueva experiencia de audio inmersivo de JBL Profesional con sus parlantes
portatiles, llevalos a cualquier lado, su increíle peso hace sentir que no lleves nada.', true, true,
        'Parlante JBL FLIP 6 BT', 429.3, 100, 1, 3);
-- DETAILS OF PRODUCTS
INSERT INTO maintenance_service.product_images (id, image_url, product_id)
VALUES (4, 'https://drive.google.com/file/d/10XAiINe7mBaBlDWdjSH4uWChQ0gypq-7/view', 1);
INSERT INTO maintenance_service.product_images (id, image_url, product_id)
VALUES (6, 'https://drive.google.com/file/d/1tnvyLFap7JxYCO1EBG1PmmrKQAxPLSrn/view', 1);
-- IMAGES OF PRODUCTS
INSERT INTO maintenance_service.product_details (id, description, name, product_id)
VALUES (1, 'Nuevo', 'Condicion del producto', 1);
INSERT INTO maintenance_service.product_details (id, description, name, product_id)
VALUES (1, 'No', 'Conexión WiFi', 1);
INSERT INTO maintenance_service.product_details (id, description, name, product_id)
VALUES (1, 'JBLFLIP6BLKAM', 'Modelo', 1);
-- CUSTOMERS
INSERT INTO maintenance_service.customers (id, birthdate, gender, last_name, name, email, is_active, modification_date, phone_number_one,
                                           phone_number_three, phone_number_two, registration_date, user_modification, user_registration,
                                           address_id)
VALUES (1, '1999-12-19 15:30:20.000000', 'M', 'Fuentes Medina', 'Luigui Alexander',
        'alexanderfuentes199912@gmail.com', false, '2024-10-08 16:33:24.460321', '917967148',
        '', '074287079', '2024-10-08 16:25:17.885464', 'UserModification',
        'UserRegistration', 1);
-- EMPLOYEES
INSERT INTO maintenance_service.employees (id, birthdate, gender, last_name, name, email, is_active, modification_date, phone_number,
                                           registration_date, user_modification, user_registration, address_id)
VALUES (1, '1999-06-15 16:20:56.000000', 'M', 'Chúman', 'Dagner Anibal', 'dagnersmooth@example.com',
        true, '2024-10-09 22:55:12.469607', '+51917967148', '2024-10-09 22:51:09.707969',
        'UserModification', 'UserRegistration', 3);
-- ADDRESS
INSERT INTO maintenance_service.address (id, address_name, department, district, place_reference, postal_code, province, residence_number,
                                         type)
VALUES (1, 'Prolongación Sucre', 'Lambayeque', 'Ferreñafe',
        'Cerca de la avenida takahasi nuñez', '14311', 'Ferreñafe', '903', 'Casa');
INSERT INTO maintenance_service.address (id, address_name, department, district, place_reference, postal_code, province, residence_number,
                                         type)
VALUES (3, 'San Roque U.V Los Olivos', 'Lambayeque', 'Ferreñafe',
        'Por el parque del algodonal', '14311', 'Ferreñafe', '456', 'Casa');