INSERT INTO roles (role_name)
VALUES ('ADMIN');

INSERT INTO roles (role_name)
VALUES ('USER');

INSERT INTO users (active, first_name, last_name, email, phone_number, password)
VALUES (true, 'Admin', 'Admin', 'admin@gmail.com', '0661328477', '$2y$10$fg5G4x.NnXIoM7YwJhmhruo31Ag/ZLHXy6MXLlgQsNywiB.yhGGm6');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);