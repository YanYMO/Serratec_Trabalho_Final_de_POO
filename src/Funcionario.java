import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Funcionario extends Pessoa implements Impostos {
    private UUID idFuncionario;
    private Double salarioBruto;
    private Double descontoINSS;
    private Double descontoIR;
    private Double descontoD;
    private List<Dependente> dependente;
    private List<FolhaPagamento> folhaPagamento;
    private Integer quantidadeDependentes = 0;

    public Funcionario(String nome, String cpf, String dataNascimento, String salarioBruto) {
        super(nome, cpf, dataNascimento);
        this.idFuncionario = UUID.randomUUID();
        this.salarioBruto = Double.valueOf(salarioBruto);
        this.descontoINSS = 0.0;
        this.descontoD = 0.0;
        this.descontoIR = 0.0;
        this.dependente = new ArrayList<>();
        this.folhaPagamento = new ArrayList<>();
    }

    public void adicionarDependente(String nome, String cpf, String dataNascimento, String parentesco) {
        Dependente dependente = new Dependente(nome, cpf, dataNascimento, parentesco);
        this.dependente.add(dependente);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\nSeu contracheque ").append(getNome()).append("\n\n");
        sb.append("Salário bruto: ").append(String.format("%.2f", getSalarioBruto())).append("\n");
        sb.append("Desconto INSS: ").append(String.format("%.2f", getDescontoINSS())).append("\n");
        sb.append("Desconto IR: ").append(String.format("%.2f", getDescontoIR())).append("\n");
        sb.append("Desconto Dependente: ").append(String.format("%.2f", getDescontoD())).append("\n\n");
        sb.append("Salário líquido: ").append(String.format("%.2f", folhaPagamento.get(0).getSalarioLiquido())).append("\n");

        return sb.toString();
    }

    @Override
    public void adicionarFolhaPagamento(Funcionario funcionario) {
        funcionario.calculaImpostos();
        FolhaPagamento folhaPagamento = new FolhaPagamento(funcionario);
        folhaPagamento.calculaSalarioLiquido(funcionario);
        this.folhaPagamento.add(folhaPagamento);
    }

    @Override
    public void calculaImpostos() {
        calculaINSS();
        calculaD();
        calculaIR();
    }

    @Override
    public void calculaINSS() {
        if (salarioBruto <= 1518.0){
            descontoINSS = (salarioBruto * 0.075);
        } else if (salarioBruto > 1518.0 && salarioBruto < 2793.88){
            descontoINSS = (salarioBruto * 0.09) - 22.77;
        } else if (salarioBruto > 2793.88 && salarioBruto < 4190.83){
            descontoINSS = (salarioBruto * 0.12) - 109.60;
        } else if (salarioBruto > 4190.83 && salarioBruto < 8157.41){
            descontoINSS = (salarioBruto * 0.14) - 190.42;
        } else {
            descontoINSS = 951.62;
        }
    }

    @Override
    public void calculaD() {
        for (Dependente dependentes : dependente) {
            quantidadeDependentes++;
        }
        this.descontoD = quantidadeDependentes * 189.59;
    }

    @Override
    public void calculaIR() {
        double baseDeCalculoIR = salarioBruto - descontoD - descontoINSS;

        if ((baseDeCalculoIR) <= 2259.0){
           descontoIR = (baseDeCalculoIR);
        } else if ((baseDeCalculoIR) > 2259.0 && (baseDeCalculoIR) < 2826.65) {
            descontoIR = ((baseDeCalculoIR) * 0.075) - 169.44;
        } else if ((baseDeCalculoIR) > 2826.65 && (baseDeCalculoIR) < 3751.05) {
            descontoIR = ((baseDeCalculoIR) * 0.15) - 381.44;
        } else if ((baseDeCalculoIR) > 3751.05 && (baseDeCalculoIR) < 4664.68) {
            descontoIR = ((baseDeCalculoIR) * 0.225) - 662.77;
        } else if ((baseDeCalculoIR) > 4664.68) {
            descontoIR = ((baseDeCalculoIR) * 0.275) - 896.0;
        }
    }

    public UUID getIdFuncionario() {
        return idFuncionario;
    }

    public Double getSalarioBruto() {
        return salarioBruto;
    }

    public void setSalarioBruto(Double salarioBruto) {
        this.salarioBruto = salarioBruto;
    }

    public Double getDescontoINSS() {
        return descontoINSS;
    }

    public void setDescontoINSS(Double descontoINSS) {
        this.descontoINSS = descontoINSS;
    }

    public Double getDescontoIR() {
        return descontoIR;
    }

    public void setDescontoIR(Double descontoIR) {
        this.descontoIR = descontoIR;
    }

    public Double getDescontoD() {
        return descontoD;
    }

    public void setDescontoD(Double descontoD) {
        this.descontoD = descontoD;
    }

    public List<Dependente> getDependente() {
        return dependente;
    }

    public List<FolhaPagamento> getFolhaPagamento() {
        return folhaPagamento;
    }

    public Integer getQuantidadeDependentes() {
        return quantidadeDependentes;
    }
}