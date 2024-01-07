--liquibase formatted sql
--changeset alex.pavel:1
-- TARA
CREATE SEQUENCE ge.tara_seq START 100;

create table ge.tara
(
    id       bigint            not null,
    cod_tara VARCHAR(2) unique not null,
    den_tara VARCHAR(200)      not null,
    constraint pk_tara primary key (id)
);

INSERT INTO ge.tara(id, cod_tara, den_tara)
VALUES (1, 'RO', 'ROMANIA');
INSERT INTO ge.tara(id, cod_tara, den_tara)
VALUES (2, 'TR', 'TURCIA');
INSERT INTO ge.tara(id, cod_tara, den_tara)
VALUES (3, 'MT', 'MALTA');
INSERT INTO ge.tara(id, cod_tara, den_tara)
VALUES (4, 'LR', 'LIBERIA');
INSERT INTO ge.tara(id, cod_tara, den_tara)
VALUES (5, 'PA', 'PANAMA');
INSERT INTO ge.tara(id, cod_tara, den_tara)
VALUES (6, 'GI', 'GIBRALTAR');


-- PORT
CREATE SEQUENCE ge.port_seq START 100;

create table ge.port
(
    id       bigint             not null,
    cod_port varchar(10) unique not null,
    den_port VARCHAR(200)       not null,
    constraint pk_port primary key (id)
);

INSERT INTO ge.port(id, cod_port, den_port)
VALUES (1, 'ROCND', 'CONSTANTA');
INSERT INTO ge.port(id, cod_port, den_port)
VALUES (2, 'ROMAG', 'MANGALIA');

-- NAVA
CREATE SEQUENCE ge.nava_seq START 100;

create table ge.nava
(
    id           bigint             not null,
    den_nava     VARCHAR(200)       not null,
    numar_imo    VARCHAR(10) unique not null,
    lungime      numeric(10, 2)     not null,
    pescaj_maxim numeric(10, 2)     not null,
    dwt          numeric(10, 2)     not null,
    fk_tara      bigint             not null,
    constraint pk_nava primary key (id),
    constraint fk_nava_tara foreign key (fk_tara) references ge.tara (id)
);

-- valori inspirate din https://www.portofconstantza.com/pn/ro/nava
INSERT INTO ge.nava(id, den_nava, numar_imo, lungime, pescaj_maxim, dwt, fk_tara)
VALUES (1, 'AALBORG', 'IMO8122830', 182.3, 11.53, 37425, 6);

INSERT INTO ge.nava(id, den_nava, numar_imo, lungime, pescaj_maxim, dwt, fk_tara)
VALUES (2, 'AAL GENOA', 'IMO9393553', 159.89, 10, 25733, 4);

INSERT INTO ge.nava(id, den_nava, numar_imo, lungime, pescaj_maxim, dwt, fk_tara)
VALUES (3, '26 AGUSTOS', 'IMO9238478', 189.99, 12, 52455, 2);

INSERT INTO ge.nava(id, den_nava, numar_imo, lungime, pescaj_maxim, dwt, fk_tara)
VALUES (4, '3 MAJ', 'IMO8122730', 224.65, 13.32, 64850, 3);

INSERT INTO ge.nava(id, den_nava, numar_imo, lungime, pescaj_maxim, dwt, fk_tara)
VALUES (5, 'A LADYBUG', 'IMO9441867', 232.38, 10, 26985, 5);

INSERT INTO ge.nava(id, den_nava, numar_imo, lungime, pescaj_maxim, dwt, fk_tara)
VALUES (6, 'ABANOZ', 'IMO9121857', 99.95, 6.61, 6500, 5);

-- LOCATIE
CREATE SEQUENCE ge.locatie_seq START 100;

