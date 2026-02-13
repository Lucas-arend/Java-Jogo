package com.game.Telas;

import java.awt.BorderLayout;
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
	JButton btnCampanha = new JButton("Campanha");
	JButton btnBatalha1 = new JButton("Modo Campanha");
	JButton btnBatalha2 = new JButton("Batalha Aleatória");
	JButton btnBatalhasEspeciais = new JButton("Eventos");

	
	
	public TelaInicial(Conta conta) {
		this.conta = conta;
		
		setTitle("Tela inicial!");
		setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel painel = new JPanel(new GridLayout());
        JPanel inferior = new JPanel(new GridLayout());
        JPanel Interface = new JPanel();
        JPanel dados = new JPanel();

        
        btnBatalha1 = new JButton("Modo Campanha");
        btnBatalha2 = new JButton("Batalha Aleatória");
        btnBatalhasEspeciais = new JButton("Eventos");
        btnCampanha = new JButton("Campanha");
        TextDados = new JTextArea("Nível: " + conta.getNivel() + "  Nome: " + conta.getUsuario() + " Moedas: " + conta.getMoedas() + " Gemas: " + conta.getGemas());
    	btnMontarDeck = new JButton("Montar Deck");
    	btnLoja = new JButton("Loja");
    	dados.add(TextDados);
    	Interface.add(btnBatalha1);
    	Interface.add(btnBatalha2);
    	inferior.add(btnLoja);
    	inferior.add(btnMontarDeck);
    	inferior.add(btnCampanha);
    	inferior.add(btnBatalhasEspeciais);
    	
    	
        btnBatalha2.addActionListener(e -> iniciarBatalhaAleatoria());
        btnBatalha1.addActionListener(e -> iniciarBatalhaCampanha());
        btnCampanha.addActionListener(e -> campanha());
        btnMontarDeck.addActionListener(e -> montarDeck());
        btnLoja.addActionListener(e -> abrirLoja());
        btnBatalhasEspeciais.addActionListener(e -> eventos());
        painel.setLayout(new BorderLayout());
        painel.add(dados, BorderLayout.NORTH);
        painel.add(Interface, BorderLayout.CENTER);
        painel.add(inferior, BorderLayout.SOUTH);
        add(painel);
        atualizarDadosConta();
        setLocationRelativeTo(null);
    	setVisible(true);
	}
	
	
	private void iniciarBatalhaAleatoria() {

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
	
	private void iniciarBatalhaCampanha() {
		
	}
	
	private void campanha() {
		
	}
	private void eventos() {
		
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
