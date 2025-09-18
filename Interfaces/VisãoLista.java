// Arquivo: VisaoLista.java
package Interfaces;
import java.util.Scanner;
import Props.Lista;
import java.util.Date;

public class VisaoLista {
    
    public static Lista leLista(Scanner entrada, int idUsuario) {
        try {
            System.out.print("Nome da lista: ");
            String nome = entrada.nextLine();
            
            System.out.print("Descrição: ");
            String descricao = entrada.nextLine();
            
            System.out.print("Data limite (dd/MM/yyyy) ou Enter para nenhuma: ");
            String dataLimiteStr = entrada.nextLine();
            
            long dataLimite = 0;
            if (!dataLimiteStr.trim().isEmpty()) {
                // Implementar conversão de data se necessário
                // dataLimite = Utils.parseDate(dataLimiteStr).getTime();
            }
            
            // Gerar código NanoID (implementação simplificada)
            String codigo = gerarNanoID();
            
            return new Lista(-1, idUsuario, nome, descricao, new Date().getTime(), dataLimite, codigo);
        } catch (Exception e) {
            System.out.println("Erro ao ler lista: " + e.getMessage());
            return null;
        }
    }
    
    public static void mostraLista(Lista lista) {
        if (lista != null) {
            System.out.println("CÓDIGO: " + lista.getCodigoCompartilhavel());
            System.out.println("NOME: " + lista.getNome());
            System.out.println("DESCRIÇÃO: " + lista.getDescricao());
            System.out.println("DATA DE CRIAÇÃO: " + new Date(lista.getDataCriacao()));
            if (lista.getDataLimite() > 0) {
                System.out.println("DATA LIMITE: " + new Date(lista.getDataLimite()));
            }
        }
    }
    
    public static String leCodigoLista(Scanner entrada) {
        System.out.print("Código da lista: ");
        return entrada.nextLine();
    }
    
    private static String gerarNanoID() {
        // Implementação simplificada do NanoID
        // Em uma implementação real, usaríamos uma biblioteca específica
        String caracteres = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * caracteres.length());
            sb.append(caracteres.charAt(index));
        }
        return sb.toString();
    }
}