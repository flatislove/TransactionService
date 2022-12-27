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
    number   VARCHAR(10) NOT NULL,
    balance  NUMERIC     NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions
(
    id             BIGSERIAL PRIMARY KEY,
    account_id     BIGSERIAL,
    account_to     VARCHAR(10),
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