import java.util.Scanner;

public class MenuUsuarios {
    
    private ArquivoUsuario arqUsuarios;
    private static Scanner console = new Scanner(System.in);
    private Usuario usuarioLogado;

    public MenuUsuarios() throws Exception {
        arqUsuarios = new ArquivoUsuario();
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public boolean fazerLogin() {
        System.out.println("\nLogin");
        System.out.println("-----");
        
        System.out.print("E-mail: ");
        String email = console.nextLine();
        
        System.out.print("Senha: ");
        String senha = console.nextLine();

        try {
            Usuario usuario = arqUsuarios.read(email);
            if (usuario != null && usuario.verificarSenha(senha)) {
                usuarioLogado = usuario;
                System.out.println("Login realizado com sucesso!");
                return true;
            } else {
                System.out.println("E-mail ou senha inválidos!");
                return false;
            }
        } catch(Exception e) {
            System.out.println("Erro ao fazer login: " + e.getMessage());
            return false;
        }
    }

    public void novoUsuario() {
        System.out.println("\nNovo Usuário");
        System.out.println("------------");
        
        System.out.print("Nome: ");
        String nome = console.nextLine();
        
        System.out.print("E-mail: ");
        String email = console.nextLine();
        
        System.out.print("Senha: ");
        String senha = console.nextLine();
        
        System.out.print("Pergunta secreta (para recuperação de senha): ");
        String pergunta = console.nextLine();
        
        System.out.print("Resposta secreta: ");
        String resposta = console.nextLine();

        try {
            // Verifica se o email já existe
            if (arqUsuarios.read(email) != null) {
                System.out.println("E-mail já cadastrado!");
                return;
            }

            Usuario novoUsuario = new Usuario(-1, nome, email, senha, pergunta, resposta);
            arqUsuarios.create(novoUsuario);
            System.out.println("Usuário cadastrado com sucesso!");

        } catch(Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public void recuperarSenha() {
        System.out.println("\nRecuperação de Senha");
        System.out.println("-------------------");
        
        System.out.print("E-mail: ");
        String email = console.nextLine();

        try {
            Usuario usuario = arqUsuarios.read(email);
            if (usuario != null) {
                System.out.println("Pergunta secreta: " + usuario.perguntaSecreta);
                System.out.print("Resposta: ");
                String resposta = console.nextLine();

                if (usuario.verificarRespostaSecreta(resposta)) {
                    System.out.print("Nova senha: ");
                    String novaSenha = console.nextLine();
                    
                    // Atualiza a senha
                    usuario.hashSenha = usuario.hashSenha(novaSenha);
                    arqUsuarios.update(usuario);
                    System.out.println("Senha alterada com sucesso!");
                } else {
                    System.out.println("Resposta incorreta!");
                }
            } else {
                System.out.println("Usuário não encontrado!");
            }
        } catch(Exception e) {
            System.out.println("Erro ao recuperar senha: " + e.getMessage());
        }
    }

    public void menuUsuarios() {
        int opcao;
        do {
            System.out.println("\nGestão de Usuários");
            System.out.println("-----------------");
            System.out.println("1 - Listar usuários");
            System.out.println("2 - Alterar usuário");
            System.out.println("3 - Excluir usuário");
            System.out.println("4 - Recuperar senha");
            System.out.println("0 - Voltar");

            System.out.print("\nOpção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch(NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    listarUsuarios();
                    break;
                case 2:
                    alterarUsuario();
                    break;
                case 3:
                    excluirUsuario();
                    break;
                case 4:
                    recuperarSenha();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    private void listarUsuarios() {
        // Implementação para listar usuários 
        System.out.println("Funcionalidade de listagem de usuários ainda não implementada.");
    }

    private void alterarUsuario() {
        // Implementação para alterar usuário
        System.out.println("Funcionalidade de alteração de usuário ainda não implementada.");
    }

    private void excluirUsuario() {
        // Implementação para excluir usuário
        System.out.println("Funcionalidade de exclusão de usuário ainda não implementada.");
    }
}