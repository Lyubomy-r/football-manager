CREATE TABLE IF NOT EXISTS teams (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       balance DECIMAL(19, 2) NOT NULL,
                       founded_year INT NOT NULL,
                       city VARCHAR(255),
                       transfer_commission INT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS players (
                         id BIGSERIAL PRIMARY KEY,
                         first_name VARCHAR(255) NOT NULL,
                         last_name VARCHAR(255) NOT NULL,
                         date_of_birth DATE NOT NULL,
                         position VARCHAR(255) NOT NULL,
                         team_id BIGINT REFERENCES teams(id),
                         experience INT NOT NULL,
                         age INT NOT NULL,
                         nationality VARCHAR(255),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transfers (
                           id BIGSERIAL PRIMARY KEY,
                           transfer_price DECIMAL(19, 2) NOT NULL,
                           total_price DECIMAL(19, 2) NOT NULL,
                           player_id BIGINT NOT NULL,
                           from_team_id BIGINT NOT NULL,
                           to_team_id BIGINT NOT NULL,
                           transfer_date TIMESTAMP NOT NULL,
                           CONSTRAINT fk_transfer_player FOREIGN KEY (player_id) REFERENCES players(id),
                           CONSTRAINT fk_transfer_from_team FOREIGN KEY (from_team_id) REFERENCES teams(id),
                           CONSTRAINT fk_transfer_to_team FOREIGN KEY (to_team_id) REFERENCES teams(id)
);