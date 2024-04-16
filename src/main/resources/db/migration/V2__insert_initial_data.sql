INSERT INTO roles (role_name)
VALUES ('ADMIN');

INSERT INTO roles (role_name)
VALUES ('USER');

INSERT INTO users (active, first_name, last_name, email, phone_number, password)
VALUES (true, 'Admin', 'Admin', 'admin@test.com', '0661328477', '$2a$10$XG6xBQArxEeBZMNwkw.B1OwQezc1yqE1VzE2oU1JFsI.42czoQH02');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);