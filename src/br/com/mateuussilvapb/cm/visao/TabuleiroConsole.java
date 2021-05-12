package br.com.mateuussilvapb.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.mateuussilvapb.cm.excecao.ExplosaoException;
import br.com.mateuussilvapb.cm.excecao.SairException;
import br.com.mateuussilvapb.cm.modelo.Tabuleiro;

public class TabuleiroConsole {

    private Tabuleiro tabuleiro;
    private Scanner entrada = new Scanner(System.in);

    public TabuleiroConsole(Tabuleiro tab) {
        this.tabuleiro = tab;

        executarJogo();
    }

    private void executarJogo() {
        try {
            boolean continuar = true;
            while (continuar) {
                cicloDoJogo();
                System.out.println("Outra partida? (S/n)");
                String resposta = entrada.nextLine();
                if ("n".equalsIgnoreCase(resposta)) {
                    continuar = false;
                } else {
                    tabuleiro.reiniciar();
                }
            }
        } catch (Exception e) {
            System.out.println("Obrigado por jogar!");
        } finally {
            entrada.close();
        }

    }

    private void cicloDoJogo() {
        try {

            while (!tabuleiro.objetivoAlcancado()) {
                System.out.println(tabuleiro);
                String digitado = capturarValorDigitado("Digite x e y ou digite sair: ");
                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(e -> Integer.parseInt(e.trim()))
                        .iterator();
                digitado = capturarValorDigitado("Escolha uma das opções abaixo\n"
                        + "1 - Abri\n"
                        + "2 - (Des)Marcar\n");
                if ("1".equals(digitado)) {
                    tabuleiro.abrirCampo(xy.next(), xy.next());
                } else if ("2".equals(digitado)) {
                    tabuleiro.alterarMarcacao(xy.next(), xy.next());
                } else {
                    System.out.println("Você digitou algo inválido!");
                    throw new SairException();

                }
            }

            System.out.println(tabuleiro);

            System.out.println("Você ganho!");
        } catch (ExplosaoException e) {
            System.out.println(tabuleiro);

            System.out.println("Você perdeu!");
            throw new SairException();
        }
    }

    private String capturarValorDigitado(String texto) {
        System.out.print(texto);
        String digitado = entrada.nextLine();
        if ("sair".equalsIgnoreCase(digitado)) {
            throw new SairException();
        }
        return digitado;
    }
}
