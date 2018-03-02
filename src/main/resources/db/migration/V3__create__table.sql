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

CREATE TABLE equipamento (
    id	BIGINT	auto_increment NOT NULL,
    nome	VARCHAR (250),
    status	VARCHAR (250),
    dataInicio	VARCHAR (250),
    dataTermino	VARCHAR (250),
    segmento	VARCHAR (250),
    descricao	VARCHAR (500),
    tipo_id	BIGINT,
    fabricante_id	BIGINT,
    precificacao_id	BIGINT,
    homologado_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (fabricante_id) REFERENCES fabricante(id) on delete cascade,
    FOREIGN KEY (precificacao_id) REFERENCES precificacao(id) on delete cascade,
    FOREIGN KEY (tipo_id) REFERENCES tipo(id) on delete cascade,
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