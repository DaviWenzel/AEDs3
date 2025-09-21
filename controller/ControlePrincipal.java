package controller;

import view.VisaoPrincipal;
import view.VisaoUsuario;
import dao.ArquivoUsuario;
import dao.ArquivoLista;
import model.Usuario;
import model.Lista;

import java.util.Scanner;

public class ControlePrincipal {
    private VisaoPrincipal visaoPrincipal;
    private VisaoUsuario visaoUsuario;
    private ArquivoUsuario arquivoUsuario;
    private ArquivoLista arquivoLista;
    private Usuario usuarioLogado;

    public ControlePrincipal() {
        try {
            this.visaoPrincipal = new VisaoPrincipal();
            this.visaoUsuario = new VisaoUsuario();
            this.arquivoUsuario = new ArquivoUsuario("dados/usuarios.db");
            this.arquivoLista = new ArquivoLista("dados/listas.db");
            this.usuarioLogado = null;
        } catch(Exception e) {
            System.err.println("ERRO: Falha ao inicializar o sistema.");
            e.printStackTrace();
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
                case 0: break;
                default: System.out.println("Opção inválida.");
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
            }
        } catch(Exception e) {
            System.err.println("ERRO: Falha ao tentar realizar o login.");
            e.printStackTrace();
        }
    }

    private void loopPrincipal() {
        int opcao;
        do {
            opcao = visaoPrincipal.menuPrincipal();
            switch(opcao) {
                case 1: // Meus dados
                    ControleUsuario cu = new ControleUsuario(arquivoUsuario, arquivoLista, usuarioLogado);
                    cu.handleMenuDados();
                    if (usuarioLogado == null) opcao = 0; // se foi excluído
                    break;

                case 2: // Minhas listas
                    ControleLista cl = new ControleLista(usuarioLogado, arquivoLista);
                    cl.handleMenuListas();
                    break;

                case 3: 
                    System.out.println("\nFuncionalidade de Produtos será implementada no TP2.");
                    break;

                case 4: // Buscar lista
                    System.out.print("\nDigite o código compartilhável: ");
                    String codigo = new Scanner(System.in).nextLine();
                    try {
                        Lista l = arquivoLista.readByCodigo(codigo);
                        if (l != null) {
                            System.out.println("\n--- LISTA ENCONTRADA ---\n" + l.toString());
                        } else {
                            System.out.println("\nNenhuma lista encontrada com este código.");
                        }
                    } catch (Exception e) {
                        System.err.println("ERRO: Falha ao buscar lista por código.");
                        e.printStackTrace();
                    }
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
}

