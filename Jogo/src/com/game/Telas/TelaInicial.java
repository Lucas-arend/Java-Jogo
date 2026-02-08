package com.game.Telas;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import com.game.Batalha;
import com.game.Conta;
import com.game.Personagem;

@SuppressWarnings("serial")
public class TelaInicial extends JFrame {
	private Conta conta;
	JTextArea TextDados = new JTextArea("");
	JButton btnMontarDeck = new JButton("Montar Deck");
	JButton btnLoja = new JButton("Loja");
	JButton btnIniciar = new JButton("Iniciar Batalha");

	
	
	public TelaInicial(Conta conta) {
		this.conta = conta;
		
		setTitle("Tela inicial!");
		setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        btnIniciar = new JButton("Iniciar Batalha");
        TextDados = new JTextArea("Nível: " + conta.getNivel() + "  Nome: " + conta.getUsuario() + " Moedas: " + conta.getMoedas() + " Gemas: " + conta.getGemas());
    	btnMontarDeck = new JButton("Montar Deck");
    	btnLoja = new JButton("Loja");
    	
        btnIniciar.addActionListener(e -> iniciarBatalha());
        btnMontarDeck.addActionListener(e -> montarDeck());
        btnLoja.addActionListener(e -> abrirLoja());

        carregar();
        atualizarDadosConta();
        setLocationRelativeTo(null);
    	setVisible(true);
	}
	

	private void carregar() {
		getContentPane().removeAll();
		setLayout(new GridLayout(4, 1));
	      add(TextDados);
	        add(btnMontarDeck);
	        add(btnLoja);
	    	add(btnIniciar);
	}
	
	private void iniciarBatalha() {

	    if (conta.getDeck().isEmpty()) {
	        JOptionPane.showMessageDialog(this,
	                "Selecione pelo menos 1 personagem!");
	        return;
	    }

	    List<Personagem> timeJogador = conta.getDeck().stream()
	            .map(Personagem::clonarParaBatalha)
	            .toList();

	    Conta contaIA = new Conta("IA", "ia");
	    List<Personagem> disponiveis = contaIA.getTodosPersonagens();
	    Collections.shuffle(disponiveis);

	    for (int i = 0; i < Math.min(4, disponiveis.size()); i++) {
	        contaIA.adicionarAoDeck(disponiveis.get(i).getId());
	    }

	    List<Personagem> timeIA = contaIA.getDeck().stream()
	            .map(Personagem::clonarParaBatalha)
	            .toList();

	    setVisible(false);
	    new TelaBatalha(new Batalha(timeJogador, timeIA, conta));
	}

    
    private void atualizarDadosConta() {
        TextDados.setText(
            "Nível: " + conta.getNivel() +
            " Nome: " + conta.getUsuario() +
            " Moedas: " + conta.getMoedas() +
            " Gemas: " + conta.getGemas()
        );
    }

    
    private void montarDeck() {
    	setVisible(false);
    	new TelaSelecaoDeck(conta);
    }
    
	private void abrirLoja() {
		setVisible(false);
		new TelaLoja(conta);
	}
}
