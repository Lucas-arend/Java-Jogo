package com.game;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(() -> {
            Conta conta1 = new Conta("Jogador1", "senha1");
            new TelaInicial(conta1);
            //new TelaSelecaoDeck(conta1);
        });
    }
}

					
