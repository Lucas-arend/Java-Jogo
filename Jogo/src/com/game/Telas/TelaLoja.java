package com.game.Telas;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

import com.game.Conta;
import com.game.ItemLoja;
import com.game.Loja;

public class TelaLoja extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Conta conta;
	
	private JTextArea txtDados;
	JButton btnMontarDeck = new JButton("Montar Deck");
	JButton btnLoja = new JButton("Loja");
	JButton btnCampanha = new JButton("Campanha");
	JButton btnBatalha1 = new JButton("Modo Campanha");
	JButton btnBatalha2 = new JButton("Batalha Aleatória");
	JButton btnBatalhasEspeciais = new JButton("Eventos");
	

	public TelaLoja(Conta conta) {
		this.conta = conta;
		
		setTitle("Loja!");
		setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
		
        txtDados = new JTextArea("Nível: " + conta.getNivel() + "  Nome: " + conta.getUsuario() + " Moedas: " + conta.getMoedas() + " Gemas: " + conta.getGemas());

        carregar();

        
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	private JPanel criarPainelBaus() {
	    JPanel panel = new JPanel();
	    panel.setBorder(BorderFactory.createTitledBorder("Baús"));

	    for (ItemLoja bau : Loja.listarBaus()) {
	        JButton btn = new JButton(bau.nome.toUpperCase() + ", " + bau.valor + " " + bau.tipoVal);

	        btn.addActionListener(e -> {
	            Loja.comprar(conta, bau);
	            atualizarDados();
	        });

	        panel.add(btn);
	    }

	    return panel;
	}


	private JPanel criarPainelOfertas() {
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBorder(BorderFactory.createTitledBorder("Ofertas"));

	    for (ItemLoja oferta : Loja.listarOfertas()) {
	        JButton btn = new JButton(oferta.nome + ", " + oferta.valor + " " + oferta.tipoVal);

	        btn.addActionListener(e -> {
	            Loja.comprar(conta, oferta);
	            atualizarDados();
	        });

	        panel.add(btn);
	    }

	    return panel;
	}



	
	private void campanha() {
		setVisible(false);
		new TelaInicial(conta);
	}
	private void eventos() {
		
	}

    private void montarDeck() {
    	setVisible(false);
    	new TelaSelecaoDeck(conta);
    }
    
	private void abrirLoja() {
		setVisible(false);
		new TelaLoja(conta);
	}


	private void atualizarDados() {
	    txtDados.setText(
	        "Nível: " + conta.getNivel() +
	        "  Nome: " + conta.getUsuario() +
	        " Moedas: " + conta.getMoedas() +
	        " Gemas: " + conta.getGemas()
	    );
	    
	}

	
	private void carregar() {
	    getContentPane().removeAll();
        JPanel painel = new JPanel();
        JPanel inferior = new JPanel(new GridLayout(1,4));
        JPanel Interface = new JPanel();
        JPanel dados = new JPanel(new GridLayout());
        btnCampanha = new JButton("Campanha");
    	btnMontarDeck = new JButton("Montar Deck");
    	btnLoja = new JButton("Loja");
    	
    	inferior.add(btnLoja);
    	inferior.add(btnMontarDeck);
    	inferior.add(btnCampanha);
    	inferior.add(btnBatalhasEspeciais);
    	
        btnCampanha.addActionListener(e -> campanha());
        btnMontarDeck.addActionListener(e -> montarDeck());
        btnLoja.addActionListener(e -> abrirLoja());
        btnBatalhasEspeciais.addActionListener(e -> eventos());
        
	    dados.add(txtDados);
	    Interface.add(criarPainelOfertas());
	    Interface.add(criarPainelBaus());	    
	    painel.setLayout(new BorderLayout());

        painel.add(dados, BorderLayout.NORTH);
        painel.add(Interface, BorderLayout.CENTER);
        painel.add(inferior, BorderLayout.SOUTH);
	    add(painel);
	    revalidate();
	    repaint();
	}


}
