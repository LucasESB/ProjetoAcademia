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
	id 					MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Armazena o codigo sequencial do aluno',
    nome 				VARCHAR(40) NOT NULL COMMENT 'Informa o nome do aluno',
    telefone			VARCHAR(11) NOT NULL COMMENT 'Informa o telefone do aluno',
    whatsapp			VARCHAR(11) NULL COMMENT 'Informa o telefone do whatsapp do aluno',
    email				VARCHAR(60) NULL COMMENT 'Informa o email do aluno',
    cpf					CHAR(11) NOT NULL COMMENT 'Informa o cpf do aluno',
    sexo				ENUM ('M','F') NOT NULL COMMENT 'Informa o sexo do aluno M - Masculino F - Feminino',
    datanascimento 		DATETIME NOT NULL COMMENT 'Informa a data de nascimento do aluno',
    datacadastro 		DATETIME NOT NULL COMMENT 'Informa a data em que o aluno em questão foi cadastrado',
    diaPrePagamento 	TINYINT UNSIGNED NOT NULL COMMENT 'Informa o dia de preferência do pagamento da mensalidade',
    observacao			TEXT NULL COMMENT 'Informa uma observação sobre o aluno',
    KEY idx_id (id),
    PRIMARY KEY (id)
) COMMENT 'Armazena os alunos do sistema';

DROP TABLE IF EXISTS recebimentos;
CREATE TABLE IF NOT EXISTS recebimentos (
	id 					MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Armazena o codigo sequencial do recebimento',
    aluno_id			MEDIUMINT UNSIGNED NOT NULL COMMENT 'Armazena o id do aluno',
    aluno_nome 			VARCHAR(40) NOT NULL COMMENT 'Informa o nome do aluno',
    usuario_id			SMALLINT UNSIGNED NOT NULL COMMENT 'Armazena o id do usuario que inseriu o registro',
    usuario_nome 		VARCHAR(30) NOT NULL COMMENT 'Informa o nome do usuario',
    usuarioEdicao_id	SMALLINT UNSIGNED NULL COMMENT 'Armazena o id do ultimo usuario que editou o registro',
    usuarioEdicao_nome 	VARCHAR(30) NULL COMMENT 'Informa o nome do ultimo usuario que editou o registro',
    dataVencimento  	DATETIME NOT NULL COMMENT 'Informa a data de vencimento do recebimento',
    dataPagamento 		DATETIME NOT NULL COMMENT 'Informa a data de pagamento do recebimento',
    dataEdicao 			DATETIME NULL COMMENT 'Informa a data da ultima edição feita no recebimento',
    formaPagamento		ENUM ('1', '2', '3', '4', '5') NOT NULL COMMENT 'Informa a forma de pagamento utilizada sendo: 1 - Dinheiro, 2 - PIX, 3 - Transferência, 4 - Cartão de Crédito, 5 - Cartão de Débito',
    vRecebimento		DECIMAL(10,2) NOT NULL COMMENT 'Informa o valor do recebimento',
    vDesconto			DECIMAL(10,2) NOT NULL COMMENT 'Informa o valor de disconto aplicado no recebimento',
    vTotal				DECIMAL(10,2) NOT NULL COMMENT 'Informa o valor total do recebimento',
    matricula			TINYINT(1) NOT NULL DEFAULT b'0' COMMENT 'Informa se o recebimento é da maatriculo',
    excluido			TINYINT(1) NOT NULL DEFAULT b'0' COMMENT 'Informa se o recebimento está marcado como excluido',
    KEY idx_id (id),
    KEY idx_aluno_id (aluno_id),
    KEY idx_usuario_id (usuario_id),
    KEY idx_dataVencimento (dataVencimento),
    KEY idx_dataPagamento (dataPagamento),
    PRIMARY KEY (id)
) COMMENT 'Armazena os recebimentos realizados';

DROP TABLE IF EXISTS turmas;
CREATE TABLE IF NOT EXISTS turmas (
	id 				MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Armazena o codigo sequencial da turma',
    descricao 		VARCHAR(40) NOT NULL COMMENT 'Informa a descrição da turma',
    vMensalidade	DECIMAL(10,2) NOT NULL COMMENT 'Informa o valor do recebimento',
    KEY idx_id (id),
    PRIMARY KEY (id)
) COMMENT 'Armazena as turmas do sistema';

DROP TABLE IF EXISTS turmasAlunos;
CREATE TABLE IF NOT EXISTS turmasAlunos (
	turma_id 		MEDIUMINT UNSIGNED NOT NULL COMMENT 'Armazena o codigo sequencial da turma',
    aluno_id 		MEDIUMINT UNSIGNED  NOT NULL COMMENT 'Informa a descrição da turma',
    KEY idx_turma_id (turma_id),
    KEY idx_aluno_id (aluno_id),
    PRIMARY KEY (turma_id, aluno_id),
    CONSTRAINT fk_turmasAlunos_turma FOREIGN KEY (turma_id) REFERENCES turmas (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_turmasAlunos_aluno FOREIGN KEY (aluno_id) REFERENCES alunos (id) ON UPDATE CASCADE ON DELETE CASCADE
) COMMENT 'Armazena as turmas do sistema';



