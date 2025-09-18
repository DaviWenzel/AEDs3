// Arquivo: Lista.java
package Props;
import java.io.*;
import java.util.Date;

public class Lista implements Registro {
    private int idLista;
    private int idUsuario;
    private String nome;
    private String descricao;
    private long dataCriacao;
    private long dataLimite;
    private String codigoCompartilhavel;

    public Lista() {
        this(-1, -1, "", "", 0, 0, "");
    }

    public Lista(int id, int idUser, String n, String d, long dc, long dl, String cc) {
        idLista = id;
        idUsuario = idUser;
        nome = n;
        descricao = d;
        dataCriacao = dc;
        dataLimite = dl;
        codigoCompartilhavel = cc;
    }

    public void setID(int id) {
        idLista = id;
    }

    public int getID() {
        return idLista;
    }

    public int getIDUsuario() {
        return idUsuario;
    }

    public String chaveSecundaria() {
        return codigoCompartilhavel;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public long getDataCriacao() {
        return dataCriacao;
    }

    public long getDataLimite() {
        return dataLimite;
    }

    public String getCodigoCompartilhavel() {
        return codigoCompartilhavel;
    }

    public void setCodigoCompartilhavel(String codigo) {
        codigoCompartilhavel = codigo;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(ba);
        data.writeInt(idLista);
        data.writeInt(idUsuario);
        data.writeUTF(nome);
        data.writeUTF(descricao);
        data.writeLong(dataCriacao);
        data.writeLong(dataLimite);
        data.writeUTF(codigoCompartilhavel);
        return ba.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream entrada = new ByteArrayInputStream(ba);
        DataInputStream data = new DataInputStream(entrada);
        idLista = data.readInt();
        idUsuario = data.readInt();
        nome = data.readUTF();
        descricao = data.readUTF();
        dataCriacao = data.readLong();
        dataLimite = data.readLong();
        codigoCompartilhavel = data.readUTF();
    }

    public String toString() {
        return nome;
    }
}