import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import aed3.Registro;

public class MenuLista {

    private ArquivoLista arquivoLista;
    private Scanner sc;
    private int idUsuarioAtivo;

    public MenuLista(int idUsuarioAtivo) throws Exception {
        this.idUsuarioAtivo = idUsuarioAtivo;
        this.arquivoLista = new ArquivoLista();
        this.sc = new Scanner(System.in);
    }

    public void exibirMenu() {
        char opcao;
        do {
            try {
                ArrayList<Lista> listas = arquivoLista.readListasByUsuario(idUsuarioAtivo);
                
                System.out.println("PresenteFácil 1.0");
                System.out.println("-----------------");
                System.out.println("> Início > Minhas listas\n");
                System.out.println("LISTAS");

                Collections.sort(listas, Comparator.comparing(Lista::getNome));

                if (listas.isEmpty()) {
                    System.out.println("Nenhuma lista encontrada.");
                } else {
                    for (int i = 0; i < listas.size(); i++) {
                        Lista lista = listas.get(i);
                        System.out.println("(" + (i + 1) + ") " + lista.getNome() + " - " + lista.getDataCriacao());
                    }
                }

                System.out.println("\n(N) Nova lista");
                System.out.println("(R) Retornar ao menu anterior");
                System.out.print("\nOpção: ");
                opcao = sc.next().toUpperCase().charAt(0);
                sc.nextLine(); 

                if (Character.isDigit(opcao)) {
                    int index = Character.getNumericValue(opcao) - 1;
                    if (index >= 0 && index < listas.size()) {
                        gerenciarLista(listas.get(index));
                    } else {
                        System.out.println("Opção inválida.");
                    }
                } else if (opcao == 'N') {
                    criarNovaLista();
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                opcao = ' ';
            }
        } while (opcao != 'R');
    }

    private void criarNovaLista() {
        try {
            System.out.println("\n--- Nova Lista ---");
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("Descrição: ");
            String descricao = sc.nextLine();
            String nanoId = "tdfd9as8bp"; 

            Lista novaLista = new Lista(nome, descricao, null, idUsuarioAtivo, nanoId);
            arquivoLista.create(novaLista);
            System.out.println("Lista '" + nome + "' criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar lista: " + e.getMessage());
        }
    }

    private void gerenciarLista(Lista lista) {
        char opcao;
        do {
            System.out.println("\nPresenteFácil 1.0");
            System.out.println("-----------------");
            System.out.println("> Início > Minhas listas > " + lista.getNome() + "\n");
            
            System.out.println(lista.toString());
            System.out.println("\n(1) Gerenciar produtos da lista");
            System.out.println("(2) Alterar dados da lista");
            System.out.println("(3) Excluir lista");
            System.out.println("\n(R) Retornar ao menu anterior");
            System.out.print("\nOpção: ");
            opcao = sc.next().toUpperCase().charAt(0);
            sc.nextLine();

            try {
                switch (opcao) {
                    case '1':
                        System.out.println("Funcionalidade de produtos será implementada no TP2.");
                        break;
                    case '2':
                        break;
                    case '3':
                        if (arquivoLista.delete(lista.getId())) {
                            System.out.println("Lista excluída com sucesso.");
                            opcao = 'R'; 
                        }
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro na operação: " + e.getMessage());
            }
        } while (opcao != 'R');
    }
}