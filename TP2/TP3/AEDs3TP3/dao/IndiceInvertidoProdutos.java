package dao;

import base.ListaInvertida;
import base.ElementoLista;
import model.Produto;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IndiceInvertidoProdutos {
    private ListaInvertida listaInvertida;
    private ArquivoProduto arquivoProduto;
    
    // Lista de stop words em português
    private Set<String> stopWords;
    
    public IndiceInvertidoProdutos(ArquivoProduto arquivoProduto) throws Exception {
        this.arquivoProduto = arquivoProduto;
        
        // Garante que o diretório dados existe
        File dadosDir = new File("dados");
        if (!dadosDir.exists()) {
            dadosDir.mkdirs();
        }
        
        this.listaInvertida = new ListaInvertida(10, "dados/dicionario.produtos.db", "dados/blocos.produtos.db");
        inicializarStopWords();
    }
    
    private void inicializarStopWords() {
        stopWords = new HashSet<>(Arrays.asList(
            "de", "da", "do", "das", "dos", "em", "para", "com", "sem", "por", 
            "ao", "aos", "na", "no", "nas", "nos", "um", "uma", "uns", "umas",
            "o", "a", "os", "as", "e", "ou", "mas", "se", "que", "qual", "quais",
            "como", "onde", "quando", "porque", "entre", "sobre", "sob", "ante",
            "apos", "ate", "com", "contra", "desde", "em", "para", "perante",
            "por", "sem", "sob", "sobre", "tras", "este", "esta", "estes", "estas",
            "esse", "essa", "esses", "essas", "aquele", "aquela", "aqueles", "aquelas",
            "isto", "isso", "aquilo", "todo", "toda", "todos", "todas", "outro",
            "outra", "outros", "outras", "algum", "alguma", "alguns", "algumas",
            "nenhum", "nenhuma", "nenhuns", "nenhumas", "cada", "qualquer", "quaisquer",
            "quantos", "quantas", "quanto", "quanta", "tal", "tais", "varios", "varias",
            "poucos", "poucas", "muitos", "muitas", "certos", "certas", "demais",
            "tanto", "tanta", "tantos", "tantas", "quaisquer", "quaisqueres"
        ));
    }
    
    /**
     * Processa o texto removendo acentos, convertendo para minúsculas e removendo stop words
     */
    private List<String> processarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        // Remove acentos e converte para minúsculas
        texto = texto.toLowerCase()
                    .replaceAll("[áàâãä]", "a")
                    .replaceAll("[éèêë]", "e")
                    .replaceAll("[íìîï]", "i")
                    .replaceAll("[óòôõö]", "o")
                    .replaceAll("[úùûü]", "u")
                    .replaceAll("[ç]", "c")
                    .replaceAll("[^a-z0-9 ]", " ");
        
        // Divide em palavras
        String[] palavras = texto.split("\\s+");
        List<String> palavrasProcessadas = new ArrayList<>();
        
        for (String palavra : palavras) {
            palavra = palavra.trim();
            if (!palavra.isEmpty() && !stopWords.contains(palavra) && palavra.length() > 2) {
                palavrasProcessadas.add(palavra);
            }
        }
        
        return palavrasProcessadas;
    }
    
    /**
     * Calcula a frequência de cada termo no texto (TF)
     */
    private double calcularTF(String termo, List<String> palavras) {
        int frequencia = 0;
        for (String palavra : palavras) {
            if (palavra.equals(termo)) {
                frequencia++;
            }
        }
        return palavras.isEmpty() ? 0 : (double) frequencia / palavras.size();
    }
    
    /**
     * Indexa um produto no índice invertido
     */
    public void indexarProduto(Produto produto) throws Exception {
        if (produto == null || !produto.getAtivo()) {
            return;
        }
        
        List<String> termos = processarTexto(produto.getNome() + " " + produto.getDescricao());
        
        for (String termo : new HashSet<>(termos)) { // Remove duplicatas
            double tf = calcularTF(termo, termos);
            listaInvertida.create(termo, new ElementoLista(produto.getId(), (float) tf));
        }
    }
    
    /**
     * Remove um produto do índice invertido
     */
    public void removerProduto(Produto produto) throws Exception {
        if (produto == null) {
            return;
        }
        
        List<String> termos = processarTexto(produto.getNome() + " " + produto.getDescricao());
        
        for (String termo : new HashSet<>(termos)) {
            listaInvertida.delete(termo, produto.getId());
        }
    }
    
    /**
     * Atualiza a indexação de um produto
     */
    public void atualizarProduto(Produto produtoAntigo, Produto produtoNovo) throws Exception {
        removerProduto(produtoAntigo);
        indexarProduto(produtoNovo);
    }
    
    /**
     * Busca produtos por termos usando TFxIDF
     */
    public List<Produto> buscarPorTermos(String consulta) throws Exception {
        List<String> termosConsulta = processarTexto(consulta);
        if (termosConsulta.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Mapa para armazenar scores TFxIDF por produto
        Map<Integer, Double> scores = new HashMap<>();
        int totalProdutos = contarProdutosAtivos();
        
        for (String termo : termosConsulta) {
            ElementoLista[] elementos = listaInvertida.read(termo);
            if (elementos.length == 0) continue;
            
            // Calcula IDF para este termo
            double idf = Math.log((double) totalProdutos / elementos.length) + 1;
            
            for (ElementoLista elemento : elementos) {
                double tf = elemento.getFrequencia();
                double score = tf * idf;
                
                scores.merge(elemento.getId(), score, Double::sum);
            }
        }
        
        // Ordena por score (maior primeiro)
        List<Map.Entry<Integer, Double>> entries = new ArrayList<>(scores.entrySet());
        entries.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        
        // Recupera os produtos ordenados
        List<Produto> resultados = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : entries) {
            Produto produto = arquivoProduto.read(entry.getKey());
            if (produto != null && produto.getAtivo()) {
                resultados.add(produto);
            }
        }
        
        return resultados;
    }

    private int contarProdutosAtivos() throws Exception {
        // Usa o método existente do ArquivoProduto e filtra os ativos
        ArrayList<Produto> todosProdutos = arquivoProduto.readAllActiveSortedByName();
        return Math.max(todosProdutos.size(), 1); // Evita divisão por zero
    }
    
    /**
     * Reindexa todos os produtos
     */
    public void reindexarTodosProdutos() throws Exception {
        ArrayList<Produto> todosProdutos = arquivoProduto.readAllActiveSortedByName();
        
        for (Produto produto : todosProdutos) {
            indexarProduto(produto);
        }
    }
}