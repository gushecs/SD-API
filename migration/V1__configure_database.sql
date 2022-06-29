CREATE TABLE tb_user (
 id integer not null AUTO_INCREMENT,
 authorization_status varchar(255),
 email varchar(255) not null unique,
 mobile_phone varchar(255),
 name varchar(255) not null,
 password varchar(255) not null,
 surname varchar(255),
 primary key (id)
);

CREATE TABLE crm (
 id integer not null AUTO_INCREMENT,
 crm varchar(45) not null,
 specialty varchar(255),
 uf varchar(2) not null,
 user_id integer,
 constraint crm_uf unique (crm, uf),
 primary key (id),
 foreign key (user_id) references tb_user(id)
);

INSERT INTO tb_user(NAME, SURNAME, EMAIL, PASSWORD, MOBILE_PHONE)
VALUES ('Maria','Bethânia','maria@bethania.com','password','22912345678'),
('Person','McPerson','real@person.com','123456','21971717272'),
('Usuario','Testeum','usuario.teste-1@email.com','hard_password','997456123'),
('Usuario','Testedois','usuario.teste-2@email.com','harder_password','997056123'),
('Usuario','Testetres','usuario.teste-3@email.com','hardest_password','997456123');

INSERT INTO crm(CRM, UF, SPECIALTY, USER_ID)
VALUES ('123', 'RJ', 'Cardiologia', 1),
       ('123', 'SP', 'Ortopedia', 1),
       ('12343', 'AC', null, 3),
       ('123', 'CE', 'Cardiologia', 4),
       ('123', 'TO', null, null);