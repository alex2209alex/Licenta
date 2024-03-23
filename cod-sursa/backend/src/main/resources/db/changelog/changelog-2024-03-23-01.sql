--liquibase formatted sql
--changeset alex.pavel:6

COMMENT ON COLUMN ge.escala.stare_escala IS '0 = Planificata, 1 = Realizata, 2 = Anulata, 3 = Arhivata, 4 = Aprobata';
