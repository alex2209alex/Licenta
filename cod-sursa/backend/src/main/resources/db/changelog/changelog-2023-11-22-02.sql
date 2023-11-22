--changeset alex.pavel:3
-- ESCALA
create table ge.escala
(
    escala_id    INTEGER not null,
    nava_id      INTEGER not null,
    port_id      INTEGER not null,
    stare_escala NUMERIC(1,0) not null,
    constraint pk_escala primary key (escala_id),
    constraint fk_esc_nava foreign key (nava_id) references ge.nava (nava_id),
    constraint fk_esc_port foreign key (port_id) references ge.port (port_id),
    constraint ce_stare check (stare_escala in (0, 1, 2))
);

-- MANEVRA
create table ge.manevra
(
    manevra_id         INTEGER not null,
    escala_id          INTEGER not null,
    locatie_plecare_id INTEGER not null,
    locatie_sosire_id  INTEGER not null,
    data_ora_start     DATE    not null,
    data_ora_final     DATE    not null,
    tip_manevra        NUMERIC(1,0) not null,
    constraint pk_manevra primary key (manevra_id),
    constraint fk_man_escala foreign key (escala_id) references ge.escala (escala_id),
    constraint fk_mansp_locatie foreign key (locatie_plecare_id) references ge.locatie (locatie_id),
    constraint fk_mans_locatie foreign key (locatie_sosire_id) references ge.locatie (locatie_id),
    constraint cm_tip_manevra check (tip_manevra in (0, 1, 2))
);

-- MARFA
create table ge.marfa
(
    marfa_id  INTEGER not null,
    den_marfa VARCHAR(200) not null,
    constraint pk_marfa primary key (marfa_id),
    constraint u_den_marfa unique (den_marfa)
);

-- FIRMA
create table ge.firma
(
    firma_id  INTEGER not null,
    den_firma VARCHAR(200) not null,
    tip_firma NUMERIC(1,0) not null,
    constraint pk_firma primary key (firma_id),
    constraint u_den_firma unique (den_firma),
    constraint cf_tip_firma check (tip_firma in (0, 1, 2))
);

-- DOCUMENT
create table ge.document
(
    document_id   INTEGER not null,
    escala_id     INTEGER not null,
    firma_id      INTEGER not null,
    data_document DATE    not null,
    constraint pk_document primary key (document_id),
    constraint fk_doc_escala foreign key (escala_id) references ge.escala (escala_id),
    constraint fk_doc_firma foreign key (firma_id) references ge.firma (firma_id)
);

-- AVIZARE_MARITIMA
create table ge.avizare_maritima
(
    document_id              INTEGER not null,
    data_ora_estimare_sosire DATE    not null,
    constraint pk_avizare_maritima primary key (document_id),
    constraint fk_am_document foreign key (document_id) references ge.document (document_id)
);

-- MARFA_DECLARATA
create table ge.marfa_declarata
(
    document_id    INTEGER not null,
    marfa_id       INTEGER not null,
    cantitate_tone NUMERIC(10,2) not null,
    constraint pk_marfa_decl primary key (document_id, marfa_id),
    constraint fk_mdecl_document foreign key (document_id) references ge.document (document_id),
    constraint fk_mdecl_marfa foreign key (marfa_id) references ge.marfa (marfa_id),
    constraint cmd_cantitate_tone CHECK (cantitate_tone > 0)
);
