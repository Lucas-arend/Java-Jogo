package com.game;

import javax.swing.SwingUtilities;

import com.game.Telas.TelaInicial;

public class Main {

    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(() -> {
            Conta conta1 = new Conta("Jogador1", "senha1");
            new TelaInicial(conta1);
        });
    }
}

					
