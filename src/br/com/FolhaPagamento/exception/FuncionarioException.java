package br.com.FolhaPagamento.exception;

public class FuncionarioException extends RuntimeException {
    private String mensagem;

    public FuncionarioException(String mensagem) {
        super(mensagem);
    }
}
