import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;


public class BibliotecaClient {
    public static void main(String[] args) {
        try {
            Registry registro = LocateRegistry.getRegistry("localhost", 1100);
            Biblioteca biblioteca = (Biblioteca) registro.lookup("MeuServico");

            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("\n--- Sistema Biblioteca ---");
                System.out.println("1. Listar livros disponíveis");
                System.out.println("2. Emprestar livro");
                System.out.println("3. Devolver livro");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        List<String> livros = biblioteca.listarLivros();
                        if (livros.isEmpty()) {
                            System.out.println("Nenhum livro disponível.");
                        } else {
                            System.out.println("Livros disponíveis:");
                            for (String livro : livros) {
                                System.out.println("- " + livro);
                            }
                        }
                        break;
                    case 2:
                        System.out.print("Digite o título do livro para emprestar: ");
                        String emprestar = scanner.nextLine();
                        if (biblioteca.emprestarLivro(emprestar)) {
                            System.out.println("Livro emprestado com sucesso!");
                        } else {
                            System.out.println("Livro indisponível ou não encontrado.");
                        }
                        break;
                    case 3:
                        System.out.print("Digite o título do livro para devolver: ");
                        String devolver = scanner.nextLine();
                        if (biblioteca.devolverLivro(devolver)) {
                            System.out.println("Livro devolvido com sucesso!");
                        } else {
                            System.out.println("Livro não encontrado ou já está disponível.");
                        }
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } while (opcao != 0);

            scanner.close();

        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}