// Arquivo: ControleLista.java
package Interfaces;
import java.util.Scanner;
import Props.Lista;
import DataBase.CRUD;
import DataBase.ArvoreBMais_String_Int;
import DataBase.ArvoreBMais_Int_Int;

public class ControleLista {
    private CRUD<Lista> crudLista;
    private ArvoreBMais_String_Int indiceCodigo;
    private ArvoreBMais_Int_Int indiceUsuario;
    
    public ControleLista(String nomeArquivo) throws Exception {
        crudLista = new CRUD<>(nomeArquivo + "Lista", Lista.class.getConstructor());
        indiceCodigo = new ArvoreBMais_String_Int(10, "dados/codigo_" + nomeArquivo + ".idx");
        indiceUsuario = new ArvoreBMais_Int_Int(10, "dados/usuario_lista_" + nomeArquivo + ".idx");
    }
    
    public boolean criarLista(Scanner entrada, int idUsuario) {
        try {
            Lista novaLista = VisaoLista.leLista(entrada, idUsuario);
            if (novaLista == null) {
                return false;
            }
            
            int id = crudLista.create(novaLista);
            if (id != -1) {
                indiceCodigo.create(novaLista.getCodigoCompartilhavel(), id);
                indiceUsuario.create(idUsuario, id);
                System.out.println("Lista criada com ID: " + id);
                return true;
            } else {
                System.out.println("Erro ao criar lista.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erro ao criar lista: " + e.getMessage());
            return false;
        }
    }
    
    public Lista[] listarListasUsuario(int idUsuario) {
        try {
            int[] ids = indiceUsuario.read(idUsuario);
            if (ids == null || ids.length == 0) {
                return null;
            }
            
            Lista[] listas = new Lista[ids.length];
            for (int i = 0; i < ids.length; i++) {
                listas[i] = crudLista.read(ids[i]);
            }
            return listas;
        } catch (Exception e) {
            System.out.println("Erro ao listar listas: " + e.getMessage());
            return null;
        }
    }
    
    public Lista buscarListaPorCodigo(String codigo) {
        try {
            int id = indiceCodigo.read(codigo);
            if (id == -1) {
                return null;
            }
            return crudLista.read(id);
        } catch (Exception e) {
            System.out.println("Erro ao buscar lista: " + e.getMessage());
            return null;
        }
    }
    
    public boolean excluirLista(int idLista, int idUsuario) {
        try {
            Lista lista = crudLista.read(idLista);
            if (lista == null || lista.getIDUsuario() != idUsuario) {
                System.out.println("Lista não encontrada ou não pertence ao usuário.");
                return false;
            }
            
            if (crudLista.delete(idLista)) {
                indiceCodigo.delete(lista.getCodigoCompartilhavel());
                indiceUsuario.delete(idUsuario, idLista);
                System.out.println("Lista excluída com sucesso.");
                return true;
            } else {
                System.out.println("Erro ao excluir lista.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir lista: " + e.getMessage());
            return false;
        }
    }
}