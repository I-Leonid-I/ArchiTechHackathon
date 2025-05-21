CREATE TABLE IF NOT EXISTS clients (
                                       client_id SERIAL PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    bank_id INT NOT NULL
    );


CREATE TABLE IF NOT EXISTS wallets (
                                       wallet_id SERIAL PRIMARY KEY,
                                       client_id INT NOT NULL,
                                       balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (client_id) REFERENCES clients(client_id)
    );


CREATE TABLE IF NOT EXISTS operations (
                                          id SERIAL PRIMARY KEY,
                                          time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                          type operation_type NOT NULL,
                                          from_wallet_id INT NOT NULL,
                                          to_wallet_id INT NOT NULL,
                                          amount DECIMAL(10, 2) NOT NULL,
    status operation_status NOT NULL DEFAULT 'processing',
    FOREIGN KEY (from_wallet_id) REFERENCES wallets(wallet_id),
    FOREIGN KEY (to_wallet_id) REFERENCES wallets(wallet_id)
    );
