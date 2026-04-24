package br.com.FolhaPagamento.interfac;

import br.com.FolhaPagamento.model.Funcionario;

public interface Incluir {

    public void adicionarFolhaPagamento(Funcionario funcionario);

    public void adicionarDependente(String nome, String cpf, String dataNascimento, String parentesco);
}