package br.com.mateuussilvapb.cm;

import br.com.mateuussilvapb.cm.excecao.SairException;
import br.com.mateuussilvapb.cm.modelo.Tabuleiro;
import br.com.mateuussilvapb.cm.visao.TabuleiroConsole;
import java.util.Scanner;

public class Aplicacao {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        System.out.println("\n--- CAMPO MINADO ---");
        System.out.println("Informe uma das opções a seguir");
        System.out.println("1 - INICIAR JOGO");
        System.out.println("2 - SAIR");
        String digitado = entrada.nextLine();
        if ("1".equals(digitado)) {
            int linhas;
            int colunas;
            int minas;
            System.out.print("\n"
                    + "Informe a quantidade de linhas: ");
            entrada = new Scanner(System.in);
            linhas = entrada.nextInt();
            System.out.print("\n"
                    + "Informe a quantidade de colunas: ");
            entrada = new Scanner(System.in);
            colunas = entrada.nextInt();
            System.out.print("\n"
                    + "Informe a quantidade de minas: ");
            entrada = new Scanner(System.in);
            minas = entrada.nextInt();
            while (minas > (linhas * colunas)) {
                System.out.print("\n"
                        + "A quantidade de minas informadas é superior a"
                        + " quantidade de campos. Informe um valor válido"
                        + " de minas: ");
                entrada = new Scanner(System.in);
                minas = entrada.nextInt();
            }
            Tabuleiro tab = new Tabuleiro(linhas, colunas, minas);
            new TabuleiroConsole(tab);
        } else if ("2".equals(digitado)) {
            throw new SairException();
        }
        entrada.close();
    }
}
