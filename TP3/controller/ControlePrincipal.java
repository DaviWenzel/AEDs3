package controller;

import view.VisaoPrincipal;
import view.VisaoUsuario;
import dao.ArquivoUsuario;
import dao.ArquivoLista;
import dao.ArquivoProduto;
import dao.ArquivoListaProduto;
import model.Usuario;
import model.Lista;
import model.Produto;
import model.ListaProduto;
import controller.ControleUsuario;
import controller.ControleLista;
import controller.ControleProduto;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class ControlePrincipal {
    private VisaoPrincipal visaoPrincipal;
    private VisaoUsuario visaoUsuario;
    private ArquivoUsuario arquivoUsuario;
    private ArquivoLista arquivoLista;
    private ArquivoProduto arquivoProduto;
    private ArquivoListaProduto arquivoListaProduto;
    private Usuario usuarioLogado;

    public ControlePrincipal() {
        try {
            this.visaoPrincipal = new VisaoPrincipal();
            this.visaoUsuario = new VisaoUsuario();
            this.arquivoUsuario = new ArquivoUsuario("dados/usuarios.db");
            this.arquivoLista = new ArquivoLista("dados/listas.db");
            this.arquivoProduto = new ArquivoProduto("dados/produtos.db");
            this.arquivoListaProduto = new ArquivoListaProduto("dados/listas_produtos.db");
            this.usuarioLogado = null;

            inicializarIndiceInvertido();
        } catch (Exception e) {
            System.err.println("ERRO: Falha ao inicializar o sistema.");
            e.printStackTrace();
        }
    }

    private void inicializarIndiceInvertido() {
        try {

            File arquivoProdutos = new File("dados/produtos.db");
            File arquivoIndice = new File("dados/dicionario.produtos.db");

            if (arquivoProdutos.exists() && (!arquivoIndice.exists() || arquivoIndice.length() == 0)) {
                System.out.println("Inicializando índice invertido...");
                

                ArrayList<Produto> produtos = arquivoProduto.readAllActiveSortedByName();
                for (Produto produto : produtos) {

                }
                System.out.println("Índice invertido inicializado com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Aviso: Não foi possível inicializar o índice invertido: " + e.getMessage());
        }
    }

    public void iniciar() {
        int opcao;
        do {
            opcao = visaoPrincipal.menuLogin();
            switch (opcao) {
                case 1:
                    handleLogin();
                    if (usuarioLogado != null) {
                        loopPrincipal();
                    }
                    break;

                case 2:
                    ControleUsuario cuNovo = new ControleUsuario(arquivoUsuario, arquivoLista, null);
                    cuNovo.handleNovoUsuario();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        System.out.println("\nSistema finalizado.");
    }

    private void handleLogin() {
        String[] dadosLogin = visaoUsuario.leLogin();
        try {
            Usuario u = arquivoUsuario.readByEmail(dadosLogin[0]);
            if (u != null && u.validarSenha(dadosLogin[1])) {
                this.usuarioLogado = u;
                System.out.println("\nLogin bem-sucedido! Bem-vindo(a), " + u.getNome() + ".");
            } else {
                System.out.println("\nE-mail ou senha inválidos.");

                // Oferecer recuperação se o usuário existe mas a senha está errada
                if (u != null) {
                    if (visaoUsuario.oferecerRecuperacao()) {
                        if (visaoUsuario.tentarRecuperacaoSenha(u)) {
                            // Atualiza o usuário no arquivo com a nova senha
                            arquivoUsuario.update(u);
                            System.out.println("Agora você pode fazer login com a nova senha.");
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("ERRO: Falha ao tentar realizar o login.");
            e.printStackTrace();
        }
    }

    private void loopPrincipal() {
        int opcao;
        do {
            opcao = visaoPrincipal.menuPrincipal();
            switch (opcao) {
                case 1: // Meus dados
                    ControleUsuario cu = new ControleUsuario(arquivoUsuario, arquivoLista, usuarioLogado);
                    cu.handleMenuDados();
                    if (usuarioLogado == null) opcao = 0; // se foi excluído
                    break;

                case 2: // Minhas listas
                    ControleLista cl = new ControleLista(usuarioLogado, arquivoLista, arquivoProduto, arquivoListaProduto);
                    cl.handleMenuListas();
                    break;

                case 3: // Produtos
                    ControleProduto cp = new ControleProduto(arquivoProduto, arquivoLista, arquivoListaProduto, usuarioLogado);
                    cp.handleMenuProdutos();
                    break;

                case 4: // Buscar lista por código
                    handleBuscarListaPorCodigo();
                    break;

                case 0:
                    this.usuarioLogado = null;
                    System.out.println("\nLogout efetuado com sucesso.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0 && usuarioLogado != null);
    }

    private void handleBuscarListaPorCodigo() {
        System.out.print("\nDigite o código compartilhável: ");
        String codigo = new Scanner(System.in).nextLine();
        try {
            Lista l = arquivoLista.readByCodigo(codigo);
            if (l != null) {
                // Buscar informações do dono da lista
                Usuario dono = arquivoUsuario.read(l.getIdUsuario());
                String nomeDono = (dono != null) ? dono.getNome() : "Usuário não encontrado";
                
                // Buscar produtos associados a esta lista usando índice B+
                ArrayList<ListaProduto> produtosNaLista = arquivoListaProduto.readByLista(l.getId());
                
                System.out.println("\n--- LISTA ENCONTRADA ---\n" + 
                    l.toStringComDono(nomeDono));
                
                // Mostrar produtos da lista
                if (produtosNaLista.isEmpty()) {
                    System.out.println("\nNenhum produto nesta lista.");
                } else {
                    System.out.println("\n--- PRODUTOS NA LISTA ---");
                    for (ListaProduto lp : produtosNaLista) {
                        Produto produto = arquivoProduto.read(lp.getIdProduto());
                        if (produto != null && produto.getAtivo()) {
                            System.out.println("\n• " + produto.getNome());
                            System.out.println("  Quantidade: " + lp.getQuantidade());
                            if (!lp.getObservacoes().isBlank()) {
                                System.out.println("  Observações: " + lp.getObservacoes());
                            }
                            System.out.println("  GTIN-13: " + produto.getGtin13());
                            System.out.println("  Descrição: " + produto.getDescricao());
                        }
                    }
                }
            } else {
                System.out.println("\nNenhuma lista encontrada com este código.");
            }
        } catch (Exception e) {
            System.err.println("ERRO: Falha ao buscar lista por código.");
            e.printStackTrace();
        }
    }
}