package br.com.mateuussilvapb.cm.visao;

import br.com.mateuussilvapb.cm.modelo.Campo;
import br.com.mateuussilvapb.cm.modelo.CampoEvento;
import br.com.mateuussilvapb.cm.modelo.CampoObservador;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class BotaoCampo extends JButton implements CampoObservador, MouseListener {

    private final Color BG_PADRAO = new Color(184, 184, 184);
    private final Color BG_MARCAR = new Color(8, 179, 247);
    private final Color BG_EXPLODIR = new Color(189, 66, 68);
    private final Color TEXTO_VERDE = new Color(0, 100, 0);
    private Campo campo;

    public BotaoCampo(Campo campo) {
        //Vinculando campo ao botão
        this.campo = campo;
        //Setando borda ao botão
        setBorder(BorderFactory.createBevelBorder(0));
        //Setando plano de fundo padrão ao botão
        setBackground(BG_PADRAO);
        //Registrando o acompanhamento do clique do mouse
        addMouseListener(this);
        //Registrando botão como observador do campo
        campo.registrarObservador(this);
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        switch (evento) {
            case ABRIR:
                aplicarEstiloAbrir();
                break;
            case MARCAR:
                aplicarEstiloMarcar();
                break;
            case EXPLODIR:
                aplicarEstiloExplodir();
                break;
            default:
                aplicarEstiloPadrao();
                setBorder(BorderFactory.createBevelBorder(0));
        }
    }

    private void aplicarEstiloAbrir() {
        setBackground(BG_PADRAO);

        if (campo.isMinado()) {
            setBackground(BG_EXPLODIR);
            return;
        }

        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        switch (campo.minasNaVizinhanca()) {
            case 1:
                setForeground(TEXTO_VERDE);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.YELLOW);
                break;
            case 4:
            case 5:
            case 6:
                setForeground(Color.RED);
                break;
            default:
                setForeground(Color.PINK);
        }
        String valor = !campo.vizinhancaSegura()
                ? campo.minasNaVizinhanca() + "" : "";
        setText(valor);
    }

    private void aplicarEstiloMarcar() {
        setBackground(BG_MARCAR);
        setForeground(Color.BLACK);
        setText("!");
    }

    private void aplicarEstiloExplodir() {
        setBackground(BG_EXPLODIR);
        setForeground(Color.WHITE);
        setText("X");
    }

    private void aplicarEstiloPadrao() {
        setBackground(BG_PADRAO);
        setText("");
    }

    //Interface dos eventos do mouse
    @Override
    public void mousePressed(MouseEvent e) {
        //QUANDO O BOTÃO ESQUEDO É PRESSIONADO
        if (e.getButton() == 1) {
            campo.abrir();
        } else {
            campo.alternarMarcacao();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
