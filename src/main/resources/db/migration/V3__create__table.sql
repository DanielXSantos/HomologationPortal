CREATE TABLE fabricante (
    id	BIGINT	auto_increment NOT NULL,
    nome	VARCHAR (250),
    email	VARCHAR (250),
    telefone	VARCHAR (250),
    PRIMARY KEY (id)
);

CREATE TABLE features (
    id	BIGINT	auto_increment NOT NULL,
    nome	VARCHAR (250),
    descricao	VARCHAR (250),
    PRIMARY KEY (id)
);

CREATE TABLE precificacao (
    id	BIGINT	NOT NULL,
    tipo	VARCHAR (250),
    part_nro	VARCHAR (250),
    descricao	VARCHAR (250),
    PRIMARY KEY (id)
);

CREATE TABLE tipo (
	id	BIGINT auto_increment NOT NULL,
	nome	VARCHAR (250),
	descricao VARCHAR(250),
	PRIMARY KEY (id)
);

CREATE TABLE homologado(
	id	BIGINT	auto_increment	NOT NULL,
	nome	VARCHAR (250),
	descricao	VARCHAR (250),
	PRIMARY KEY	(id)
);

CREATE TABLE requisito (
    id	BIGINT	auto_increment NOT NULL,
    nome	VARCHAR (250),
    descricao	VARCHAR (250),
    PRIMARY KEY (id)
);

CREATE TABLE equipamento (
    id	BIGINT	auto_increment NOT NULL,
    nome	VARCHAR (250),
    status	VARCHAR (250),
    data_inicio	 DATETIME,
    data_termino DATETIME,
    segmento	VARCHAR (250),
    descricao	VARCHAR (500),
    fabricante_id	BIGINT,
    precificacao_id	BIGINT,
    homologado_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (fabricante_id) REFERENCES fabricante(id) on delete cascade,
    FOREIGN KEY (precificacao_id) REFERENCES precificacao(id) on delete cascade,
    FOREIGN KEY (homologado_id) REFERENCES homologado(id) on delete cascade
);

-- Tabela auxiliar ManyToMany
CREATE TABLE equipamento_features(
    equipamento_id  BIGINT NOT NULL,
    features_id     BIGINT NOT NULL,
    PRIMARY KEY(equipamento_id, features_id),
    CONSTRAINT equipamento_id_fk FOREIGN KEY (equipamento_id) REFERENCES equipamento(id),
    CONSTRAINT features_id_fk FOREIGN KEY (features_id) REFERENCES features(id)    
);

CREATE TABLE homologado_para(
    equipamento_id BIGINT NOT NULL,
    homologado_id  BIGINT NOT NULL,
    PRIMARY KEY(equipamento_id, homologado_id),
    CONSTRAINT equipamento_id_homologado_fk FOREIGN KEY (equipamento_id) REFERENCES equipamento(id),
    CONSTRAINT homologado_id_fk FOREIGN KEY (homologado_id) REFERENCES homologado(id)
);

CREATE TABLE equipamento_requisito(
    equipamento_id  BIGINT NOT NULL,
    requisito_id     BIGINT NOT NULL,
    PRIMARY KEY(equipamento_id, requisito_id),
    CONSTRAINT equipamento_id_requisito_fk FOREIGN KEY (equipamento_id) REFERENCES equipamento(id),
    CONSTRAINT requisito_id_fk FOREIGN KEY (requisito_id) REFERENCES requisito(id)    
);

CREATE TABLE equipamento_tipo(
    equipamento_id  BIGINT NOT NULL,
    tipo_id     BIGINT NOT NULL,
    PRIMARY KEY(equipamento_id, tipo_id),
    CONSTRAINT equipamento_id_tipo_fk FOREIGN KEY (equipamento_id) REFERENCES equipamento(id),
    CONSTRAINT tipo_id_fk FOREIGN KEY (tipo_id) REFERENCES tipo(id)    
);


INSERT INTO `fabricante` VALUES 
(1,'Lucas','Lucasgomezsousa@gmail.com',992937515),
(2,'Daniel','emaildaniel@email.com',998617878),
(3,'Ronistone','ronipedra@gmail.com',993547060);

INSERT INTO `features` VALUES
(1,'ADSL','Acesso a Internet'),
(2,'Wifi','Acesso a Internet'),
(3,'Ronistone','manda pro github');

INSERT INTO `precificacao` VALUES
(1,'Cabo','123.456-789','Cabo de Energia'),
(2,'Cabo','987.654-321','Cabo de Energia'),
(3,'Teclado','1.0.0','Teclado ABNT2');

INSERT INTO `tipo` VALUES
(1,'Cabos','Cabos de Rede'),
(2,'Mouse','Mouse Wireless'),
(3,'Teclado','Teclado ABNT');

INSERT INTO `requisito` VALUES
(1,'wifi','wifi');

INSERT INTO `homologado` VALUES
(1,'Homologado','Concluido'),
(2,'Em Teste','Em Testes'),
(3,'Não-Homologado','Não foi homologado');


INSERT INTO `equipamento` VALUES	
(1,'Switch','EmTestes','2003-09-03','2012-11-17','B2B','Conexao a Internet',1,2,3),
(2,'Desktop','NaoHomologado','2019-03-18','2017-04-18','B2C','Uso CTT',2,3,3),
(3,'Fax','Homologado','2013-04-10','2017-03-18','B2B-B2C','Uso dos Associados',3,1,1);

INSERT INTO `equipamento_tipo` VALUE
(1,1),(1,2),(2,3),(2,1),(2,2),(3,3);


INSERT INTO `user` VALUE(1, 1, 'admin@admin.com','admin', 'admin','admin','2019-01-01');
INSERT INTO `role` VALUE(DEFAULT, 'ADMIN', 1),
                        (DEFAULT, 'USER', 1);