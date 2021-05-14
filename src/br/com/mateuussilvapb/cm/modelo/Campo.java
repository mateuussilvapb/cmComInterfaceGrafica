package br.com.mateuussilvapb.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

    private final int LINHA;
    private final int COLUNA;

    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    private List<Campo> vizinhos = new ArrayList<>();
    private List<CampoObservador> observadores = new ArrayList<>();

    Campo(int linha, int coluna) {
        this.COLUNA = coluna;
        this.LINHA = linha;
    }

    public void registrarObservador(CampoObservador observador) {
        observadores.add(observador);
    }

    public void notificarObservadores(CampoEvento evento) {
        observadores.stream()
                .forEach(o -> o.eventoOcorreu(this, evento));
    }

    boolean adicionarVizinho(Campo vizinho) {
        /*
		 * Caso a linha e a coluna do campo atual seja diferente do vizinho significa
		 * que o vizinho pode estar na diagonal.
         */
        boolean linhaDiferente = this.LINHA != vizinho.LINHA;
        boolean colunaDiferente = this.COLUNA != vizinho.COLUNA;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(this.LINHA - vizinho.LINHA);
        int deltaColuna = Math.abs(this.COLUNA - vizinho.COLUNA);
        int deltaGeral = deltaColuna + deltaLinha;

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
            if (marcado) {
                notificarObservadores(CampoEvento.MARCAR);
            } else {
                notificarObservadores(CampoEvento.DESMARCAR);
            }
        }
    }

    boolean abrir() {
        if (!aberto && !marcado) {
            if (minado) {
                notificarObservadores(CampoEvento.EXPLODIR);
                return true;
            }
            setAberto(aberto);
            notificarObservadores(CampoEvento.ABRIR);
            if (vizinhacaSegura()) {
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        } else {
            return false;
        }
    }

    boolean vizinhacaSegura() {
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    void minar() {
        minado = true;
    }

    public boolean isMinado() {
        return minado;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public boolean isAberto() {
        return aberto;
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;
        if (aberto) {
            notificarObservadores(CampoEvento.ABRIR);
        }
    }

    public boolean isFechado() {
        return !isAberto();
    }

    public int getLINHA() {
        return LINHA;
    }

    public int getCOLUNA() {
        return COLUNA;
    }

    boolean objetivoAlcancado() {
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    long minasNaVizinhanca() {
        return vizinhos.parallelStream().filter(v -> v.minado).count();
    }

    void reiniciar() {
        aberto = false;
        minado = false;
        marcado = false;
    }
}
