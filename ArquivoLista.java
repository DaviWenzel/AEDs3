import aed3.Arquivo;
import aed3.HashExtensivel;
import aed3.BPlusTree;
import aed3.Registro;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class ArquivoLista extends Arquivo<Lista> {

    HashExtensivel<ParNanoIDID> indiceIndiretoNanoID;
    BPlusTree<Integer, Integer> arvoreUsuarioLista;

    public ArquivoLista() throws Exception {
        super("listas", Lista.class.getConstructor());
        
        indiceIndiretoNanoID = new HashExtensivel<>(
            ParNanoIDID.class.getConstructor(), 
            4, 
            ".\\dados\\listas\\indiceNanoID.d.db", 
            ".\\dados\\listas\\indiceNanoID.c.db"
        );

        arvoreUsuarioLista = new BPlusTree<>(
            Integer.class.getConstructor(), 
            Integer.class.getConstructor(), 
            new File("dados/listas/listasPorUsuario.db")
        );
    }
    
    public ArrayList<Lista> readListasByUsuario(int idUsuario) throws Exception {
        ArrayList<Integer> ids = arvoreUsuarioLista.readAll(idUsuario);
        ArrayList<Lista> listas = new ArrayList<>();
        if (ids != null) {
            for (int id : ids) {
                listas.add(super.read(id));
            }
        }
        return listas;
    }
}