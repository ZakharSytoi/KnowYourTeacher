CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE university
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE users
(
    id             SERIAL PRIMARY KEY,
    nickname       VARCHAR(255) NOT NULL,
    university_id  INTEGER REFERENCES university (id),
    field_of_study VARCHAR(255) NOT NULL
);


CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR
);


CREATE TABLE user_security_info
(
    user_id  INTEGER PRIMARY KEY NOT NULL UNIQUE REFERENCES users (id) ON DELETE CASCADE,
    email    VARCHAR(255)        NOT NULL UNIQUE,
    password VARCHAR             NOT NULL
);


CREATE TABLE security_users_roles
(
    security_user_id INTEGER REFERENCES user_security_info (user_id),
    role_id          INTEGER REFERENCES roles (id)
);


CREATE TABLE teacher
(
    id                 SERIAL PRIMARY KEY,
    name               VARCHAR(255),
    surname            VARCHAR(255)                  NOT NULL,
    avg_score          DECIMAL(2, 1),
    university_id      INTEGER REFERENCES university (id),
    created_by         INTEGER REFERENCES users (id) NOT NULL,
    created_date       DATE                          NOT NULL,
    modified_date      DATE                          NOT NULL,
    profile_image_link VARCHAR(255)                  NOT NULL
);


CREATE TABLE review
(
    id           SERIAL PRIMARY KEY,
    score        SMALLINT                        NOT NULL,
    teacher_id   INTEGER REFERENCES teacher (id) NOT NULL,
    subject_name VARCHAR(255)                    NOT NULL,
    user_id      INTEGER REFERENCES users (id)   NOT NULL,
    review_text  TEXT                            NOT NULL,
    created_date TIMESTAMP                       NOT NULL,
    UNIQUE (teacher_id, user_id)

);


CREATE TABLE likes
(
    review_id BIGINT REFERENCES review (id) ON DELETE CASCADE,
    user_id   BIGINT REFERENCES users (id) ON DELETE CASCADE,
    PRIMARY KEY (review_id, user_id)
);

CREATE TABLE dislikes
(
    review_id BIGINT REFERENCES review (id) ON DELETE CASCADE,
    user_id   BIGINT REFERENCES users (id) ON DELETE CASCADE,
    PRIMARY KEY (review_id, user_id)
);


CREATE
OR
REPLACE
FUNCTION update_average_teacher_score()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE teacher
    SET avg_score = (SELECT AVG(score)
                     FROM review
                     WHERE review.teacher_id = NEW.teacher_id
                     GROUP BY teacher_id)
    WHERE teacher.id = NEW.teacher_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER update_average_score_trigger
    AFTER INSERT
    ON review
    FOR EACH ROW
    EXECUTE FUNCTION update_average_teacher_score();

CREATE VIEW review_with_likes_dislikes_count AS
SELECT r.id                       AS review_id,
       r.score                    AS score,
       r.teacher_id               AS teacher_id,
       r.subject_name             AS subject_name,
       r.user_id                  AS user_id,
       r.review_text              AS review_text,
       r.created_date             AS created_date,
       u.nickname                 AS nickname,
       uni.name                   AS university_name,
       COALESCE(like_count, 0)    AS like_count,
       COALESCE(dislike_count, 0) AS dislike_count
FROM review r
         LEFT JOIN
     (SELECT review_id, COUNT(user_id) AS like_count
      FROM likes
      GROUP BY review_id) l ON r.id = l.review_id
         LEFT JOIN
     (SELECT review_id, COUNT(user_id) AS dislike_count
      FROM dislikes
      GROUP BY review_id) d ON r.id = d.review_id
         JOIN users u ON r.user_id = u.id
         JOIN university uni ON u.university_id = uni.id
ORDER BY like_count DESC;

CREATE VIEW top_teachers_with_most_popular_review_text AS
WITH RankedReviews AS (SELECT rw.*,
                              ROW_NUMBER() OVER (PARTITION BY rw.teacher_id ORDER BY rw.like_count DESC) AS rank
                       FROM review_with_likes_dislikes_count rw)
SELECT teacher_id,
       teacher.name         as teacher_name,
       teacher.surname      as teacher_surname,
       u.name               as university_name,
       teacher.avg_score,
       review_text          as most_popular_review_text,
       teacher.profile_image_link as profile_image_link
FROM RankedReviews
         JOIN teacher ON teacher_id = teacher.id
         JOIN university u on teacher.university_id = u.id
WHERE rank = 1
ORDER BY (teacher.avg_score * (SELECT count(*) FROM review WHERE review.teacher_id = RankedReviews.teacher_id)) DESC;