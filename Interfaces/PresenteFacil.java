// Arquivo: PresenteFacil.java
import Interfaces.*;
import java.util.Scanner;

public class PresenteFacil {
    private ControleUsuario controleUsuario;
    private ControleLista controleLista;
    private int usuarioLogadoId;
    
    public PresenteFacil(String nomeArquivo) {
        try {
            controleUsuario = new ControleUsuario(nomeArquivo);
            controleLista = new ControleLista(nomeArquivo);
            usuarioLogadoId = -1;
        } catch (Exception e) {
            System.out.println("Erro ao inicializar sistema: " + e.getMessage());
        }
    }
    
    public void menuPrincipal() {
        Scanner entrada = new Scanner(System.in);
        int opcao;
        
        do {
            System.out.println("\nPresenteFácil 1.0");
            System.out.println("-----------------");
            System.out.println("> Início");
            System.out.println();
            System.out.println("(1) Login");
            System.out.println("(2) Novo usuário");
            System.out.println();
            System.out.println("(0) Sair");
            System.out.println();
            System.out.print("Opção: ");
            
            opcao = entrada.nextInt();
            entrada.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    usuarioLogadoId = controleUsuario.login(entrada);
                    if (usuarioLogadoId != -1) {
                        menuUsuarioLogado(entrada);
                    }
                    break;
                case 2:
                    controleUsuario.criarUsuario(entrada);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
        
        entrada.close();
    }
    
    private void menuUsuarioLogado(Scanner entrada) {
        int opcao;
        
        do {
            System.out.println("\nPresenteFácil 1.0");
            System.out.println("-----------------");
            System.out.println("> Início");
            System.out.println();
            System.out.println("(1) Meus dados");
            System.out.println("(2) Minhas listas");
            System.out.println("(3) Buscar lista");
            System.out.println();
            System.out.println("(0) Logout");
            System.out.println();
            System.out.print("Opção: ");
            
            opcao = entrada.nextInt();
            entrada.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    mostrarMeusDados();
                    break;
                case 2:
                    menuMinhasListas(entrada);
                    break;
                case 3:
                    buscarListaPorCodigo(entrada);
                    break;
                case 0:
                    System.out.println("Logout realizado.");
                    usuarioLogadoId = -1;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0 && usuarioLogadoId != -1);
    }
    
    private void mostrarMeusDados() {
        Usuario usuario = controleUsuario.buscarUsuario(usuarioLogadoId);
        if (usuario != null) {
            VisaoUsuario.mostraUsuario(usuario);
        }
    }
    
    private void menuMinhasListas(Scanner entrada) {
        int opcao;
        
        do {
            System.out.println("\nPresenteFácil 1.0");
            System.out.println("-----------------");
            System.out.println("> Início > Minhas listas");
            System.out.println();
            
            Lista[] listas = controleLista.listarListasUsuario(usuarioLogadoId);
            if (listas != null && listas.length > 0) {
                for (int i = 0; i < listas.length; i++) {
                    System.out.println("(" + (i+1) + ") " + listas[i].getNome());
                }
            } else {
                System.out.println("Nenhuma lista encontrada.");
            }
            
            System.out.println();
            System.out.println("(N) Nova lista");
            System.out.println("(R) Retornar ao menu anterior");
            System.out.println();
            System.out.print("Opção: ");
            
            String opcaoStr = entrada.nextLine();
            
            if (opcaoStr.equalsIgnoreCase("N")) {
                controleLista.criarLista(entrada, usuarioLogadoId);
            } else if (opcaoStr.equalsIgnoreCase("R")) {
                return;
            } else {
                try {
                    opcao = Integer.parseInt(opcaoStr);
                    if (opcao > 0 && opcao <= listas.length) {
                        menuDetalhesLista(entrada, listas[opcao-1]);
                    } else {
                        System.out.println("Opção inválida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Opção inválida.");
                }
            }
        } while (true);
    }
    
    private void menuDetalhesLista(Scanner entrada, Lista lista) {
        int opcao;
        
        do {
            System.out.println("\nPresenteFácil 1.0");
            System.out.println("-----------------");
            System.out.println("> Início > Minhas listas > " + lista.getNome());
            System.out.println();
            
            VisaoLista.mostraLista(lista);
            System.out.println();
            System.out.println("(1) Gerenciar produtos da lista");
            System.out.println("(2) Alterar dados da lista");
            System.out.println("(3) Excluir lista");
            System.out.println();
            System.out.println("(R) Retornar ao menu anterior");
            System.out.println();
            System.out.print("Opção: ");
            
            String opcaoStr = entrada.nextLine();
            
            if (opcaoStr.equalsIgnoreCase("R")) {
                return;
            } else {
                try {
                    opcao = Integer.parseInt(opcaoStr);
                    switch (opcao) {
                        case 1:
                            System.out.println("Funcionalidade disponível no TP2.");
                            break;
                        case 2:
                            System.out.println("Funcionalidade disponível no TP2.");
                            break;
                        case 3:
                            if (controleLista.excluirLista(lista.getID(), usuarioLogadoId)) {
                                return;
                            }
                            break;
                        default:
                            System.out.println("Opção inválida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Opção inválida.");
                }
            }
        } while (true);
    }
    
    private void buscarListaPorCodigo(Scanner entrada) {
        String codigo = VisaoLista.leCodigoLista(entrada);
        Lista lista = controleLista.buscarListaPorCodigo(codigo);
        if (lista != null) {
            System.out.println("Lista encontrada:");
            VisaoLista.mostraLista(lista);
        } else {
            System.out.println("Lista não encontrada.");
        }
    }
    
    public static void main(String[] args) {
        PresenteFacil sistema = new PresenteFacil("dados/presentefacil");
        sistema.menuPrincipal();
    }
}