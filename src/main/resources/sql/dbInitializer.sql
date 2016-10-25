CREATE TABLE IF NOT EXISTS users (
  email VARCHAR PRIMARY KEY,
  user_name VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  admin INTEGER NOT NULL);

CREATE TABLE IF NOT EXISTS authors (
  author_name VARCHAR PRIMARY KEY);

CREATE TABLE IF NOT EXISTS documents (
  title VARCHAR,
  author_name VARCHAR NOT NULL,
  user_owner VARCHAR NOT NULL,
  content VARCHAR NOT NULL,
  PRIMARY KEY(title, author_name),
  FOREIGN KEY(author_name) REFERENCES authors(author_name),
  FOREIGN KEY(user_owner) REFERENCES users(email));