package view;

import model.Usuario;
import java.util.Scanner;

public class VisaoUsuario {
    private Scanner scanner;

    public VisaoUsuario() {
        this.scanner = new Scanner(System.in, "UTF-8");
    }

    public String[] leLogin() {
        System.out.println("\n-- LOGIN --");
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        return new String[]{email, senha};
    }

    public Usuario leUsuario() {
        System.out.println("\n-- NOVO USUÁRIO --");
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Crie uma senha: ");
        String senha = scanner.nextLine();
        System.out.print("Pergunta secreta (ex: nome do primeiro animal): ");
        String pergunta = scanner.nextLine();
        System.out.print("Resposta secreta: ");
        String resposta = scanner.nextLine();
        return new Usuario(nome, email, senha, pergunta, resposta);
    }
    
    public void mostraUsuario(Usuario u) {
        System.out.println("\n-- MEUS DADOS --");
        System.out.println(u.toString());
    }
}
