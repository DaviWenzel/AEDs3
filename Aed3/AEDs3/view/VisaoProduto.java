package view;

import model.Produto;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
//conferir depois :)

public class VisaoProduto {
    private Scanner scanner;

    public VisaoProduto() {
        this.scanner = new Scanner(System.in, "UTF-8");
    }

    private String formatarData(long timestamp) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(timestamp));
    }

    public String menuProdutos(ArrayList<Produto> produtos) {
        System.out.println("\nPresenteFácil 1.0");
        System.out.println("-----------------");
        System.out.println("> Início > Produtos\n");
        System.out.println("PRODUTOS");

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            for (int i = 0; i < produtos.size(); i++) {
                Produto p = produtos.get(i);
                System.out.println("(" + (i + 1) + ") " + p.getNome() + " - GTIN: " + p.getGtin13());
            }
        }

        System.out.println("\n(N) Novo produto");
        System.out.println("(R) Retornar ao menu anterior");
        System.out.print("\nOpção: ");
        return scanner.nextLine().toUpperCase();
    }

    public Produto lerProduto() {
        System.out.println("\n-- NOVO PRODUTO --");
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("GTIN-13: ");
        String gtin = scanner.nextLine();

        return new Produto(nome, descricao, gtin);
    }

    public int menuDetalhesProduto(Produto produto) {
        System.out.println("\nPresenteFácil 1.0");
        System.out.println("-----------------");
        System.out.println("> Início > Produtos > " + produto.getNome() + "\n");

        System.out.println(produto.toString());

        System.out.println("\n(1) Alterar dados do produto");
        System.out.println("(2) Excluir produto");
        System.out.println("\n(R) Retornar ao menu anterior");

        System.out.print("\nOpção: ");
        String opcao = scanner.nextLine().toUpperCase();
        if (opcao.equals("R")) return 0;
        try {
            return Integer.parseInt(opcao);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public Produto editarProduto(Produto produto) {
        System.out.println("\n-- EDITAR PRODUTO --");
        System.out.println("(deixe em branco para manter o valor atual)");

        System.out.println("Nome atual: " + produto.getNome());
        System.out.print("Novo nome: ");
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) produto.setNome(novoNome);

        System.out.println("Descrição atual: " + produto.getDescricao());
        System.out.print("Nova descrição: ");
        String novaDesc = scanner.nextLine();
        if (!novaDesc.isBlank()) produto.setDescricao(novaDesc);

        System.out.println("GTIN-13 atual: " + produto.getGtin13());
        System.out.print("Novo GTIN-13: ");
        String novoGtin = scanner.nextLine();
        if (!novoGtin.isBlank()) produto.setGtin13(novoGtin);

        System.out.println("Status atual: " + (produto.getAtivo() ? "Ativo" : "Inativo"));
        System.out.print("Deseja alterar o status? (S/N): ");
        String alteraStatus = scanner.nextLine().trim().toUpperCase();
        if (alteraStatus.equals("S")) produto.setAtivo(!produto.getAtivo());

        return produto;
    }

    public boolean confirmarExclusao(Produto produto) {
        System.out.println("\n-- EXCLUIR PRODUTO --");
        System.out.println("Você tem certeza que deseja excluir o produto '" + produto.getNome() + "'?");
        System.out.print("Digite 'S' para confirmar ou qualquer outra tecla para cancelar: ");
        String resp = scanner.nextLine().trim().toUpperCase();
        return resp.equals("S");
    }
}
