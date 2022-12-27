-- drop table if exists Address cascade;

CREATE TABLE IF NOT EXISTS currency
(
    id       BIGSERIAL PRIMARY KEY,
    symbol   VARCHAR(20),
    rate     NUMERIC NOT NULL,
    date_cur DATE
);

CREATE TABLE IF NOT EXISTS accounts
(
    id       BIGSERIAL PRIMARY KEY,
    owner_id BIGSERIAL   NOT NULL,
    number   VARCHAR(15) NOT NULL,
    balance  NUMERIC     NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions
(
    id             BIGSERIAL PRIMARY KEY,
    account_id     BIGSERIAL,
    type_operation VARCHAR(10),
    data_operation DATE,
    limit_exceeded BOOLEAN,
    limit_id       BIGSERIAL,
    currency       VARCHAR(3),
    amount         NUMERIC
);

CREATE TABLE IF NOT EXISTS limits
(
    id         BIGSERIAL PRIMARY KEY,
    account_id BIGSERIAL NOT NULL,
    limit_op   NUMERIC   NOT NULL,
    date_set   DATE,
    type_limit VARCHAR(20)
);


--
--         -- drop table if exists Sports cascade;
--         CREATE TABLE IF NOT EXISTS Sports
--         (
--         id_sport BIGSERIAL PRIMARY KEY ,
--         name varchar(50) not null
--         );
--
--         -- drop table if exists Systems cascade;
--         CREATE TABLE IF NOT EXISTS Systems
--         (
--         id_system BIGSERIAL PRIMARY KEY ,
--         name varchar(50) not null,
--         rules varchar(1200)
--         );
--
--         -- drop table if exists Leagues cascade;
--         CREATE TABLE IF NOT EXISTS Leagues
--         (
--         id_league BIGSERIAL PRIMARY KEY ,
--         sport bigserial not null,
--         system bigserial not null ,
--         name varchar(50) not null ,
--         count_teams bigserial,
--         constraint fk_sport
--         foreign key (sport)
--         references Sports(id_sport),
--         constraint fk_system
--         foreign key (system)
--         references Systems(id_system)
--         );
--
--         -- drop table if exists Teams cascade;
--         CREATE TABLE IF NOT EXISTS Teams
--         (
--         id_team BIGSERIAL PRIMARY KEY ,
--         league bigserial,
--         lead bigserial NOT NULL ,
--         name varchar(50) not null ,
--         logo varchar(40),
--         sport bigserial,
--         constraint fk_league
--         foreign key (league)
--         references Leagues(id_league),
--         constraint fk_sport
--         foreign key (sport)
--         references Sports(id_sport)
--         );
--
--         -- drop table if exists Users cascade;
--         CREATE TABLE IF NOT EXISTS Users
--         (
--         id_user bigserial primary key,
--         firstname varchar(50) not null,
--         lastname VARCHAR(50) NOT NULL ,
--         email VARCHAR(254),
--         telephone VARCHAR(50),
--         team bigserial,
--         constraint fk_teams
--         foreign key (team)
--         references Teams(id_team)
--         );
--
--         -- drop table if exists Gyms cascade;
--         CREATE TABLE IF NOT EXISTS Gyms
--         (
--         id_gym BIGSERIAL PRIMARY KEY ,
--         sport bigserial,
--         gym_owner bigserial,
--         name varchar(50),
--         price bigserial,
--         constraint fk_sport
--         foreign key (sport)
--         references Sports(id_sport),
--         constraint fk_gym_owner
--         foreign key (gym_owner)
--         references Gym_owners(id_owner)
--         );
--
--         -- drop table if exists Events cascade;
--         CREATE TABLE IF NOT EXISTS Events
--         (
--         id_event BIGSERIAL PRIMARY KEY ,
--         team bigserial,
--         gym bigserial,
--         sport bigserial not null,
--         name varchar(50),
--         start_time date,
--         finish_time date,
--         constraint fk_team
--         foreign key (team)
--         references Teams(id_team),
--         constraint fk_gym
--         foreign key (gym)
--         references Gyms(id_gym),
--         constraint fk_sport
--         foreign key (sport)
--         references Sports(id_sport)
--         );

--CREATE SEQUENCE clients_id_seq START WITH 3 INCREMENT BY 1;
--DROP TABLE IF EXISTS users
