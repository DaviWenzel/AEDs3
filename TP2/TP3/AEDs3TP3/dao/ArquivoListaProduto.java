package dao;

import base.Arquivo;
import model.ListaProduto;
import index.ArvoreBMais;
import index.ParIntInt;
import java.util.ArrayList;

public class ArquivoListaProduto extends Arquivo<ListaProduto> {

    private ArvoreBMais<ParIntInt> indiceListaProduto;
    private ArvoreBMais<ParIntInt> indiceProdutoLista;

    public ArquivoListaProduto(String nomeArquivo) throws Exception {
        super(nomeArquivo, ListaProduto.class.getConstructor());
        
        // Índice: (idLista, idListaProduto)
        indiceListaProduto = new ArvoreBMais<>(
            ParIntInt.class.getConstructor(),
            5,
            "dados/listaproduto.lista.idx.db"
        );
        
        // Índice: (idProduto, idListaProduto)
        indiceProdutoLista = new ArvoreBMais<>(
            ParIntInt.class.getConstructor(),
            5,
            "dados/listaproduto.produto.idx.db"
        );
    }

    @Override
    public int create(ListaProduto obj) throws Exception {
        int id = super.create(obj);
        
        // Cria entradas nos índices
        ParIntInt parLista = new ParIntInt(obj.getIdLista(), id);
        ParIntInt parProduto = new ParIntInt(obj.getIdProduto(), id);
        
        indiceListaProduto.create(parLista);
        indiceProdutoLista.create(parProduto);
        
        return id;
    }

    @Override
    public ListaProduto read(int id) throws Exception {
        return super.read(id);
    }

    /**
     * Busca todas as associações de uma lista com produtos.
     * Utiliza busca direta no arquivo para contornar problemas de índice B+
     * na ArvoreBMais 
     */
    public ArrayList<ListaProduto> readByLista(int idLista) throws Exception {
        ArrayList<ListaProduto> resultados = new ArrayList<>();
        
        // Busca direta no arquivo 
        file.seek(4); 
        
        while (file.getFilePointer() < file.length()) {
            char lapide = (char) file.readByte();
            short tam = file.readShort();
            byte[] ba = new byte[tam];
            file.readFully(ba);

            if (lapide == ' ') {
                ListaProduto lp = constructor.newInstance();
                lp.fromByteArray(ba);
                
                // Se o produto pertence à lista desejada
                if (lp.getIdLista() == idLista) {
                    resultados.add(lp);
                }
            }
        }
        
        return resultados;
    }

    @Override
    public boolean delete(int id) throws Exception {
        ListaProduto lp = super.read(id);
        if (lp != null) {
            // Remove dos índices antes de deletar do arquivo principal
            indiceListaProduto.delete(new ParIntInt(lp.getIdLista(), id));
            indiceProdutoLista.delete(new ParIntInt(lp.getIdProduto(), id));
            
            return super.delete(id);
        }
        return false;
    }

    @Override
    public boolean update(ListaProduto obj) throws Exception {
        // Lê o objeto antigo para remover dos índices
        ListaProduto antigo = super.read(obj.getId());
        if (antigo != null) {
            indiceListaProduto.delete(new ParIntInt(antigo.getIdLista(), obj.getId()));
            indiceProdutoLista.delete(new ParIntInt(antigo.getIdProduto(), obj.getId()));
        }
        
        // Atualiza o objeto
        boolean sucesso = super.update(obj);
        
        // Adiciona com os novos valores aos índices
        if (sucesso) {
            indiceListaProduto.create(new ParIntInt(obj.getIdLista(), obj.getId()));
            indiceProdutoLista.create(new ParIntInt(obj.getIdProduto(), obj.getId()));
        }
        
        return sucesso;
    }

    /**
     * Busca todas as associações de um produto com listas usando índice B+
     */
    public ArrayList<ListaProduto> readByProduto(int idProduto) throws Exception {
        ArrayList<ListaProduto> resultados = new ArrayList<>();
        
        // Busca no índice por todos os idListaProduto associados ao idProduto
        ArrayList<ParIntInt> pares = indiceProdutoLista.read(new ParIntInt(idProduto, -1));
        
        for (ParIntInt par : pares) {
            if (par.getNum1() == idProduto) {
                ListaProduto lp = super.read(par.getNum2());
                if (lp != null) {
                    resultados.add(lp);
                }
            }
        }
        
        return resultados;
    }

    /**
     * Busca uma associação específica entre lista e produto usando índices
     */
    public ListaProduto readByListaAndProduto(int idLista, int idProduto) throws Exception {
        // Busca no índice de lista
        ArrayList<ParIntInt> paresLista = indiceListaProduto.read(new ParIntInt(idLista, -1));
        
        for (ParIntInt parLista : paresLista) {
            if (parLista.getNum1() == idLista) {
                ListaProduto lp = super.read(parLista.getNum2());
                if (lp != null && lp.getIdProduto() == idProduto) {
                    return lp;
                }
            }
        }
        
        return null;
    }

    /**
     * Remove todas as associações de uma lista (útil quando excluindo uma lista)
     */
    public boolean deleteByLista(int idLista) throws Exception {
        ArrayList<ListaProduto> associacoes = readByLista(idLista);
        boolean sucesso = true;
        
        for (ListaProduto lp : associacoes) {
            if (!delete(lp.getId())) {
                sucesso = false;
            }
        }
        
        return sucesso;
    }

    /**
     * Remove todas as associações de um produto 
     */
    public boolean deleteByProduto(int idProduto) throws Exception {
        ArrayList<ListaProduto> associacoes = readByProduto(idProduto);
        boolean sucesso = true;
        
        for (ListaProduto lp : associacoes) {
            if (!delete(lp.getId())) {
                sucesso = false;
            }
        }
        
        return sucesso;
    }
}