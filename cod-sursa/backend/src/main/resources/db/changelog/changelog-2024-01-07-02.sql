--liquibase formatted sql
--changeset alex.pavel:3

-- ESCALA
CREATE SEQUENCE ge.escala_seq START 100;

create table ge.escala
(
    id           bigint        not null,
    fk_nava      bigint        not null,
    fk_port      bigint        not null,
    stare_escala NUMERIC(1, 0) not null,
    constraint pk_escala primary key (id),
    constraint fk_escala_nava foreign key (fk_nava) references ge.nava (id),
    constraint fk_escala_port foreign key (fk_port) references ge.port (id)
);

COMMENT ON COLUMN ge.escala.stare_escala IS '0 = Planificata, 1 = Realizata, 2 = Anulata, 3 = Arhivata';

-- AVIZARE_MARITIMA
CREATE SEQUENCE ge.avizare_maritima_seq START 100;

create table ge.avizare_maritima
(
    id                       bigint        not null,
    data_ora_estimare_sosire timestamp     not null,
    fk_agent                 bigint        not null,
    fk_escala                bigint        not null,
    data_creare              timestamp     not null,
    stare_document           numeric(1, 0) not null,
    motiv_respingere         varchar(400),
    constraint pk_avizare_maritima primary key (id),
    constraint fk_am_agent foreign key (fk_agent) references ge.firma (id),
    constraint fk_am_escala foreign key (fk_agent) references ge.firma (id)
);

COMMENT ON COLUMN ge.avizare_maritima.stare_document IS '0 = Asteapta aprobari, 1 = Aprobat ANR, 2 = Aprobat ANR si APMC, 3 = Respins, 4 = Anulata';

-- MARFA_DECLARATA
CREATE SEQUENCE ge.marfa_declarata_seq START 100;

create table ge.marfa_declarata
(
    id             bigint         not null,
    fk_am          bigint         not null,
    fk_marfa       bigint         not null,
    cantitate_tone NUMERIC(10, 2) not null,
    constraint pk_marfa_decl primary key (id),
    constraint am_marfa_uq unique (fk_am, fk_marfa),
    constraint fk_md_am foreign key (fk_am) references ge.avizare_maritima (id),
    constraint fk_md_marfa foreign key (fk_marfa) references ge.marfa (id)
);

-- MANEVRA
CREATE SEQUENCE ge.manevra_seq START 100;

create table ge.manevra
(
    id                 bigint        not null,
    fk_escala          bigint        not null,
    fk_locatie_plecare bigint        not null,
    fk_locatie_sosire  bigint        not null,
    data_ora_start     timestamp     not null,
    data_ora_final     timestamp     not null,
    tip_manevra        NUMERIC(1, 0) not null,
    stare_manevra      NUMERIC(1, 0) not null,
    constraint pk_manevra primary key (id),
    constraint fk_man_escala foreign key (fk_escala) references ge.escala (id),
    constraint fk_manp_locatie foreign key (fk_locatie_plecare) references ge.locatie (id),
    constraint fk_mans_locatie foreign key (fk_locatie_sosire) references ge.locatie (id)
);

COMMENT ON COLUMN ge.manevra.tip_manevra IS '0 = Intrare, 1 = Mutare, 2 = Iesire';
COMMENT ON COLUMN ge.manevra.stare_manevra IS '0 = Planificate, 1 = Realizata, 2 = Anulata';

-- BULETIN_PILOTAJ
CREATE SEQUENCE ge.buletin_pilotaj_seq START 100;

create table ge.buletin_pilotaj
(
    id                      bigint        not null,
    data_ora_estimare_start timestamp     not null,
    fk_operator_marfa       bigint        not null,
    fk_pilotaj              bigint        not null,
    fk_agent                bigint        not null,
    fk_escala               bigint        not null,
    data_creare             timestamp     not null,
    stare_document          numeric(1, 0) not null,
    motiv_respingere        varchar(400),
    constraint pk_buletin_pilotaj primary key (id),
    constraint fk_bp_operator_marfa foreign key (fk_operator_marfa) references ge.firma (id),
    constraint fk_bp_pilotaj foreign key (fk_pilotaj) references ge.firma (id),
    constraint fk_bp_agent foreign key (fk_agent) references ge.firma (id),
    constraint fk_bp_escala foreign key (fk_escala) references ge.escala (id)
);

COMMENT ON COLUMN ge.buletin_pilotaj.stare_document IS '0 = Asteapta aprobari, 1 = Aprobat ANR, 2 = Aprobat ANR si APMC, 3 = Realizata de firma de pilotaj, 4 = Respins, 5 = Anulata';

-- OPERARE_MARFA
CREATE SEQUENCE ge.operare_marfa_seq START 100;

create table ge.operare_marfa
(
    id               bigint         not null,
    fk_escala        bigint         not null,
    fk_locatie       bigint         not null,
    fk_marfa         bigint         not null,
    fk_firma_operare bigint         not null,
    tip_operatie     NUMERIC(1, 0)  not null,
    cantitate_tone   NUMERIC(10, 2) not null,
    constraint pk_om primary key (id),
    constraint fk_om_escala foreign key (fk_escala) references ge.escala (id),
    constraint fk_om_locatie foreign key (fk_locatie) references ge.locatie (id),
    constraint fk_om_marfa foreign key (fk_marfa) references ge.marfa (id),
    constraint fk_om_firma foreign key (fk_firma_operare) references ge.firma (id)
);

COMMENT ON COLUMN ge.operare_marfa.tip_operatie IS '0 = Incarcare, 1 = Descarcare';
