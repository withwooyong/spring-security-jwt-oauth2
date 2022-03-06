/*
IMPORTANT:
    If using PostgreSQL, update ALL columns defined with 'blob' to 'text',
    as PostgreSQL does not support the 'blob' data type.
*/
DROP TABLE if EXISTS oauth2_registered_client;
CREATE TABLE oauth2_registered_client
(
    id                            varchar(100)                            NOT NULL UNIQUE KEY ,
    client_id                     varchar(100)                            NOT NULL PRIMARY KEY ,
    client_id_issued_at           timestamp     DEFAULT CURRENT_TIMESTAMP NOT NULL,
    client_secret                 varchar(200)  DEFAULT NULL,
    client_secret_expires_at      timestamp     DEFAULT NULL,
    client_name                   varchar(200)                            NOT NULL,
    client_authentication_methods varchar(1000)                           NOT NULL,
    authorization_grant_types     varchar(1000)                           NOT NULL,
    redirect_uris                 varchar(1000) DEFAULT NULL,
    scopes                        varchar(1000)                           NOT NULL,
    client_settings               varchar(2000)                           NOT NULL,
    token_settings                varchar(2000)                           NOT NULL
);



DROP TABLE if EXISTS oauth2_authorization;
CREATE TABLE oauth2_authorization
(
    id                            varchar(100) NOT NULL UNIQUE KEY ,
    registered_client_id          varchar(100) NOT NULL,
    principal_name                varchar(200) NOT NULL PRIMARY KEY ,
    authorization_grant_type      varchar(100) NOT NULL,
    attributes                    blob          DEFAULT NULL,
    state                         varchar(500)  DEFAULT NULL,
    authorization_code_value      blob          DEFAULT NULL,
    authorization_code_issued_at  timestamp     DEFAULT NULL,
    authorization_code_expires_at timestamp     DEFAULT NULL,
    authorization_code_metadata   blob          DEFAULT NULL,
    access_token_value            blob          DEFAULT NULL,
    access_token_issued_at        timestamp     DEFAULT NULL,
    access_token_expires_at       timestamp     DEFAULT NULL,
    access_token_metadata         blob          DEFAULT NULL,
    access_token_type             varchar(100)  DEFAULT NULL,
    access_token_scopes           varchar(1000) DEFAULT NULL,
    oidc_id_token_value           blob          DEFAULT NULL,
    oidc_id_token_issued_at       timestamp     DEFAULT NULL,
    oidc_id_token_expires_at      timestamp     DEFAULT NULL,
    oidc_id_token_metadata        blob          DEFAULT NULL,
    refresh_token_value           blob          DEFAULT NULL,
    refresh_token_issued_at       timestamp     DEFAULT NULL,
    refresh_token_expires_at      timestamp     DEFAULT NULL,
    refresh_token_metadata        blob          DEFAULT NULL,
    FOREIGN KEY (registered_client_id) REFERENCES oauth2_registered_client(client_id)
);

DROP TABLE if EXISTS oauth2_authorization_consent;
CREATE TABLE oauth2_authorization_consent
(
    registered_client_id varchar(100)  NOT NULL,
    principal_name       varchar(200)  NOT NULL,
    authorities          varchar(1000) NOT NULL,
    FOREIGN KEY (registered_client_id) references oauth2_registered_client(client_id),
    FOREIGN KEY (principal_name) references oauth2_authorization(principal_name)
);


CREATE TABLE `parent`
(
      `id1` INT(11) NOT NULL,
      `id2` INT(11) NOT NULL,
      `id3` INT(11) NOT NULL,
      `uk1` INT(11) NOT NULL,
      `uk2` INT(11) NOT NULL,
      `uk3` INT(11) NOT NULL,
      PRIMARY KEY (`id1`, `id2`, `id3`),
      UNIQUE KEY (`uk1`, `uk2`, `uk3`)
);

CREATE TABLE `child`
(
     `id` INT(11) NOT NULL,
     `id1` INT(11) NOT NULL,
     `id2` INT(11) NOT NULL,
     `uk1` INT(11) NOT NULL,
     `uk2` INT(11) NOT NULL,
     PRIMARY KEY (`id`),
     FOREIGN KEY (`id1`, `id2`) REFERENCES `parent` (`id1`, `id2`),
     FOREIGN KEY (`uk1`, `uk2`) REFERENCES `parent` (`uk1`, `uk2`)
)

create table department
(
       id int auto_increment primary key,
       name varchar(20) not null,
       code char(13) not null unique key
);

create table employee (
      id int auto_increment primary key,
      name varchar(20) not null,
      code char(13) not null unique key,
      dept_id int,
      foreign key (dept_id) references department(id)
);
