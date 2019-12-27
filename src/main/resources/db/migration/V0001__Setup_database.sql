--create account table
CREATE TABLE account(
   user_id serial PRIMARY KEY,
   username VARCHAR (50) UNIQUE NOT NULL,
   email VARCHAR (355) UNIQUE NOT NULL
);

--insert example accounts
INSERT INTO account (username, email)
VALUES ('ADAM', 'adam@o2.pl');
INSERT INTO account (username, email)
VALUES ('BOT', 'BOT@o2.pl');
INSERT INTO account (username, email)
VALUES ('DOM', 'DOM@o2.pl');
