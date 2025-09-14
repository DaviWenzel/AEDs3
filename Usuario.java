import aed3.Registro;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.security.MessageDigest;

public class Usuario implements Registro {

    public int id;
    public String nome;
    public String email;
    public String hashSenha;
    public String perguntaSecreta;
    public String respostaSecreta;

    public Usuario() {
        this(-1, "", "", "", "", "");
    }

    public Usuario(int id, String nome, String email, String senha, String perguntaSecreta, String respostaSecreta) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.hashSenha = hashSenha(senha);
        this.perguntaSecreta = perguntaSecreta;
        this.respostaSecreta = hashSenha(respostaSecreta);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public boolean verificarSenha(String senha) {
        return hashSenha(senha).equals(this.hashSenha);
    }

    public boolean verificarRespostaSecreta(String resposta) {
        return hashSenha(resposta).equals(this.respostaSecreta);
    }

    // Mudei de private para public
    public String hashSenha(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(texto.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return "\nID..............: " + this.id +
               "\nNome............: " + this.nome +
               "\nE-mail..........: " + this.email +
               "\nPergunta secreta: " + this.perguntaSecreta;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.email);
        dos.writeUTF(this.hashSenha);
        dos.writeUTF(this.perguntaSecreta);
        dos.writeUTF(this.respostaSecreta);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.email = dis.readUTF();
        this.hashSenha = dis.readUTF();
        this.perguntaSecreta = dis.readUTF();
        this.respostaSecreta = dis.readUTF();
    }
}