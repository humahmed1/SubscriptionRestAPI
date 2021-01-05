DROP TABLE IF EXISTS subscription;

CREATE TABLE subscription (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    price FLOAT NOT NULL,
    date INT NOT NULL,
    account VARCHAR(255)
);