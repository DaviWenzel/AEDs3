// Arquivo: VisaoUsuario.java
package Interfaces;
import java.util.Scanner;
import Props.Usuario;
import DataBase.SaltedHash;

public class VisaoUsuario {
    
    public static Usuario leUsuario(Scanner entrada) {
        try {
            System.out.print("Nome: ");
            String nome = entrada.nextLine();
            
            System.out.print("Email: ");
            String email = entrada.nextLine();
            
            System.out.print("Senha: ");
            String senha = entrada.nextLine();
            
            System.out.print("Pergunta secreta: ");
            String pergunta = entrada.nextLine();
            
            System.out.print("Resposta secreta: ");
            String resposta = entrada.nextLine();
            
            // Gerar hash da senha com salt
            SaltedHash sh = new SaltedHash(senha);
            
            return new Usuario(-1, nome, email, sh.get_passwd(), sh.get_salt(), pergunta, resposta);
        } catch (Exception e) {
            System.out.println("Erro ao ler usuário: " + e.getMessage());
            return null;
        }
    }
    
    public static void mostraUsuario(Usuario usuario) {
        if (usuario != null) {
            System.out.println("ID: " + usuario.getID());
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("Email: " + usuario.getEmail());
            System.out.println("Pergunta secreta: " + usuario.getPerguntaSecreta());
        }
    }
    
    public static String leEmail(Scanner entrada) {
        System.out.print("Email: ");
        return entrada.nextLine();
    }
    
    public static String leSenha(Scanner entrada) {
        System.out.print("Senha: ");
        return entrada.nextLine();
    }
}