// Arquivo: Usuario.java
package Props;
import java.io.*;

public class Usuario implements Registro {
    private int idUsuario;
    private String nome;
    private String email;
    private byte[] hashSenha;
    private byte[] salt;
    private String perguntaSecreta;
    private String respostaSecreta;

    public Usuario() {
        this(-1, "", "", new byte[0], new byte[0], "", "");
    }

    public Usuario(int id, String n, String e, byte[] hs, byte[] s, String ps, String rs) {
        idUsuario = id;
        nome = n;
        email = e;
        hashSenha = hs;
        salt = s;
        perguntaSecreta = ps;
        respostaSecreta = rs;
    }

    public void setID(int id) {
        idUsuario = id;
    }

    public int getID() {
        return idUsuario;
    }

    public String chaveSecundaria() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getHashSenha() {
        return hashSenha;
    }

    public byte[] getSalt() {
        return salt;
    }

    public String getPerguntaSecreta() {
        return perguntaSecreta;
    }

    public String getRespostaSecreta() {
        return respostaSecreta;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(ba);
        data.writeInt(idUsuario);
        data.writeUTF(nome);
        data.writeUTF(email);
        data.writeInt(hashSenha.length);
        data.write(hashSenha);
        data.writeInt(salt.length);
        data.write(salt);
        data.writeUTF(perguntaSecreta);
        data.writeUTF(respostaSecreta);
        return ba.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream entrada = new ByteArrayInputStream(ba);
        DataInputStream data = new DataInputStream(entrada);
        idUsuario = data.readInt();
        nome = data.readUTF();
        email = data.readUTF();
        int hashLength = data.readInt();
        hashSenha = new byte[hashLength];
        data.readFully(hashSenha);
        int saltLength = data.readInt();
        salt = new byte[saltLength];
        data.readFully(salt);
        perguntaSecreta = data.readUTF();
        respostaSecreta = data.readUTF();
    }

    public String toString() {
        return nome + " (" + email + ")";
    }
}