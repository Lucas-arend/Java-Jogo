package com.game.Telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.game.Conta;
import com.game.Personagem;

public class TelaInfoCard extends JFrame{
	
    private JTextArea txtStatusP1;
    
    Personagem p;
    Conta conta;
    private JTextArea txtDados;

	
	public TelaInfoCard(Conta conta, Personagem p) {
		this.p = p;
		p.StatusNivel();
		p.StatusGrau();
		this.conta = conta;
		setTitle("Informações de " + p.getNome() + "!");
		setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        txtDados = new JTextArea("Nível: " + conta.getNivel() + "  Nome: " + conta.getUsuario() + " Moedas: " + conta.getMoedas() + " Gemas: " + conta.getGemas());

        JPanel painelTopo = new JPanel();
        JPanel centro = new JPanel(new GridLayout(1, 2));
        JPanel painelInferior = new JPanel();


        JPanel painelP1 = criarPainelPersonagem(true);
        JPanel painel2 = new JPanel();

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.addActionListener(e -> voltar());
        
        // IMAGEM
        PainelImagem imagem = new PainelImagem(
        	    new ImageIcon(getClass().getResource(p.getCaminhoImagem()))
        	);

        	imagem.setPreferredSize(new Dimension(150, 250));

        	JButton botaoUparNivel = new JButton("Comprar Xp de Nível: " + ((p.getMetaExperiencia() - p.getExperiencia()) * 2) + " moedas");
        	botaoUparNivel.addActionListener(e -> comprarXpNivel());
        	JButton botaoUparGrau = new JButton("Subir de Grau: " + ((p.getGrau()) * 1000) + " moedas");
        	botaoUparGrau.addActionListener(e -> uparGrau());
        	painel2.add(botaoVoltar);
        	painel2.add(botaoUparNivel);
        	painel2.add(botaoUparGrau);
        	
        	painelTopo.add(txtDados);
            centro.add(painelP1);
            centro.add(imagem);
            painelInferior.add(painel2);
            
            atualizarDados();
            
            add(painelTopo);
            add(centro, BorderLayout.CENTER);
            add(painelInferior, BorderLayout.SOUTH);
        
            
            setLocationRelativeTo(null);
            setVisible(true);
		
	}
	
    private JPanel criarPainelPersonagem(boolean jogador) {

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        JTextArea txtStatus = criarTextArea();
        painel.add(new JLabel("STATUS"));
        painel.add(new JScrollPane(txtStatus));


       txtStatusP1 = txtStatus;



        return painel;
    }
    
    private JTextArea criarTextArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        return area;
    }

    /*private String Passivas() {
    	return p.listarPassivasUI();
    }*/
    
    private String Habilidades() {

    	return p.listarHabilidadesUI();
    }

    /* ================= ATUALIZAÇÃO ================= */
	private void atualizarDados() {
	    txtDados.setText(
	        "Nível: " + conta.getNivel() +
	        "  Nome: " + conta.getUsuario() +
	        " Moedas: " + conta.getMoedas() +
	        " Gemas: " + conta.getGemas()
	    );
	    if (p != null) {
            txtStatusP1.setText(p.listarStatusUI() + "\n\n" + p.listarPassivasUI() + "\n\n" + Habilidades());


        }
	    
	}
    
    private void comprarXpNivel() {
    	if(p.getDesbloqueado()) {
    		int valor = p.getMetaExperiencia() - p.getExperiencia();
    	    if(conta.gastarMoedas(valor * 2)) p.ganharExperiencia(valor);
    	    else System.out.println("Moedas insuficientes");
    	
            atualizarDados();
            }
    	else System.out.println("Personagem bloqueado!");

    	}
    
    private void uparGrau() {
    	if(p.getDesbloqueado()) {
    		if(p.getMetaCopias() <= p.getCopias()) {
    		    if(conta.gastarMoedas(p.getGrau() * 1000)) p.subirGrau();
    		    else System.out.println("Moedas insuficientes! ");
    	    }
    	    else System.out.println("Cópias insuficientes! ");
    	
            atualizarDados();
            }
    	else System.out.println("Personagem bloqueado!");
    	}
    
	private void voltar() {
		setVisible(false);
		new TelaSelecaoDeck(conta);
	}
}
