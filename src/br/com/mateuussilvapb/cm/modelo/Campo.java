package br.com.mateuussilvapb.cm.modelo;

import br.com.mateuussilvapb.cm.excecao.ExplosaoException;
import java.util.ArrayList;
import java.util.List;

public class Campo {
    
    private final int LINHA;
    private final int COLUNA;
    
    private boolean aberto;
    private boolean marcado;
    private boolean minado;
    
    private List<Campo> vizinhos = new ArrayList<>();
    
    public Campo(int LINHA, int COLUNA) {
        this.LINHA = LINHA;
        this.COLUNA = COLUNA;
    }
    
    boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = this.LINHA != vizinho.LINHA;
        boolean colunaDiferente = this.COLUNA != vizinho.COLUNA;
        boolean diagonal = linhaDiferente && colunaDiferente;
        
        int deltaLinha = Math.abs(this.LINHA - vizinho.LINHA);
        int deltaColuna = Math.abs(this.COLUNA - vizinho.COLUNA);
        int deltaGeral = deltaLinha + deltaColuna;
        
        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }
    
    void alternarMarcacao() {
        if (!aberto) {
            marcado = !marcado;
        }
    }
    
    boolean abrir() {
        if (!aberto && !marcado) {
            aberto = true;
            if (minado) {
                throw new ExplosaoException();
            }
            if (vizinhancaSegura()) {
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        } else {
            return false;
        }
    }
    
    private boolean vizinhancaSegura() {
        return vizinhos.stream().noneMatch(v -> v.minado);
    }
    
    boolean objetivoAlcancado() {
        boolean desvendado = aberto && !minado;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }
    
    long minasNaVizinhanca() {
        return vizinhos.stream().filter(v -> v.minado).count();
    }
    
    void reiniciar() {
        aberto = false;
        minado = false;
        marcado = false;
    }
    
    @Override
    public String toString() {
        if (aberto) {
            return " ";
        } else if (marcado) {
            return "X";
        } else if (aberto && minado) {
            return "*";
        } else if (aberto && minasNaVizinhanca() > 0) {
            return Long.toString(minasNaVizinhanca());
        } else {
            return "?";
        }
    }
    
    public boolean isAberto() {
        return aberto;
    }
    
    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }
    
    public boolean isMarcado() {
        return marcado;
    }
    
    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }
    
    public boolean isMinado() {
        return minado;
    }
    
    public void setMinado(boolean minado) {
        this.minado = minado;
    }
    
    public List<Campo> getVizinhos() {
        return vizinhos;
    }
    
    public void setVizinhos(List<Campo> vizinhos) {
        this.vizinhos = vizinhos;
    }
    
    public int getLINHA() {
        return LINHA;
    }
    
    public int getCOLUNA() {
        return COLUNA;
    }
    
}
