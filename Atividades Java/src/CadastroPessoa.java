import java.util.ArrayList;
import java.util.List;

public class CadastroPessoa {
    private List<Pessoa> pessoas;
    private int proximoId;

    public CadastroPessoa() {
        this.pessoas = new ArrayList<>();
        this.proximoId = 1;
    }

    public void cadastrarPessoa(String nome, int idade) {
        Pessoa Pessoa = new Pessoa(proximoId, nome, idade);
        pessoas.add(Pessoa);
        proximoId++;
    }

    public Pessoa localizarPessoa(int id) {
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getId() == id) {
                return pessoa;
            }
        }
        return null;
    }

    public boolean editarPessoa(int id, String novoNome, int novaIdade) {
        Pessoa pessoa = localizarPessoa(id);
        if (pessoa != null) {
            pessoa.setNome(novoNome);
            pessoa.setIdade(novaIdade);
            return true;
        }
        return false;
    }

    public boolean excluirPessoa(int id) {
        Pessoa pessoa = localizarPessoa(id);
        if (pessoa != null) {
            pessoas.remove(pessoa);
            return true;
        }
        return false;
    }

    public void imprimirPessoas() {
        for (Pessoa pessoa : pessoas) {
            System.out.println(pessoa);
        }
    }
}
