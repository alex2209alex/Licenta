--changeset alex.pavel:2
-- PORT
create table ge.port
(
    port_id    INTEGER not null,
    den_oras   VARCHAR(200) not null,
    adresa     VARCHAR(500) not null,
    cod_postal VARCHAR(50) not null,
    constraint pk_port primary key (port_id),
    constraint u_den_oras unique (den_oras),
    constraint u_cod_postal unique (cod_postal)
);

-- NAVA
create table ge.nava
(
    nava_id      INTEGER not null,
    den_nava     VARCHAR(200) not null,
    numar_imo    VARCHAR(10) not null,
    lungime      NUMERIC(10,2) not null,
    pescaj_maxim NUMERIC(10,2) not null,
    dwt          NUMERIC(10,2) not null,
    cod_tara     VARCHAR(2) not null,
    constraint pk_nava primary key (nava_id),
    constraint fk_cod_tara foreign key (cod_tara) references ge.tara (cod_tara),
    constraint u_numar_imo unique (numar_imo),
    constraint cn_lungime CHECK (lungime > 0),
    constraint cn_pescaj_maxim CHECK (pescaj_maxim > 0),
    constraint cn_dwt CHECK (dwt > 0)
);

-- LOCATIE
create table ge.locatie
(
    locatie_id  INTEGER not null,
    port_id     INTEGER not null,
    den_locatie VARCHAR(200) not null,
    adancime    NUMERIC(10,2) not null,
    lungime     NUMERIC(10,2),
    operativa   NUMERIC(1,0) not null,
    constraint pk_locatie primary key (locatie_id),
    constraint fk_loc_port foreign key (port_id) references ge.port (port_id),
    constraint u_den_locatie unique (den_locatie, port_id),
    constraint cl_adancime CHECK (adancime > 0),
    constraint cl_lungime CHECK (lungime > 0),
    constraint cl_operativa check (operativa in (0, 1))
);
