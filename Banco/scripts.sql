DROP DATABASE IF EXISTS academia;
CREATE DATABASE IF NOT EXISTS academia;

USE academia;

DROP TABLE IF EXISTS usuarios;
CREATE TABLE IF NOT EXISTS usuarios (
	id 		SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Armazena o codigo sequencial do usuario',
    login 	VARCHAR(120) NOT NULL COMMENT 'Informa o login de acesso do usuario',
    nome 	VARCHAR(30) NOT NULL COMMENT 'Informa o nome do usuario',
    senha 	VARCHAR(32) NOT NULL COMMENT 'Armazena a senha criptografada do usuario',
    admin 	BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Informa se o usuario é um administrador',
    KEY idx_id (id),
    KEY idx_login (login),
    KEY idx_senha (senha),
    PRIMARY KEY (id)
) COMMENT 'Armazena os usuarios do sistema';

INSERT IGNORE INTO usuarios (login, nome, senha, admin) VALUES ('Master', 'MASTER', 'Master', true);

DROP TABLE IF EXISTS contasReceber;
CREATE TABLE IF NOT EXISTS contasReceber (
	id 			SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Armazena o codigo sequencial do contas a receber',
    dataemissao DATETIME NOT NULL COMMENT 'Armazena a data e a hora do computador em que o dado foi inserido',
    vbruto 		DECIMAL(10,2) UNSIGNED NOT NULL COMMENT 'Armazena o valor bruto do conta a receber',
    vdesconto 	DECIMAL(10,2) UNSIGNED NOT NULL COMMENT 'Armazena o valor do desconto aplicado no conta a receber',
    vliquido 	DECIMAL(10,2) UNSIGNED NOT NULL COMMENT 'Armazena o valor liquido do conta a receber',
    KEY idx_id (id),
    KEY idx_dataemissao (dataemissao),
    PRIMARY KEY (id)
) COMMENT 'Armazena os contas a receber da empresa';
