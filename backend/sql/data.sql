-- Insert data into the university table
INSERT INTO university (name)
VALUES ('University of Example'),
       ('Another University');

-- Insert data into the users table
INSERT INTO users (nickname, university_id, field_of_study)
VALUES ('admin_user', 1, 'Computer Science'),
       ('user1', 1, 'Physics'),
       ('user2', 1, 'Biology'),
       ('user3', 1, 'Mathematics'),
       ('user4', 1, 'Chemistry'),
       ('user5', 1, 'History'),
       ('user6', 1, 'Economics'),
       ('user7', 1, 'Psychology'),
       ('user8', 1, 'English'),
       ('user9', 1, 'Engineering'),
       ('user10', 1, 'Political Science');

-- Insert data into the roles table
INSERT INTO roles (name)
VALUES ('ADMIN'),
       ('USER');

-- Insert data into the user_security_info table
INSERT INTO user_security_info (user_id, email, password)
VALUES (1, 'admin@example.com', '$2a$12$wsFZqukbU9e4usv6YYE4OuBXHZbAs9ksmrZ9YJwq5uCI2c4GGrn3u'),
       (2, 'user1@example.com', '$2a$12$16Ai8pXO4ieWyQYw6iQWhuVin78flKB7RsI21p.vVNfvRB69vfHzu'),
       (3, 'user2@example.com', '$2a$12$16Ai8pXO4ieWyQYw6iQWhuVin78flKB7RsI21p.vVNfvRB69vfHzu'),
       (4, 'user3@example.com', '$2a$12$16Ai8pXO4ieWyQYw6iQWhuVin78flKB7RsI21p.vVNfvRB69vfHzu'),
       (5, 'user4@example.com', '$2a$12$16Ai8pXO4ieWyQYw6iQWhuVin78flKB7RsI21p.vVNfvRB69vfHzu'),
       (6, 'user5@example.com', '$2a$12$16Ai8pXO4ieWyQYw6iQWhuVin78flKB7RsI21p.vVNfvRB69vfHzu'),
       (7, 'user6@example.com', '$2a$12$afJ68QkG97cgMFUHMQE9P.xm9.Ec4JQ.JSJsCWzvI1WNg32DuCqMO'),
       (8, 'user7@example.com', '$2a$12$afJ68QkG97cgMFUHMQE9P.xm9.Ec4JQ.JSJsCWzvI1WNg32DuCqMO'),
       (9, 'user8@example.com', '$2a$12$afJ68QkG97cgMFUHMQE9P.xm9.Ec4JQ.JSJsCWzvI1WNg32DuCqMO'),
       (10, 'user9@example.com', '$2a$12$afJ68QkG97cgMFUHMQE9P.xm9.Ec4JQ.JSJsCWzvI1WNg32DuCqMO');

-- Insert data into the users_roles table
INSERT INTO security_users_roles (security_user_id, role_id)
VALUES (1, 1), -- admin_user is an ADMIN
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 2),
       (7, 2),
       (8, 2),
       (9, 2),
       (10, 2);

