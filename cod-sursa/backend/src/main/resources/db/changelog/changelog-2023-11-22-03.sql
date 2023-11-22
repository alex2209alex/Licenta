--changeset alex.pavel:4
-- AVIZARE_DOCUMENT
create table ge.avizare_document
(
    document_id       INTEGER not null,
    firma_id          INTEGER not null,
    data_ora_avizarii DATE    not null,
    rezolutie         INTEGER not null,
    constraint pk_rez_document primary key (document_id, firma_id),
    constraint fk_rez_document foreign key (document_id) references ge.document (document_id),
    constraint fk_rez_firma foreign key (firma_id) references ge.firma (firma_id),
    constraint cad_rezolutie check (rezolutie in (0, 1))
);

-- OPERARE_MARFA
create table ge.operare_marfa
(
    escala_id      INTEGER not null,
    locatie_id     INTEGER not null,
    marfa_id       INTEGER not null,
    firma_id       INTEGER not null,
    tip_operatie   NUMERIC(1,0) not null,
    cantitate_tone NUMERIC(10,2) not null,
    constraint pk_op_marfa primary key (escala_id, locatie_id, marfa_id, firma_id),
    constraint fk_opmarf_escala foreign key (escala_id) references ge.escala (escala_id),
    constraint fk_opmarf_locatie foreign key (locatie_id) references ge.locatie (locatie_id),
    constraint fk_opmarf_marfa foreign key (marfa_id) references ge.marfa (marfa_id),
    constraint fk_opmarf_firma foreign key (firma_id) references ge.firma (firma_id),
    constraint com_tip_operatie check (tip_operatie in (0, 1)),
    constraint com_cantitate_tone CHECK (cantitate_tone > 0)
);