create database folhapagamento;

create schema matriz;

create table matriz.funcionario (
idFuncionario uuid primary key,
nome varchar(150) not null,
cpf varchar(11) unique not null,
dataNascimento date not null,
salarioBruto float not null,
descontoINSS float not null,
descontoIR float not null
);

create type matriz.parentesco as enum ('FILHO', 'SOBRINHO', 'OUTROS');

create table matriz.dependente (
idDependente uuid primary key,
nome varchar(150) not null,
cpf varchar(11) unique not null,
dataNascimento date not null,
parentesco matriz.parentesco not null,
idFuncionario uuid not null references matriz.funcionario(idFuncionario)
);

create table matriz.folhaPagamento (
codigo uuid primary key,
dataPagamento date not null,
descontoINSS float not null,
descontoIR float not null,
salarioLiquido float not null,
idFuncionario uuid not null references matriz.funcionario(idFuncionario)
);