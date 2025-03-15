import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void apagarConsole() throws InterruptedException, IOException{
        Scanner outroscanner = new Scanner(System.in);
        System.out.println("Insira algo para continuar");
        String texto = outroscanner.next();
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
        outroscanner.close();
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        CadastroPessoa cadastro = new CadastroPessoa();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("Menu:");
            System.out.println("1. Cadastrar uma Pessoa");
            System.out.println("2. Localizar uma Pessoa");
            System.out.println("3. Editar uma Pessoa");
            System.out.println("4. Excluir Pessoa");
            System.out.println("5. Imprimir Pessoas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite a idade: ");
                    int idade = scanner.nextInt();
                    cadastro.cadastrarPessoa(nome, idade);
                    System.out.println("Pessoa cadastrada com sucesso!");

                    apagarConsole();
                    break;
                case 2:
                    System.out.print("Digite o ID da pessoa: ");
                    int idLocalizar = scanner.nextInt();
                    Pessoa pessoaLocalizada = cadastro.localizarPessoa(idLocalizar);
                    if (pessoaLocalizada != null) {
                        System.out.println("Pessoa encontrada: " + pessoaLocalizada);
                    } else {
                        System.out.println("Pessoa não encontrada.");
                    }
                    
                    apagarConsole();
                    break;
                case 3:
                    System.out.print("Digite o ID da pessoa: ");
                    int idEditar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Digite o novo nome: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Digite a nova idade: ");
                    int novaIdade = scanner.nextInt();
                    if (cadastro.editarPessoa(idEditar, novoNome, novaIdade)) {
                        System.out.println("Pessoa editada com sucesso!");
                    } else {
                        System.out.println("Pessoa não encontrada.");
                    }

                    apagarConsole();
                    break;
                case 4:
                    System.out.print("Digite o ID da pessoa: ");
                    int idExcluir = scanner.nextInt();
                    if (cadastro.excluirPessoa(idExcluir)) {
                        System.out.println("Pessoa excluída com sucesso!");
                    } else {
                        System.out.println("Pessoa não encontrada.");
                    }
                    
                    apagarConsole();
                    break;
                case 5:
                    System.out.println("Lista de Pessoas:");
                    cadastro.imprimirPessoas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    apagarConsole();
                    break;
            }
        } while (opcao != 0);

        scanner.close();
    }
}
