package com.game.Telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.*;
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

    private JPanel painelDeck;
    private JPanel painelCartas;


    public TelaSelecaoDeck(Conta conta) {
    	
        this.conta = conta;

        setTitle("Selecione seu Deck");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout());

        painelDeck = new JPanel(new GridLayout(2, 2, 10, 10));
        painelDeck.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelCartas = new JPanel(new GridLayout(0, 2, 10, 10));
        painelCartas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        
        JPanel painelCentral = new JPanel(new GridLayout(1, 2, 10, 10));
        

        JPanel painelBotoes = new JPanel(new GridLayout(1, 2));
        
        JPanel wrapperCartas = new JPanel(new BorderLayout());
        wrapperCartas.add(painelCartas, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(
        		wrapperCartas,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        JScrollPane scroll2 = new JScrollPane(
                painelDeck,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.getVerticalScrollBar().setUnitIncrement(16); // scroll suave
        painelCentral.add(scroll2);
        painelCentral.add(scroll);
        
        //add(scroll, BorderLayout.CENTER);
        
        add(painelCentral);
        add(painelBotoes, BorderLayout.SOUTH);


        botoes[0] = new JButton("Voltar");
        botoes[0].addActionListener(e -> voltar());
        painelBotoes.add(botoes[0]);
        botoes[1] = new JButton("Salvar");
        botoes[1].addActionListener(e -> salvar());
        painelBotoes.add(botoes[1]);
        
        atualizarListaCartas();
        atualizarPainelDeck();





        setLocationRelativeTo(null);
        setVisible(true);
    }



    private JPanel criarCarta(Personagem p) {

        JPanel cartaPanel = new JPanel(new BorderLayout());
        cartaPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cartaPanel.setPreferredSize(new Dimension(160, 260));

        PainelImagem imagem = new PainelImagem(
                new ImageIcon(getClass().getResource(p.getCaminhoImagem()))
        );
        imagem.setPreferredSize(new Dimension(150, 250));

        JLabel nome = new JLabel(p.getNome(), SwingConstants.CENTER);

        StringBuilder estrelas = new StringBuilder();
        for (int i = 0; i < p.getGrau(); i++) {
            estrelas.append("🌟");
        }

        JLabel grau = new JLabel(estrelas + " Nv " + p.getNivel(), SwingConstants.CENTER);

        JButton botaoInfo = new JButton("Informações");
        JButton botao = new JButton();

        if (conta.getDeck().contains(p)) {
            botao.setText("✔ Selecionado");
        } else {
            botao.setText("Selecionar");
        }

        botao.addActionListener(e -> {

            if (conta.getDeck().contains(p)) {
                conta.removerDoDeck(p.getId());
            } else {
                if (!conta.adicionarAoDeck(p.getId())) {
                    JOptionPane.showMessageDialog(this, "Deck cheio!");
                    return;
                }
            }

            atualizarPainelDeck();
            atualizarListaCartas();
        });



        botaoInfo.addActionListener(e -> {
            setVisible(false);
            new TelaInfoCard(conta, p);
        });

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.add(grau);
        infoPanel.add(botaoInfo);
        infoPanel.add(botao);

        cartaPanel.add(nome, BorderLayout.NORTH);
        cartaPanel.add(imagem, BorderLayout.CENTER);
        cartaPanel.add(infoPanel, BorderLayout.SOUTH);

        return cartaPanel;
    }
    private JPanel criarCartaVazia() {
        JPanel carta = new JPanel(new BorderLayout());
        carta.setPreferredSize(new Dimension(160, 260));
        carta.setBorder(BorderFactory.createDashedBorder(Color.GRAY));

        JLabel vazio = new JLabel("Slot vazio", SwingConstants.CENTER);
        vazio.setForeground(Color.GRAY);

        carta.add(vazio, BorderLayout.CENTER);

        return carta;
    }

    private void atualizarPainelDeck() {
        painelDeck.removeAll();

        int totalSlots = 4;
        int usados = 0;

        for (Personagem p : conta.getDeck()) {
            painelDeck.add(criarCarta(p));
            usados++;
        }

        for (int i = usados; i < totalSlots; i++) {
            painelDeck.add(criarCartaVazia());
        }

        painelDeck.revalidate();
        painelDeck.repaint();
    }

    private void atualizarListaCartas() {
        painelCartas.removeAll();

        List<Personagem> disponiveis = new ArrayList<>();

        for (Personagem p : conta.getTodosPersonagens()) {
            if (p.getDesbloqueado() && !conta.getDeck().contains(p)) {
                disponiveis.add(p);
            }
        }

        for (Personagem p : disponiveis) {
            painelCartas.add(criarCarta(p));
        }

        painelCartas.revalidate();
        painelCartas.repaint();
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
