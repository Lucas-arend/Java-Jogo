package com.game.Telas;

import java.awt.GridLayout;

import javax.swing.*;

import com.game.Conta;
import com.game.ItemLoja;
import com.game.Loja;
import com.game.Loja.TipoLoja;

public class TelaLoja extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Conta conta;
	
	private JTextArea txtDados;
	

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
	    JPanel panel = new JPanel(new GridLayout(0, 1));
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

	
	private void voltar() {
		setLayout(new GridLayout(3,2));
		JButton btn = new JButton("VOLTAR");
		btn.addActionListener(e -> {
			setVisible(false);
	        new TelaInicial(conta);
		});
		add(btn);
	}
	private JPanel criarPainelOfertas() {
	    JPanel panel = new JPanel(new GridLayout(0, 1));
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




	private JPanel criarPainelVoltar() {
	    JPanel panel = new JPanel();
	    JButton btn = new JButton("VOLTAR");

	    btn.addActionListener(e -> {
	        dispose();
	        new TelaInicial(conta);
	    });

	    panel.add(btn);
	    return panel;
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
	    setLayout(new GridLayout(4, 1));

	    add(txtDados);
	    add(criarPainelOfertas());
	    add(criarPainelBaus());
	    add(criarPainelVoltar());

	    revalidate();
	    repaint();
	}


}
