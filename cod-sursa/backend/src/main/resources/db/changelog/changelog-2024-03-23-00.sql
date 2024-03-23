--liquibase formatted sql
--changeset alex.pavel:5

ALTER TABLE ge.manevra
DROP CONSTRAINT fk_man_escala;

ALTER TABLE ge.manevra
DROP COLUMN fk_escala;