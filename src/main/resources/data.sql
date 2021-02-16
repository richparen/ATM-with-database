DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id INT PRIMARY KEY,
                       first_name VARCHAR(250) NOT NULL,
                       last_name VARCHAR(250) NOT NULL
);

INSERT INTO users (id, first_name, last_name) VALUES
(1, 'Duman', 'Serikbay'),
(2, 'Karina', 'Suingaraeva');

DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts(
                         account_no BIGINT PRIMARY KEY,
                         pin INT,
                         account_type VARCHAR(10),
                         balance REAL,
                         user_id INT,
                         locked BIT NOT NULL DEFAULT 0,
                         incorrect_attempts INT DEFAULT 0,
                         foreign key (user_id) references users(id)
);

INSERT INTO accounts(account_no, pin, account_type, balance, user_id) VALUES
(26279, 0809, 'SAVINGS', 10000, 1);
INSERT INTO accounts(account_no, pin, account_type, balance, user_id) VALUES
(26280, 0920, 'CHECKING', 20000, 2);

DROP TABLE IF EXISTS transactions;

CREATE TABLE transactions(
                             transaction_id INT AUTO_INCREMENT PRIMARY KEY,
                             type VARCHAR(20),
                             transaction_time TIMESTAMP,
                             account_no BIGINT,
                             amount REAL,
                             foreign key (account_no) references accounts(account_no)
);