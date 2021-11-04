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

INSERT IGNORE INTO usuarios (login, nome, senha, admin) VALUES ('Master', 'MASTER', 'b8e8b80c9a269375a8526b12eefc8cbf', true);

DROP TABLE IF EXISTS alunos;
CREATE TABLE IF NOT EXISTS alunos (
	id 				MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Armazena o codigo sequencial do aluno',
    nome 			VARCHAR(40) NOT NULL COMMENT 'Informa o nome do usuario',
    telefone		VARCHAR(11) NOT NULL COMMENT 'Informa o telefone do usuario',
    email			VARCHAR(60) NULL COMMENT 'Informa o email do usuario',
    cpf				CHAR(11) NOT NULL COMMENT 'Informa o cpf do usuario',
    sexo			ENUM ('M','F') NOT NULL COMMENT 'Informa o sexo do usuario M - Masculino F - Feminino',
    datanascimento 	DATETIME NOT NULL COMMENT 'Informa a data de nascimento do usuario',
    datacadastro 	DATETIME NOT NULL COMMENT 'Informa a data em que o usuario em questão foi cadastrado',
    observacao		TEXT NULL COMMENT 'Informa uma observação sobre o usuario',
    KEY idx_id (id),
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS recebimentos;
CREATE TABLE IF NOT EXISTS recebimentos (
	id 				MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Armazena o codigo sequencial do recebimento',
    aluno_id		MEDIUMINT UNSIGNED NOT NULL COMMENT 'Armazena o id do aluno',
    usuario_id		SMALLINT UNSIGNED NOT NULL COMMENT 'Armazena o id do usuario que inseriu o registro',
    dataVencimento  DATETIME NOT NULL COMMENT 'Informa a data de vencimento do recebimento',
    dataPagamento 	DATETIME NOT NULL COMMENT 'Informa a data de pagamento do recebimento',
    formaPagamento	ENUM ('1', '2', '3', '4', '5') NOT NULL COMMENT 'Informa a forma de pagamento utilizada sendo: 1 - Dinheiro, 2 - PIX, 3 - Transferência, 4 - Cartão de Crédito, 5 - Cartão de Débito',
    vRecebimento	DECIMAL(10,2) NOT NULL COMMENT 'Informa o valor do recebimento',
    vDesconto		DECIMAL(10,2) NOT NULL COMMENT 'Informa o valor de disconto aplicado no recebimento',
    vTotal			DECIMAL(10,2) NOT NULL COMMENT 'Informa o valor total do recebimento',
    matricula		TINYINT(1) NOT NULL DEFAULT b'0' COMMENT 'Informa se o recebimento é da maatriculo',
    KEY idx_id (id),
    KEY idx_aluno_id (aluno_id),
    KEY idx_usuario_id (usuario_id),
    KEY idx_dataVencimento (dataVencimento),
    KEY idx_dataPagamento (dataPagamento),
    PRIMARY KEY (id)
);
