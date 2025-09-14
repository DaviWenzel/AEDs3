import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParEmailID implements aed3.RegistroHashExtensivel<ParEmailID> {
    
    private String email; // chave
    private int id;       // valor
    private final short TAMANHO = 100;  // tamanho em bytes

    public ParEmailID() {
        this.email = "";
        this.id = -1;
    }

    public ParEmailID(String email, int id) {
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return hash(this.email);
    }

    public short size() {
        return this.TAMANHO;
    }

    public String toString() {
        return "(" + this.email + ";" + this.id + ")";
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(this.email);
        dos.writeInt(this.id);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.email = dis.readUTF();
        this.id = dis.readInt();
    }

    public static int hash(String email) {
        return Math.abs(email.hashCode());
    }
}