CREATE TABLE IF NOT EXISTS APP_USER (
  id                    SERIAL          NOT NULL PRIMARY KEY,
  login                 varchar(50)     NOT NULL,
  password              varchar(100)    NOT NULL,
  role                  varchar(20)     NOT NULL,
  first_name            varchar(50),
  last_name             varchar(50),
  UNIQUE (login)
);

CREATE TABLE IF NOT EXISTS TICKET (
  id                    SERIAL          NOT NULL PRIMARY KEY,
  created_date          timestamp       NOT NULL,
  user_id               integer         NOT NULL,
  status                varchar(20)     NOT NULL,
  subject               varchar(50)     NOT NULL,
  message               text,
  rating                smallint,
  FOREIGN KEY (user_id) REFERENCES APP_USER(id)
);

