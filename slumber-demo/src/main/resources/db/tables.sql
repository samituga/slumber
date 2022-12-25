CREATE TABLE language
(
    id          INTEGER NOT NULL PRIMARY KEY,
    cd          CHAR(2) NOT NULL,
    description VARCHAR(50)
);

CREATE TABLE author
(
    id            INTEGER     NOT NULL PRIMARY KEY,
    first_name    VARCHAR(50),
    last_name     VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    year_of_birth INTEGER,
    distinguished INTEGER
);

CREATE TABLE book
(
    id           INTEGER     NOT NULL PRIMARY KEY,
    author_id    INTEGER     NOT NULL,
    title        VARCHAR(40) NOT NULL,
    published_in INTEGER     NOT NULL,
    language_id  INTEGER     NOT NULL,

    CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES author (id),
    CONSTRAINT fk_book_language FOREIGN KEY (language_id) REFERENCES language (id)
);

CREATE TABLE book_store
(
    name VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE book_to_book_store
(
    name    VARCHAR(40) NOT NULL,
    book_id INTEGER     NOT NULL,
    stock   INTEGER,

    PRIMARY KEY (name, book_id),
    CONSTRAINT fk_b2bs_book_store FOREIGN KEY (name) REFERENCES book_store (name) ON DELETE CASCADE,
    CONSTRAINT fk_b2bs_book FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE
);


INSERT INTO language (id, cd, description)
VALUES (1, 'en', 'English');
INSERT INTO language (id, cd, description)
VALUES (2, 'de', 'Deutsch');
INSERT INTO language (id, cd, description)
VALUES (3, 'fr', 'Français');
INSERT INTO language (id, cd, description)
VALUES (4, 'pt', 'Português');

INSERT INTO author (id, first_name, last_name, date_of_birth, year_of_birth)
VALUES (1, 'George', 'Orwell', DATE '1903-06-26', 1903);
INSERT INTO author (id, first_name, last_name, date_of_birth, year_of_birth)
VALUES (2, 'Paulo', 'Coelho', DATE '1947-08-24', 1947);

INSERT INTO book (id, author_id, title, published_in, language_id)
VALUES (1, 1, '1984', 1948, 1);
INSERT INTO book (id, author_id, title, published_in, language_id)
VALUES (2, 1, 'Animal Farm', 1945, 1);
INSERT INTO book (id, author_id, title, published_in, language_id)
VALUES (3, 2, 'O Alquimista', 1988, 4);
INSERT INTO book (id, author_id, title, published_in, language_id)
VALUES (4, 2, 'Brida', 1990, 2);

INSERT INTO book_store
VALUES ('Orell Füssli');
INSERT INTO book_store
VALUES ('Ex Libris');
INSERT INTO book_store
VALUES ('Buchhandlung im Volkshaus');

INSERT INTO book_to_book_store
VALUES ('Orell Füssli', 1, 10);
INSERT INTO book_to_book_store
VALUES ('Orell Füssli', 2, 10);
INSERT INTO book_to_book_store
VALUES ('Orell Füssli', 3, 10);
INSERT INTO book_to_book_store
VALUES ('Ex Libris', 1, 1);
INSERT INTO book_to_book_store
VALUES ('Ex Libris', 3, 2);
INSERT INTO book_to_book_store
VALUES ('Buchhandlung im Volkshaus', 3, 1);