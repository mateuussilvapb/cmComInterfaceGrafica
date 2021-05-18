package br.com.mateuussilvapb.cm.visao;

import br.com.mateuussilvapb.cm.modelo.Tabuleiro;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PainelTabuleiro extends JPanel {

    public PainelTabuleiro(Tabuleiro tabuleiro) {
        setLayout(new GridLayout(tabuleiro.getLINHAS(), tabuleiro.getCOLUNAS()));
        int total = tabuleiro.getLINHAS() * tabuleiro.getCOLUNAS();
        for (int i = 0; i < total; i++) {
            add(new JButton());
        }
    }

}
