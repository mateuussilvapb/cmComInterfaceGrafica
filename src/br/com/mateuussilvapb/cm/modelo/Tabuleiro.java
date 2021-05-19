package br.com.mateuussilvapb.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador {

    private final int LINHAS;
    private final int COLUNAS;
    private final int MINAS;

    private final List<Campo> campos = new ArrayList<>();
    private final List<Consumer<ResultadoEvento>> observadores
            = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.LINHAS = linhas;
        this.COLUNAS = colunas;
        this.MINAS = minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void paraCadaCampo(Consumer<Campo> funcao) {
        campos.forEach(funcao);
    }

    public void registrarObservador(Consumer<ResultadoEvento> observador) {
        observadores.add(observador);
    }

    public void notificarObservadores(boolean resultado) {
        observadores.stream()
                .forEach(o -> o.accept(new ResultadoEvento(resultado)));
    }

    public void abrirCampo(int linha, int coluna) {
        campos.parallelStream()
                .filter(c -> c.getLINHA() == linha && c.getCOLUNA() == coluna)
                .findFirst()
                .ifPresent(c -> c.abrir());;
    }

    private void mostrarMinas() {
        campos.stream()
                .filter(c -> c.isMinado())
                .forEach(c -> c.setAberto(true));
    }

    public void alterarMarcacao(int linha, int coluna) {
        campos.parallelStream()
                .filter(c -> c.getLINHA() == linha && c.getCOLUNA() == coluna)
                .findFirst()
                .ifPresent(c -> c.alternarMarcacao());;
    }

    private void gerarCampos() {
        for (int linha = 0; linha < LINHAS; linha++) {
            for (int coluna = 0; coluna < COLUNAS; coluna++) {
                Campo campo = new Campo(linha, coluna);
                campo.registrarObservador(this);
                campos.add(campo);

            }
        }

    }

    /*
	 * Percorre toda a lista de vizinhos duas vezes e tenta associar cada um a cada
	 * um. Porém, só será possível associar se, de fato, os campos forem vizinhos.
     */
    private void associarVizinhos() {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        int minasArmadas = 0;
        Predicate<Campo> minado = c -> c.isMinado();

        do {
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = (int) campos.stream().filter(minado).count();
        } while (minasArmadas < MINAS);
    }

    public boolean objetivoAlcancado() {
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar() {
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        if (evento == CampoEvento.EXPLODIR) {
            mostrarMinas();
            notificarObservadores(false);
        } else if (objetivoAlcancado()) {
            notificarObservadores(true);
        }
    }

    public int getLINHAS() {
        return LINHAS;
    }

    public int getCOLUNAS() {
        return COLUNAS;
    }

    public int getMINAS() {
        return MINAS;
    }

}
