INSERT INTO users(email, user_name, password, admin) VALUES
  ('admin@fib.upc.edu', 'admin', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1),
  ('user@fib.upc.edu', 'user', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 0);
-- Passwords are 123456 (hashed)

INSERT INTO authors(author_name) VALUES
  ('Zofia Bivens'),
  ('Earnestine Rine'),
  ('Dwain Spratling'),
  ('Mardell Tucci'),
  ('Sima Hannon'),
  ('Rodrick Weimer'),
  ('Teisha Suman'),
  ('Gaylord Lisowski'),
  ('Merrill Wolanski');

INSERT INTO documents(title, author_name, user_owner, term_frequency, content) VALUES
  ('presharing', 'Rodrick Weimer', 'admin', '[]', '1'),
  ('cladoceran', 'Dwain Spratling', 'admin', '[]', '2'),
  ('fabulously', 'Mardell Tucci', 'admin', '[]', '3'),
  ('unlessened', 'Gaylord Lisowski', 'user', '[]', '4'),
  ('impanelled', 'Rodrick Weimer', 'user', '[]', '5'),
  ('unsluggish', 'Sima Hannon', 'admin', '[]', '6'),
  ('rightabout', 'Zofia Bivens', 'admin', '[]', '7'),
  ('hardfisted', 'Dwain Spratling', 'user', '[]', '8'),
  ('unprowling', 'Earnestine Rine', 'admin', '[]', '9'),
  ('uncaptured', 'Rodrick Weimer', 'admin', '[]', 'A');
