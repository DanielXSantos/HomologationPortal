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
    preco	double,
    PRIMARY KEY (id)
);

CREATE TABLE tipo (
	id	BIGINT auto_increment NOT NULL,
	nome	VARCHAR (250),
	descricao VARCHAR(250),
	PRIMARY KEY (id)
);
CREATE TABLE equipamento (
    id	BIGINT	auto_increment NOT NULL,
    nome	VARCHAR (250),
    status	VARCHAR (250),
    data	VARCHAR (250),
    segmento	VARCHAR (250),
    id_tipo	BIGINT,
    id_fabricante	BIGINT,
    id_features	BIGINT,
    id_precificacao	BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (id_fabricante) REFERENCES fabricante(id) on delete cascade,
    FOREIGN KEY (id_features) REFERENCES features(id) on delete cascade,
    FOREIGN KEY (id_precificacao) REFERENCES precificacao(id) on delete cascade,
    FOREIGN KEY (id_tipo) REFERENCES tipo(id) on delete cascade
);