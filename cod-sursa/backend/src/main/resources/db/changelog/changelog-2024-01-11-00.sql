--liquibase formatted sql
--changeset alex.pavel:4

DROP SEQUENCE ge.utizilator_seq;

DROP SEQUENCE ge.rol_seq;

DROP TABLE ge.utilizator_rol;

DROP TABLE ge.rol;

DROP TABLE ge.utilizator;
