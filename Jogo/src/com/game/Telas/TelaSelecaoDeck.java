package com.game.Telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import com.game.Conta;
import com.game.Personagem;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class TelaSelecaoDeck extends JFrame {

	private JButton[] botoes = new JButton[2];
	private List<JButton> btn = new ArrayList<>();
    int i = 0;

    private Conta conta;

    public TelaSelecaoDeck(Conta conta) {
    	
        this.conta = conta;

        setTitle("Selecione seu Deck");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout());

        
        JPanel painelCartas = new JPanel(new GridLayout(0, 4, 10, 10));
        painelCartas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelBotoes = new JPanel(new GridLayout(1, 2));
        JScrollPane scroll = new JScrollPane(
                painelCartas,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scroll.getVerticalScrollBar().setUnitIncrement(16); // scroll suave

        add(scroll, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);


        botoes[0] = new JButton("Voltar");
        botoes[0].addActionListener(e -> voltar());
        painelBotoes.add(botoes[0]);
        botoes[1] = new JButton("Salvar");
        botoes[1].addActionListener(e -> salvar());
        painelBotoes.add(botoes[1]);
        
        for (Personagem p : conta.getTodosPersonagens()) {
        	if (p.getDesbloqueado()) {

            JPanel cartaPanel = new JPanel();
            cartaPanel.setLayout(new BorderLayout());
            cartaPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cartaPanel.setPreferredSize(new Dimension(180, 260));

            // IMAGEM
            JLabel imagem = new JLabel();
            ImageIcon icon = new ImageIcon("imgs/" + p.getImagem()); // ex: blueEyes.png
            Image img = icon.getImage().getScaledInstance(160, 200, Image.SCALE_SMOOTH);
            imagem.setIcon(new ImageIcon(img));
            imagem.setHorizontalAlignment(SwingConstants.CENTER);

            // INFO
            JLabel nome = new JLabel(p.getNome(), SwingConstants.CENTER);
            String grauS = "";
            for(int i = 0; p.getGrau() >= i; i++) {
            	grauS += "🌟";
            }
            JLabel grau = new JLabel(grauS + " Nv " + p.getNivel(), SwingConstants.CENTER);
            JLabel raridade = new JLabel(p.getRaridade().toString(), SwingConstants.CENTER);

            // BOTÃO
            JButton botaoInfo = new JButton("Informações");
            JButton botao = new JButton("Selecionar");

            if (conta.getDeck().contains(p)) {
                botao.setText("✔ Selecionado");
            }

            botao.addActionListener(e -> {
                if (conta.getDeck().contains(p)) {
                    conta.removerDoDeck(p.getId());
                    botao.setText("Selecionar");
                } else {
                    if (!conta.adicionarAoDeck(p.getId())) {
                        JOptionPane.showMessageDialog(this, "Deck cheio!");
                        return;
                    }
                    botao.setText("✔ Selecionado");
                }
            });
            
            botaoInfo.addActionListener(e -> {
            	setVisible(false);
            	new TelaInfoCard(conta, p);
            });

            // RODAPÉ
            JPanel infoPanel = new JPanel(new GridLayout(3, 1));
            infoPanel.add(grau);
            infoPanel.add(botaoInfo);
            infoPanel.add(botao);

            JPanel topo = new JPanel(new BorderLayout());
            topo.add(nome, BorderLayout.CENTER);

            cartaPanel.add(topo, BorderLayout.NORTH);
            cartaPanel.add(imagem, BorderLayout.CENTER);
            cartaPanel.add(infoPanel, BorderLayout.SOUTH);


            painelCartas.add(cartaPanel);
       }
        }


        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void salvar() {
        if (conta.getDeck().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Selecione pelo menos 1 personagem!");
            return;
        }

        conta.criarDeckParaBatalha();
        setVisible(false);
        new TelaInicial(conta);
    }

	
	private void voltar() {
		setVisible(false);
		new TelaInicial(conta);
	}
    

}
