CREATE TABLE transfer (
id BIGINT PRIMARY KEY,
date DATE NOT NULL,
amount DECIMAL,
payee BIGINT,
payer BIGINT,
FOREIGN KEY (payee) REFERENCES user(id),
FOREIGN KEY (payer) REFERENCES user(id)
);
