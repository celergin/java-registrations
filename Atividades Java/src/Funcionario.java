public class Funcionario {
    private String nome;
    private String dataAdmissao;
    private float salario;

    public Funcionario(String nome, String dataAdmissao, float salario) {
        this.nome = nome;
        this.dataAdmissao = dataAdmissao;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public String getDataAdmissao() {
        return dataAdmissao;
    }
    public float getSalario() {
        return salario;
    }
}
