CREATE DATABASE centrale
--c centrale

CREATE TYPE player_position AS ENUM (
     'STRIKER',
     'MIDFIELDER',
     'DEFENSE',
   'GOAL_KEEPER'
 );

CREATE TYPE championship AS ENUM (
    'PREMIER_LEAGUE',
    'LA_LIGA',
    'BUNDESLIGA',
    'SERIA',
    'LIGUE_1'
);



CREATE TABLE club (
     id VARCHAR PRIMARY KEY,
     name VARCHAR NOT NULL,
     acronym VARCHAR(10),
     year_creation INT,
     stadium VARCHAR,
     coach_id VARCHAR(20),
     championship championship NOT NULL
 );

 CREATE TABLE player (
     id VARCHAR PRIMARY KEY,
     name VARCHAR NOT NULL,
     number INT,
     age INT,
     position player_position,
     nationality VARCHAR,
     club_id VARCHAR REFERENCES club(id),
     championship VARCHAR NOT NULL
 );


CREATE TABLE player_statistics (
                                   id SERIAL PRIMARY KEY,
                                   player_id TEXT REFERENCES player(id),
                                   season_year INT,
                                   scored_goals INT NOT NULL,
                                   playing_time_value DOUBLE PRECISION NOT NULL,
                                   playing_time_unit TEXT NOT NULL
);


CREATE TABLE club_statistics (
                                 club_id VARCHAR PRIMARY KEY REFERENCES club(id),
                                 scored_goals INT NOT NULL,
                                 conceded_goals INT NOT NULL,
                                 difference_goals INT NOT NULL,
                                 clean_sheet_number INT NOT NULL,
                                 ranking_points INT NOT NULL
);

CREATE TABLE championship_rankings (
                                       championship VARCHAR PRIMARY KEY,
                                       difference_goals_median DOUBLE PRECISION,
                                       rank INT
);


CREATE TABLE IF NOT EXISTS coaches (
                                       id TEXT PRIMARY KEY,
                                       name TEXT NOT NULL,
                                       nationality TEXT NOT NULL
);

ALTER TABLE club ADD COLUMN coach_id VARCHAR;

ALTER TABLE club
    ADD CONSTRAINT fk_coach
        FOREIGN KEY (coach_id)
            REFERENCES coaches(id)
            ON UPDATE CASCADE
            ON DELETE SET NULL;
