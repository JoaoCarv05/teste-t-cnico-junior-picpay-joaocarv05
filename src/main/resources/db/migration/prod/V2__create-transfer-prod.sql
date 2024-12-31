CREATE TABLE transfer (
    id BIGSERIAL PRIMARY KEY,
    date DATE NOT NULL,
    amount DECIMAL,
    payee BIGINT,
    payer BIGINT,
    FOREIGN KEY (payee) REFERENCES users(id),
    FOREIGN KEY (payer) REFERENCES users(id)
);