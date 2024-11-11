CREATE DATABASE gestao_financeira_estoque_glp;

USE gestao_financeira_estoque_glp;

-- Tabela de usuarios
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL
);

-- Tabela para controle de estoque
CREATE TABLE controle_estoque (
    id_transacao INT AUTO_INCREMENT PRIMARY KEY,
    id_produto INT,
    id_usuario INT,
    qtd_entrada INT,
    qtd_vendas INT,
    nome_fornecedor VARCHAR(100) NOT NULL,
    nome_produto VARCHAR(100) NOT NULL,
    data_transacao TIMESTAMP NOT NULL,
    valor_compra DECIMAL(10, 2),
    valor_venda DECIMAL(10, 2),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);
