package com.game.Telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;
import com.game.Conta;
import com.game.Personagem;

import java.util.ArrayList;
import java.util.List;
import java.text.Normalizer;


@SuppressWarnings("serial")
public class TelaSelecaoDeck extends JFrame {

    int i = 0;

    private Conta conta;

    private JPanel painelDeck;
    private JPanel painelCartas;
    JButton btnMontarDeck = new JButton("Montar Deck");
	JButton btnLoja = new JButton("Loja");
	JButton btnCampanha = new JButton("Campanha");
	JButton btnBatalha1 = new JButton("Modo Campanha");
	JButton btnBatalha2 = new JButton("Batalha Aleatória");
	JButton btnBatalhasEspeciais = new JButton("Eventos");
	
	private JTextField areaParaPesquisar;

	private String normalizar(String texto) {
	    return Normalizer
	        .normalize(texto, Normalizer.Form.NFD)
	        .replaceAll("[^\\p{ASCII}]", "")
	        .toLowerCase();
	}

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
        
        btnCampanha = new JButton("Campanha");
    	btnMontarDeck = new JButton("Montar Deck");
    	btnLoja = new JButton("Loja");

        btnCampanha.addActionListener(e -> campanha());
        btnMontarDeck.addActionListener(e -> montarDeck());
        btnLoja.addActionListener(e -> abrirLoja());
        btnBatalhasEspeciais.addActionListener(e -> eventos());

        JPanel painelWrapperInterno = new JPanel(new BorderLayout());
        painelWrapperInterno.add(painelCartas, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 4));

        painelBotoes.add(btnLoja);
        painelBotoes.add(btnMontarDeck);
        painelBotoes.add(btnCampanha);
        painelBotoes.add(btnBatalhasEspeciais);
        
        JPanel wrapperCartas = new JPanel(new BorderLayout());
        JPanel painelFiltros = new JPanel(new GridLayout(1,3));
        JButton catalogo = new JButton("Catálogo de cartas");
        
        catalogo.addActionListener(e -> abrirCatalogo());
        areaParaPesquisar = new JTextField();
        
        areaParaPesquisar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrarCartas();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrarCartas();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrarCartas();
            }
        });
        painelFiltros.add(areaParaPesquisar);
        painelFiltros.add(catalogo);
        wrapperCartas.add(painelFiltros, BorderLayout.NORTH);
     // SCROLL SÓ NAS CARTAS
        JScrollPane scrollCartas = new JScrollPane(
        	    painelWrapperInterno,
        	    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        	    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        	);
        wrapperCartas.add(scrollCartas, BorderLayout.CENTER);

        JScrollPane scroll2 = new JScrollPane(
                painelDeck,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollCartas.getVerticalScrollBar().setUnitIncrement(16); // scroll suave
        painelCentral.add(scroll2);
        painelCentral.add(wrapperCartas);
        
        //add(scroll, BorderLayout.CENTER);
        
        add(painelCentral);
        add(painelBotoes, BorderLayout.SOUTH);


        
        atualizarListaCartas();
        atualizarPainelDeck();





        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void filtrarCartas() {
        painelCartas.removeAll();

        String texto = areaParaPesquisar.getText().toLowerCase();

        for (Personagem p : conta.getTodosPersonagens()) {

            // se quiser manter filtro de deck também:
            if (conta.getDeck().contains(p)) continue;

            //String nome = p.getNome().toLowerCase();

            String nome = normalizar(p.getNome());
            String busca = normalizar(areaParaPesquisar.getText());
            if (p.getDesbloqueado() && nome.contains(texto)) {
                painelCartas.add(criarCarta(p));
            }
        }

        painelCartas.revalidate();
        painelCartas.repaint();
    }

    private JPanel criarCarta(Personagem p) {

        JPanel cartaPanel = new JPanel(new BorderLayout());
        cartaPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cartaPanel.setPreferredSize(new Dimension(150, 260));
        
        
        
        PainelImagem imagem = new PainelImagem(
                new ImageIcon(getClass().getResource(p.getCaminhoImagem()))
        );
        imagem.setPreferredSize(new Dimension(150, 250));
        
        StringBuilder estrelas = new StringBuilder();
        for (int i = 0; i < p.getGrau(); i++) {
            estrelas.append("🌟");
        }

        JLabel nome = new JLabel(p.getNome() + " Nível: " + p.getNivel(), SwingConstants.CENTER);

        

        JLabel grau = new JLabel(estrelas + "", SwingConstants.CENTER);

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
            if (/*p.getDesbloqueado() && */!conta.getDeck().contains(p)) {
                disponiveis.add(p);
            }
        }

        for (Personagem p : disponiveis) {
            painelCartas.add(criarCarta(p));
        }

        painelCartas.revalidate();
        painelCartas.repaint();
    }




    private void abrirCatalogo() {
    	setVisible(false);
    	new TelaCatalogo(conta);
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


    

}
