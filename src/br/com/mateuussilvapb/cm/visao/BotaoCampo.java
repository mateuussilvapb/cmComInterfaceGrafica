package br.com.mateuussilvapb.cm.visao;

import br.com.mateuussilvapb.cm.modelo.Campo;
import br.com.mateuussilvapb.cm.modelo.CampoEvento;
import br.com.mateuussilvapb.cm.modelo.CampoObservador;
import javax.swing.JButton;

public class BotaoCampo extends JButton implements CampoObservador {

    public BotaoCampo(Campo campo) {

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
        }
    }

    private void aplicarEstiloAbrir() {

    }

    private void aplicarEstiloMarcar() {

    }

    private void aplicarEstiloExplodir() {

    }

    private void aplicarEstiloPadrao() {

    }

}
