package controller;

import dao.ArquivoProduto;
import model.Produto;
import view.VisaoProduto;
import java.util.ArrayList;
//conferir depois :)

public class ControleProduto {

    private ArquivoProduto arquivoProduto;
    private VisaoProduto visaoProduto;

    public ControleProduto(ArquivoProduto arquivoProduto) {
        this.arquivoProduto = arquivoProduto;
        this.visaoProduto = new VisaoProduto();
    }

    public void handleMenuProdutos() {
        String opcao;
        do {
            try {
                ArrayList<Produto> produtos = arquivoProduto.readAllActiveSortedByName();
                opcao = visaoProduto.menuProdutos(produtos);

                if (opcao.equalsIgnoreCase("N")) {
                    handleNovoProduto();
                } else if (!opcao.equalsIgnoreCase("R")) {
                    try {
                        int index = Integer.parseInt(opcao) - 1;
                        if (index >= 0 && index < produtos.size()) {
                            menuDetalhesProduto(produtos.get(index));
                        } else {
                            System.out.println("Opção inválida.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Opção inválida.");
                    }
                }
            } catch(Exception e) {
                System.err.println("ERRO: Falha ao gerenciar produtos.");
                e.printStackTrace();
                opcao = "R";
            }
        } while (!opcao.equalsIgnoreCase("R"));
    }

    private void handleNovoProduto() throws Exception {
        Produto novo = visaoProduto.lerProduto();

        if (!Produto.validaGtin13(novo.getGtin13())) {
            System.out.println("\nGTIN-13 inválido! Produto não cadastrado.");
            return;
        }

        try {
            arquivoProduto.create(novo);
            System.out.println("\nProduto '" + novo.getNome() + "' cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    private void menuDetalhesProduto(Produto produto) throws Exception {
        int opcao;
        do {
            opcao = visaoProduto.menuDetalhesProduto(produto);

            switch(opcao) {
                case 1:
                    handleEditarProduto(produto);
                    break;
                case 2:
                    handleExcluirProduto(produto);
                    opcao = 0; 
                    break;
                case 0: break;
                default:
                    System.out.println("\nOpção inválida.");
            }
        } while(opcao != 0);
    }

    private void handleEditarProduto(Produto produto) throws Exception {
        Produto atualizado = visaoProduto.editarProduto(produto);
        arquivoProduto.update(atualizado);
        System.out.println("\nProduto atualizado com sucesso!");
    }

    private void handleExcluirProduto(Produto produto) throws Exception {
        boolean confirmado = visaoProduto.confirmarExclusao(produto);
        if (confirmado) {
            arquivoProduto.delete(produto.getId());
            System.out.println("\nProduto excluído com sucesso!");
        } else {
            System.out.println("\nExclusão cancelada.");
        }
    }
}
