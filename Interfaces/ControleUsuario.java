// Arquivo: ControleUsuario.java
package Interfaces;
import java.util.Scanner;
import Props.Usuario;
import DataBase.CRUD;
import DataBase.SaltedHash;
import java.util.Arrays;

public class ControleUsuario {
    private CRUD<Usuario> crudUsuario;
    private ArvoreBMais_String_Int indiceEmail;
    
    public ControleUsuario(String nomeArquivo) throws Exception {
        crudUsuario = new CRUD<>(nomeArquivo + "Usuario", Usuario.class.getConstructor());
        indiceEmail = new ArvoreBMais_String_Int(10, "dados/email_" + nomeArquivo + ".idx");
    }
    
    public int login(Scanner entrada) {
        try {
            String email = VisaoUsuario.leEmail(entrada);
            String senha = VisaoUsuario.leSenha(entrada);
            
            // Buscar usuário por email
            int idUsuario = indiceEmail.read(email);
            if (idUsuario == -1) {
                System.out.println("Usuário não encontrado.");
                return -1;
            }
            
            Usuario usuario = crudUsuario.read(idUsuario);
            if (usuario == null) {
                System.out.println("Erro ao carregar usuário.");
                return -1;
            }
            
            // Verificar senha
            SaltedHash sh = new SaltedHash(senha, usuario.getSalt());
            if (Arrays.equals(usuario.getHashSenha(), sh.get_passwd())) {
                System.out.println("Login realizado com sucesso!");
                return idUsuario;
            } else {
                System.out.println("Senha incorreta.");
                return -1;
            }
        } catch (Exception e) {
            System.out.println("Erro no login: " + e.getMessage());
            return -1;
        }
    }
    
    public boolean criarUsuario(Scanner entrada) {
        try {
            Usuario novoUsuario = VisaoUsuario.leUsuario(entrada);
            if (novoUsuario == null) {
                return false;
            }
            
            // Verificar se email já existe
            if (indiceEmail.read(novoUsuario.getEmail()) != -1) {
                System.out.println("Email já cadastrado.");
                return false;
            }
            
            int id = crudUsuario.create(novoUsuario);
            if (id != -1) {
                indiceEmail.create(novoUsuario.getEmail(), id);
                System.out.println("Usuário criado com ID: " + id);
                return true;
            } else {
                System.out.println("Erro ao criar usuário.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erro ao criar usuário: " + e.getMessage());
            return false;
        }
    }
    
    public Usuario buscarUsuario(int id) {
        try {
            return crudUsuario.read(id);
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
            return null;
        }
    }
}