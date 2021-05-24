package br.com.mateuussilvapb.cm.visao;

import br.com.mateuussilvapb.cm.modelo.Tabuleiro;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PainelTabuleiro extends JPanel {

    public PainelTabuleiro(Tabuleiro tabuleiro) {
        setLayout(new GridLayout(tabuleiro.getLINHAS(), tabuleiro.getCOLUNAS()));

        tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));
        tabuleiro.registrarObservador(e -> {
            SwingUtilities.invokeLater(() -> {
                if (e.isGANHOU()) {
                    JOptionPane.showMessageDialog(this, "PARABÉNS, VOCÊ GANHO :D !!!");
                } else {
                    JOptionPane.showMessageDialog(this, "INFELIZMENTE, NÃO FOI DESSA VEZ :C...");
                }
                tabuleiro.reiniciar();
            });
            
        });
    }

}
