CREATE TABLE person (
                        id SERIAL PRIMARY KEY,
                        full_name VARCHAR(255),
                        birth_date DATE,
                        document VARCHAR(255),
                        email VARCHAR(255),
                        telephone VARCHAR(255),
                        active BOOLEAN,
                        manage_accounts BOOLEAN
);

CREATE TABLE account (
                         id SERIAL PRIMARY KEY,
                         balance NUMERIC(19, 2),
                         active BOOLEAN,
                         person_id BIGINT UNIQUE,
                         CONSTRAINT fk_account_person FOREIGN KEY (person_id) REFERENCES person(id)
);
