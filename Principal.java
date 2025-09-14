import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        char opcao;

        try {
            MenuUsuarios menuUsuarios = new MenuUsuarios();
            
            do {
                System.out.println("\n\nPresenteFácil 1.0");
                System.out.println("-----------------");
                System.out.println("\n(1) Login");
                System.out.println("(2) Novo usuário");
                System.out.println("(S) Sair");

                System.out.print("\nOpção: ");
                opcao = console.nextLine().toUpperCase().charAt(0);

                switch (opcao) {
                    case '1':
                        if (menuUsuarios.fazerLogin()) {
                            menuPrincipal(menuUsuarios.getUsuarioLogado());
                        }
                        break;
                    case '2':
                        menuUsuarios.novoUsuario();
                        break;
                    case 'S':
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }

            } while (opcao != 'S');

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void menuPrincipal(Usuario usuarioLogado) {
        Scanner console = new Scanner(System.in);
        int opcao;

        try {
            do {
                System.out.println("\n\nPresenteFácil 1.0");
                System.out.println("-----------------");
                System.out.println("Usuário: " + usuarioLogado.getNome());
                System.out.println("\n1 - Clientes");
                System.out.println("2 - Usuários");
                System.out.println("0 - Logout");

                System.out.print("\nOpção: ");
                try {
                    opcao = Integer.valueOf(console.nextLine());
                } catch(NumberFormatException e) {
                    opcao = -1;
                }

                switch (opcao) {
                    case 1:
                        // Passe o usuário logado para o MenuClientes
                        (new MenuClientes(usuarioLogado)).menu();
                        break;
                    case 2:
                        (new MenuUsuarios()).menuUsuarios();
                        break;
                    case 0:
                        System.out.println("Logout realizado com sucesso!");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }

            } while (opcao != 0);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}