create table ge.locatie
(
    id          bigint         not null,
    fk_port     bigint         not null,
    den_locatie VARCHAR(200)   not null,
    adancime    NUMERIC(10, 2) not null,
    lungime     NUMERIC(10, 2),
    constraint pk_locatie primary key (id),
    constraint fk_loc_port foreign key (fk_port) references ge.port (id)
);

-- valori inspirate din https://umex.ro/dane-operatiuni/
INSERT INTO ge.locatie(id, fk_port, den_locatie, adancime, lungime)
VALUES (1, 1, '38', 12.5, 205);

INSERT INTO ge.locatie(id, fk_port, den_locatie, adancime, lungime)
VALUES (2, 1, '39', 13.5, 205);

INSERT INTO ge.locatie(id, fk_port, den_locatie, adancime, lungime)
VALUES (3, 1, '40', 13.5, 205);

INSERT INTO ge.locatie(id, fk_port, den_locatie, adancime, lungime)
VALUES (4, 1, 'RORO3', 12.5, 185);

INSERT INTO ge.locatie(id, fk_port, den_locatie, adancime, lungime)
VALUES (5, 1, 'RADA-CT', 100, null);

INSERT INTO ge.locatie(id, fk_port, den_locatie, adancime, lungime)
VALUES (6, 2, 'RADA-MG', 100, null);

INSERT INTO ge.locatie(id, fk_port, den_locatie, adancime, lungime)
VALUES (7, 2, '01', 12, null);

INSERT INTO ge.locatie(id, fk_port, den_locatie, adancime, lungime)
VALUES (8, 2, '02', 11, null);

-- MARFA
CREATE SEQUENCE ge.marfa_seq START 100;

create table ge.marfa
(
    id        bigint              not null,
    den_marfa VARCHAR(200) unique not null,
    constraint pk_marfa primary key (id)
);

INSERT INTO ge.marfa(id, den_marfa)
VALUES (1, 'Porumb');
INSERT INTO ge.marfa(id, den_marfa)
VALUES (2, 'Grau');
INSERT INTO ge.marfa(id, den_marfa)
VALUES (3, 'Carbune');
INSERT INTO ge.marfa(id, den_marfa)
VALUES (4, 'Fier vechi');
INSERT INTO ge.marfa(id, den_marfa)
VALUES (5, 'Azotat');
INSERT INTO ge.marfa(id, den_marfa)
VALUES (6, 'Banane');

-- FIRMA
create table ge.firma
(
    id        bigint              not null,
    den_firma VARCHAR(200) unique not null,
    tip_firma NUMERIC(1, 0)       not null,
    constraint pk_firma primary key (id)
);

COMMENT ON COLUMN ge.firma.tip_firma IS '0 = Agent nava, 1 = Operator marfa, 2 = Autoritate, 3 = Companie de pilotaj';

-- valori inspirate din https://www.portofconstantza.com/pn/ro/servicii/2
-- autoritate
INSERT INTO ge.firma(id, den_firma, tip_firma)
VALUES (1, 'Compania Nationala Administratia Porturilor Maritime', 2);
-- autoritate
INSERT INTO ge.firma(id, den_firma, tip_firma)
VALUES (2, 'Autoritatea Navala Romana', 2);
-- agent nava
INSERT INTO ge.firma(id, den_firma, tip_firma)
VALUES (3, 'ADEMAR TRANSPORT LINE SRL', 0);
-- agent nava
INSERT INTO ge.firma(id, den_firma, tip_firma)
VALUES (4, 'AGREX SHIPPING AND TRADING S.R.L.', 0);
-- operator portuar
INSERT INTO ge.firma(id, den_firma, tip_firma)
VALUES (5, 'UMEX', 1);
-- operator portuar
INSERT INTO ge.firma(id, den_firma, tip_firma)
VALUES (6, 'DECIROM', 1);
-- companie de pilotaj
INSERT INTO ge.firma(id, den_firma, tip_firma)
VALUES (7, '	COMPANIA DE PILOTAJ PILOT SERVICE SA', 3);
