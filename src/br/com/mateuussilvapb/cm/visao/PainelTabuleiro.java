package br.com.mateuussilvapb.cm.visao;

import br.com.mateuussilvapb.cm.modelo.Tabuleiro;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PainelTabuleiro extends JPanel {

    public PainelTabuleiro(Tabuleiro tabuleiro) {
        setLayout(new GridLayout(tabuleiro.getLINHAS(), tabuleiro.getCOLUNAS()));

        tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));
        tabuleiro.registrarObservador(e -> {
            //TODO mostrar resultado para o usuário
        });
    }

}
