import java.time.LocalDate;

import aed3.Registro;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Lista implements Registro {

    public int id;
    private String nome;
    private String descricaoDetalhada;
    private LocalDate dataCriacao;
    private LocalDate dataLimite; 
    private String codigoCompartilhavel; // NanoID
    private int idUsuario; // Chave estrangeira

    public Lista() {
        this.id = -1;
    }

    public Lista(String nome, String descricaoDetalhada, LocalDate dataLimite, int idUsuario, String codigoCompartilhavel) {
        this.id = -1;
        this.nome = nome;
        this.descricaoDetalhada = descricaoDetalhada;
        this.dataCriacao = LocalDate.now();
        this.dataLimite = dataLimite;
        this.idUsuario = idUsuario;
        this.codigoCompartilhavel = codigoCompartilhavel;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricaoDetalhada() {
        return descricaoDetalhada;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public String getCodigoCompartilhavel() {
        return codigoCompartilhavel;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setDescricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
    }
    
    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    @Override
    public String toString() {
        return "\nID...............: " + this.id +
               "\nNome.............: " + this.nome +
               "\nDescrição........: " + this.descricaoDetalhada +
               "\nData de Criação..: " + this.dataCriacao +
               "\nData Limite......: " + (this.dataLimite != null ? this.dataLimite : "Não definida") +
               "\nCódigo...........: " + this.codigoCompartilhavel;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.descricaoDetalhada);
        dos.writeLong(this.dataCriacao.toEpochDay());
        
        boolean temDataLimite = this.dataLimite != null;
        dos.writeBoolean(temDataLimite);
        if (temDataLimite) {
            dos.writeLong(this.dataLimite.toEpochDay());
        }
        
        dos.writeUTF(this.codigoCompartilhavel);
        dos.writeInt(this.idUsuario);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.descricaoDetalhada = dis.readUTF();
        this.dataCriacao = LocalDate.ofEpochDay(dis.readLong());
        
        boolean temDataLimite = dis.readBoolean();
        if (temDataLimite) {
            this.dataLimite = LocalDate.ofEpochDay(dis.readLong());
        } else {
            this.dataLimite = null;
        }

        this.codigoCompartilhavel = dis.readUTF();
        this.idUsuario = dis.readInt();
    }
}
