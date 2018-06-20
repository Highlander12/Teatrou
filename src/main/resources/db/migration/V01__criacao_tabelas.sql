CREATE TABLE usuario (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao (
	codigo BIGINT(20) PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE usuario_permissao (
	codigo_usuario BIGINT(20) NOT NULL,
	codigo_permissao BIGINT(20) NOT NULL,
	PRIMARY KEY (codigo_usuario, codigo_permissao),
	FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
	FOREIGN KEY (codigo_permissao) REFERENCES permissao(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE compra (
   codigo VARCHAR(40) PRIMARY KEY,
   codigo_usuario BIGINT(20),
   quantidade_ingresso INT NOT NULL,
   data_compra DATE NOT NULL,
   valor_total DECIMAL(10,2) NOT NULL,
   FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE evento (
   codigo BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
   codigo_usuario BIGINT(20), 
   titulo VARCHAR(255) NOT NULL,
   descricao VARCHAR(255),
   data_evento DATE,
   hora_inicial VARCHAR(255),
   hora_final VARCHAR(255),
   tema VARCHAR(255),
   endereco VARCHAR(255),
   quantidade_ingresso INT NOT NULL,
   valor_ingresso DECIMAL(10,2) NOT NULL,
   ativo BOOLEAN NOT NULL,
   FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
 
 CREATE TABLE ingresso (
   codigo BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
   codigo_evento BIGINT(20),
   codigo_compra BIGINT(20),
   faixa_etaria VARCHAR(20) NOT NULL,
   ativo BOOLEAN NOT NULL,
   FOREIGN KEY (codigo_evento) REFERENCES evento(codigo),
   FOREIGN KEY (codigo_compra) REFERENCES compra(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


