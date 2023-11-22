--changeset alex.pavel:1
-- TARA
create table ge.tara
(
    cod_tara VARCHAR(2)   not null,
    den_tara VARCHAR(200) not null,
    constraint pk_tara primary key (cod_tara)
);