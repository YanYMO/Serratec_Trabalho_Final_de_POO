public class FuncionarioException extends RuntimeException {
    private String mensagem;

    public FuncionarioException(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
