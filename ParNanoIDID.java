import aed3.RegistroHashExtensivel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParNanoIDID implements RegistroHashExtensivel<ParNanoIDID> {

    private String codigo; 
    private int id;       
    private final short TAMANHO = 100;

    public ParNanoIDID() {
        this("");
    }

    public ParNanoIDID(String codigo) {
        this.codigo = codigo;
        this.id = -1;
    }

    public ParNanoIDID(String codigo, int id) {
        this.codigo = codigo;
        this.id = id;
    }

    @Override
    public int hashCode() {
        return hash(this.codigo);
    }

    public static int hash(String codigo) {
        return Math.abs(codigo.hashCode());
    }

    public String getCodigo() {
        return codigo;
    }

    public int getId() {
        return id;
    }

    @Override
    public short size() {
        return this.TAMANHO;
    }

    @Override
    public String toString() {
        return "(" + this.codigo + ";" + this.id + ")";
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(this.codigo);
        dos.writeInt(this.id);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.codigo = dis.readUTF();
        this.id = dis.readInt();
    }
}