import java.util.ArrayList;

public class Empresa {
    private String nome;
    private String cnpj;
    private ArrayList<Departamento> departamentos;

    public Empresa(String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.departamentos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public ArrayList<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void adicionarDepartamento(Departamento departamento) {
        if (this.departamentos.size() < 10) {
            this.departamentos.add(departamento);
        } else {
            System.out.println("A empresa já possui 10 departamentos.");
        }
    }
    public void AumentoFuncionarios(){

    }

    public static void main(String[] args) {

        Funcionario f1 = new Funcionario("Alice", "2023-01-01", 1500);
        Funcionario f2 = new Funcionario("Bob", "2023-02-01", 1450);

        
        Departamento d1 = new Departamento("Recursos Humanos");
        d1.adicionarFuncionario(f1);
        d1.adicionarFuncionario(f2);

        Empresa e1 = new Empresa("Minha Empresa Ltda", "12.345.678/0001-99");
        e1.adicionarDepartamento(d1);

        System.out.println("Empresa: " + e1.getNome() + " | CNPJ: " + e1.getCnpj());
        for (Departamento d : e1.getDepartamentos()) {
            System.out.println("Departamento: " + d.getNome());
            for (Funcionario f : d.getFuncionarios()) {
                System.out.println("  Funcionário: " + f.getNome() + " | Data de Admissão: " + f.getDataAdmissao());
            }
        }
    }
}