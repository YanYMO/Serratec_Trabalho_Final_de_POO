package br.com.FolhaPagamento.exception;

public class DependenteException extends RuntimeException {
    private String mensagem;

    public DependenteException(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
