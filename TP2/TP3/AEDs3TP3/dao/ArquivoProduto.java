package dao;

import base.Arquivo;
import model.Produto;
import java.util.ArrayList;
import java.util.Comparator;

public class ArquivoProduto extends Arquivo<Produto> {
    private IndiceInvertidoProdutos indiceInvertido;

    public ArquivoProduto(String nomeArquivo) throws Exception {
        super(nomeArquivo, Produto.class.getConstructor());
        this.indiceInvertido = new IndiceInvertidoProdutos(this);
    }

    @Override
    public int create(Produto obj) throws Exception {
        Produto existente = readByGtin(obj.getGtin13());
        if (existente != null) {
            throw new Exception("GTIN já cadastrado (ID: " + existente.getId() + ")");
        }
        int id = super.create(obj);
        
        // Indexa o novo produto
        obj.setId(id);
        indiceInvertido.indexarProduto(obj);
        
        return id;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Produto p = super.read(id);
        if (p != null) {
            // Remove do índice invertido
            indiceInvertido.removerProduto(p);
            return super.delete(id);
        }
        return false;
    }

    @Override
    public boolean update(Produto obj) throws Exception {
        Produto antigo = super.read(obj.getId());
        boolean sucesso = super.update(obj);
        
        if (sucesso && antigo != null) {
            // Atualiza no índice invertido
            indiceInvertido.atualizarProduto(antigo, obj);
        }
        
        return sucesso;
    }

    public Produto readByGtin(String gtin) throws Exception {
        file.seek(4);
        while (file.getFilePointer() < file.length()) {
            char lapide = (char) file.readByte();
            short tam = file.readShort();
            byte[] ba = new byte[tam];
            file.readFully(ba);

            if (lapide == ' ') {
                Produto p = constructor.newInstance();
                p.fromByteArray(ba);
                if (p.getGtin13().equals(gtin)) {
                    return p;
                }
            }
        }
        return null;
    }

    public ArrayList<Produto> readAllActiveSortedByName() throws Exception {
        ArrayList<Produto> produtos = new ArrayList<>();

        file.seek(4);
        while (file.getFilePointer() < file.length()) {
            char lapide = (char) file.readByte();
            short tam = file.readShort();
            byte[] ba = new byte[tam];
            file.readFully(ba);

            if (lapide == ' ') {
                Produto p = constructor.newInstance();
                p.fromByteArray(ba);
                if (p.getAtivo()) produtos.add(p);
            }
        }

        produtos.sort(Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER));
        return produtos;
    }

    /**
     * Busca produtos por termos usando índice invertido
     */
    public ArrayList<Produto> buscarPorTermos(String termos) throws Exception {
        return new ArrayList<>(indiceInvertido.buscarPorTermos(termos));
    }
    
    public void inicializarIndice() throws Exception {

        java.io.File arquivoIndice = new java.io.File("dados/dicionario.produtos.db");
        if (!arquivoIndice.exists() || arquivoIndice.length() == 0) {
            System.out.println("Inicializando índice invertido...");
            indiceInvertido.reindexarTodosProdutos();
            System.out.println("Índice invertido inicializado com sucesso!");
        }
    }
}