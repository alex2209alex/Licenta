--liquibase formatted sql
--changeset alex.pavel:2

-- UTILIZATOR
CREATE SEQUENCE ge.utizilator_seq START 100;

create table ge.utilizator
(
    id       bigint              not null,
    email    VARCHAR(100) unique not null,
    parola   VARCHAR(400)        not null,
    fk_firma bigint              not null,
    constraint pk_utilizator primary key (id),
    constraint fk_utilizator_firma foreign key (fk_firma) references ge.firma (id)
);
-- ROL
CREATE SEQUENCE ge.rol_seq START 100;

create table ge.rol
(
    id       bigint              not null,
    denumire VARCHAR(100) unique not null,
    constraint pk_rol primary key (id)
);

INSERT INTO ge.rol(id, denumire)
VALUES (1, 'ROL_AGENT_NAVA');
INSERT INTO ge.rol(id, denumire)
VALUES (2, 'ROL_DISPECER_APMC');
INSERT INTO ge.rol(id, denumire)
VALUES (3, 'ROL_DISPECER_ANR');
INSERT INTO ge.rol(id, denumire)
VALUES (4, 'ROL_DISPECER_PILOTAJ');
INSERT INTO ge.rol(id, denumire)
VALUES (5, 'ROL_OPERATOR_DE_MARFA');
INSERT INTO ge.rol(id, denumire)
VALUES (6, 'ROL_ADMINISTRATOR_SISTEM');


-- UTILIZATOR_ROL
create table ge.utilizator_rol
(
    fk_utilizator bigint not null,
    fk_rol        bigint not null,
    constraint pk_utilizator_rol primary key (fk_utilizator, fk_rol),
    constraint fk_utilizator_rol_utilizator foreign key (fk_utilizator) references ge.utilizator (id),
    constraint fk_utilizator_rol_rol foreign key (fk_rol) references ge.rol (id)
);
