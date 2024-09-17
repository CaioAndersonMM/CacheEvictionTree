import java.util.LinkedList;

public class TabelaHash {
    private static final int m = 30;
    private LinkedList<Entrada>[] tabela = new LinkedList[m]; // Array de linkedlists
    private int tamanho;
    private static final double alfaCarga = 0.75;

    public TabelaHash() {
        tabela = new LinkedList[m];
        tamanho = 0;

        // Cada indice do array é uma linkedlist pois pode haver colisões
        for (int i = 0; i < tabela.length; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    public void inserir(int codigo, OrdemServico ordem) {
        if ((double) tamanho / tabela.length >= alfaCarga) {
            redimensionar();
        }

        int indice = hash(codigo);
        LinkedList<Entrada> lista = tabela[indice];

        for (Entrada entrada : lista) {
            if (entrada.codigo == codigo) {
                entrada.ordem = ordem;
                return;
            }
        }

        lista.add(new Entrada(codigo, ordem));
        tamanho++;
    }

    public OrdemServico buscar(int codigo) {
        int indice = hash(codigo);
        LinkedList<Entrada> lista = tabela[indice];

        for (Entrada entrada : lista) {
            if (entrada.codigo == codigo) {
                return entrada.ordem;
            }
        }
        return null;
    }

    private int hash(int codigo) {
        return Integer.hashCode(codigo) % tabela.length;
    }

    private void redimensionar() {
        LinkedList<Entrada>[] novaTabela = new LinkedList[tabela.length * 2];
        for (int i = 0; i < novaTabela.length; i++) {
            novaTabela[i] = new LinkedList<>();
        }

        for (LinkedList<Entrada> lista : tabela) {
            for (Entrada entrada : lista) {
                int indice = Integer.hashCode(entrada.codigo) % novaTabela.length;
                novaTabela[indice].add(entrada);
            }
        }

        tabela = novaTabela;
    }

    private static class Entrada {
        int codigo;
        OrdemServico ordem;

        Entrada(int codigo, OrdemServico ordem) {
            this.codigo = codigo;
            this.ordem = ordem;
        }
    }
}