package com.game.Telas;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.game.Batalha;
import com.game.Conta;
import com.game.Habilidade;
import com.game.Personagem;

@SuppressWarnings("serial")
public class TelaBatalha extends JFrame {
	
	private Conta conta;

    private Batalha batalha;

    private JTextArea logArea;
    private JPanel painelCartasP1;
    private JPanel painelCartasP2;

    private JPanel painelCartaAtivaP1;
    private JPanel painelCartaAtivaP2;
    
    private JTextArea Nome1;
    private JTextArea Nome2;
    private JPanel painelCentral;
    private JTextArea statusP1;
    private JTextArea statusP2;

    private JProgressBar vidaP1;
    private JProgressBar vidaP2;
    private JButton trocar = new JButton();
    private JButton desistir = new JButton();
    
    private JButton[] botoes = new JButton[5];

    public TelaBatalha(Batalha batalha) {
        this.batalha = batalha;

        this.conta = batalha.getConta();
        setTitle("Batalha");
        setSize(1000, 700);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(criarPainelInimigos(), BorderLayout.WEST);
        add(criarCentroBatalha(), BorderLayout.CENTER);
        add(criarPainelJogador(), BorderLayout.EAST);

        atualizarTela();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel criarPainelInimigos() {
        JPanel base = new JPanel(new BorderLayout());

        Nome2 = new JTextArea("Nome2");
        painelCartasP2 = new JPanel();
        painelCartasP2.setLayout(new BoxLayout(painelCartasP2, BoxLayout.Y_AXIS));

        desistir = criarBotao("DESISTIR", 6);
        base.add(Nome2);
        base.add(painelCartasP2, BorderLayout.CENTER);

        base.add(desistir, BorderLayout.SOUTH);
        return base;
    }

    private JPanel criarPainelJogador() {
        JPanel base = new JPanel(new BorderLayout());
        

        painelCartasP1 = new JPanel();
        painelCartasP1.setLayout(new BoxLayout(painelCartasP1, BoxLayout.Y_AXIS));

        trocar = criarBotao("TROCAR", 5);
        
        base.add(painelCartasP1, BorderLayout.CENTER);

        base.add(trocar, BorderLayout.SOUTH);

        return base;
    }

    private JPanel criarCentroBatalha() {
        JPanel centro = new JPanel(new BorderLayout());
        
        painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));

        JPanel wrapperCartas = new JPanel(new BorderLayout());
        wrapperCartas.add(criarPainelPersonagem(true), BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(
        		wrapperCartas,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        centro.add(scroll, BorderLayout.WEST);
        
        painelCartaAtivaP2 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        painelCartaAtivaP1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
    
        
        JPanel wrapperCartas2 = new JPanel(new BorderLayout());
        wrapperCartas2.add(criarPainelPersonagem(false), BorderLayout.NORTH);

        JScrollPane scroll2 = new JScrollPane(
        		wrapperCartas2,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        painelCentral.add(painelCartaAtivaP2);
        painelCentral.add(criarLogCombate());
        painelCentral.add(painelCartaAtivaP1);

        
        centro.add(painelCentral, BorderLayout.CENTER);
        centro.add(scroll2, BorderLayout.EAST); 
        centro.add(criarBotoes(), BorderLayout.SOUTH);


       
        return centro;
    }

    private JPanel criarPainelPersonagem(boolean jogador) {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        JTextArea status = criarAreaTexto();
        JProgressBar vida = new JProgressBar();
        vida.setStringPainted(true);


        if (jogador) {
            statusP1 = status;
            vidaP1 = vida;
        } else {
            statusP2 = status;
            vidaP2 = vida;
        }

        painel.add(vida);
        painel.add(status);
        return painel;
    }

    private JScrollPane criarLogCombate() {
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setForeground(Color.BLACK);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBorder(BorderFactory.createTitledBorder("LOG DE COMBATE"));
        scroll.setPreferredSize(new Dimension(300, 200));
        return scroll;
    }

    private JPanel criarBotoes() {
        JPanel painel = new JPanel(new GridLayout(1,5,5,5));
        painel.setBorder(new EmptyBorder(5,5,5,5));

        for(int i=0;i<4;i++){
            int idx = i+1;
            botoes[i] = criarBotao("—", idx);
            painel.add(botoes[i]);
        }

        //painel.add(botoes[3]);

        return painel;
    }

    private JButton criarBotao(String txt, int acao){
        JButton b = new JButton(txt);
        b.setFocusPainted(false);
        b.setForeground(Color.BLACK);
        b.setFont(new Font("Arial", Font.BOLD, 14));

        b.addActionListener(e -> executarAcao(acao));
        return b;
    }

    private void executarAcao(int acao){

    	batalha.limparLog();
    	if (acao == 5) {
    	    abrirTelaTroca();
    	    return;
    	}
        batalha.batalha(acao);

        animarDano();
        atualizarTela();

        
        // adiciona novas linhas do log
        for(String linha : batalha.getLog()){
            logArea.append(linha + "\n");
        }

        logArea.setCaretPosition(logArea.getDocument().getLength());

        if(batalha.batalhaEncerrada()){

            JOptionPane.showMessageDialog(this, batalha.getResultado());

            // fecha a tela atual
            dispose();

            // volta ao menu inicial
            SwingUtilities.invokeLater(() -> new TelaInicial(conta));
            batalha.getLog().clear();
        }
    }

    private void abrirTelaTroca() {

        JDialog dialog = new JDialog(this, "Escolher Personagem", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(0, 1, 5, 5));

        for (Personagem p : batalha.time1) {

            if (!p.isVivo() || p == batalha.getAtivo1())
                continue;

            JButton btn = new JButton(p.getNome() + " (" + p.getVida() + " HP)");

            btn.addActionListener(e -> {
            	long vivos = batalha.time1.stream()
            		    .filter(Personagem::isVivo)
            		    .count();

            		if (vivos <= 1) {
            		    JOptionPane.showMessageDialog(this, "Nenhum personagem disponível!");
            		    return;
            		}
                batalha.trocarPersonagem(p);

                atualizarTela();

                dialog.dispose();
            });

            dialog.add(btn);
        }

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private JTextArea criarAreaTexto(){
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setForeground(Color.BLACK);
        return area;
    }

    private void atualizarTela(){
        Personagem p1 = batalha.getAtivo1();
        Personagem p2 = batalha.getAtivo2();

        atualizarPersonagem(p1, vidaP1, statusP1);
        atualizarPersonagem(p2, vidaP2, statusP2);

        atualizarCartas();

        if(p1 != null){
            String[] habilidades = p1.getHabilidadesTexto();
            for(int i=0;i<4;i++){
                botoes[i].setText(habilidades[i]);
                Habilidade h = p1.getHabilidade(i);
                botoes[i].setEnabled(h!=null && h.podeUsar());
                botoes[i].setToolTipText(h!=null ? p1.getDescricaoHabilidade(i) + " \n Cooldawn(" + h.getCooldownAtual() + "/" + h.getCooldownMax() + ")" : "");
            }
        }
    }

    private void atualizarPersonagem(Personagem p, JProgressBar barra, JTextArea area){
        if(p==null) return;

        barra.setMaximum(p.getVidaMaxima());
        barra.setValue(p.getVida());
        barra.setString(p.getVida()+" / "+p.getVidaMaxima());

        double perc = p.getVida()/(double)p.getVidaMaxima();
        if(perc < 0.3) barra.setForeground(Color.RED);
        else if(perc < 0.6) barra.setForeground(Color.ORANGE);
        else barra.setForeground(Color.GREEN);

        area.setText(p.listarStatusUIBatalha()+"\n\nEfeitos:\n"+p.getEfeitosTexto() + "\n\nHabilidades:\n" + p.listarHabilidadesUIBatalha());
    }

    private void atualizarCartas(){
        painelCartasP1.removeAll();
        painelCartasP2.removeAll();
        painelCartaAtivaP1.removeAll();
        painelCartaAtivaP2.removeAll();

        for(Personagem p : batalha.time1){
        	if(p != batalha.getAtivo1()) painelCartasP1.add(new CartaPersonagem(p, p==batalha.getAtivo1()));
        }
        Personagem p1 = batalha.getAtivo1();
        if(p1 != null)
            painelCartaAtivaP1.add(new CartaPersonagem(p1, true));
        for(Personagem p : batalha.time2){
        	if(p != batalha.getAtivo2()) painelCartasP2.add(new CartaPersonagem(p, p==batalha.getAtivo2()));
        }

        Personagem p2 = batalha.getAtivo2();
        
        if(p2 != null)
            painelCartaAtivaP2.add(new CartaPersonagem(p2, true));
        painelCartasP1.revalidate();
        painelCartasP2.revalidate();
        painelCartaAtivaP1.revalidate();
        painelCartaAtivaP2.revalidate();
    }

    private void animarDano(){
        Color original = vidaP2.getForeground();
        vidaP2.setForeground(Color.WHITE);
        Timer t = new Timer(120, e -> vidaP2.setForeground(original));
        t.setRepeats(false);
        t.start();
    }
}


@SuppressWarnings("serial")
class CartaPersonagem extends JPanel {
	

    public CartaPersonagem(Personagem p, boolean ativo){
    	setToolTipText(criarTooltip(p));
        setPreferredSize(new Dimension(140,200));
        setLayout(new BorderLayout());

        JLabel nome = new JLabel(p.getNome(), SwingConstants.CENTER);
        nome.setForeground(Color.BLACK);
        nome.setFont(new Font("Arial", Font.BOLD, 12));

        JProgressBar vida = new JProgressBar();
        vida.setMaximum(p.getVidaMaxima());
        vida.setValue(p.getVida());
        vida.setStringPainted(true);

        vida.setPreferredSize(new Dimension(140, 18));
        if(p.getVida() > (int)(p.getVidaMaxima() * 0.8)) vida.setForeground(Color.GREEN);
        else if(p.getVida() <= (int)(p.getVidaMaxima() * 0.8) && p.getVida() >= (int)(p.getVidaMaxima() * 0.5)) vida.setForeground(Color.YELLOW);
        else if(p.getVida() <= (int)(p.getVidaMaxima() * 0.5) && p.getVida() >= (int)(p.getVidaMaxima() * 0.3)) vida.setForeground(Color.ORANGE);
        else if(p.getVida() <= (int)(p.getVidaMaxima() * 0.3)) vida.setForeground(Color.RED);

        vida.setFont(new Font("Arial", Font.BOLD, 10));
        
        
        ImageIcon icon = new ImageIcon(getClass().getResource(p.getCaminhoImagem()));
        Image img = icon.getImage().getScaledInstance(140, 160, Image.SCALE_SMOOTH);

        JLabel imagem = new JLabel(new ImageIcon(img));
        imagem.setHorizontalAlignment(JLabel.CENTER);

        add(nome, BorderLayout.NORTH);
        add(imagem, BorderLayout.CENTER);
        add(vida, BorderLayout.SOUTH);

        if(ativo)
            setBorder(BorderFactory.createLineBorder(Color.BLUE,3));
        else
            setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
    }
    
    private String criarTooltip(Personagem p) {

        StringBuilder sb = new StringBuilder("<html>");
        
        sb.append(p.listarStatusUIBatalha().replace("\n", "<br>"));

        sb.append("<b>Efeitos Ativos:</b><br>");
        sb.append(p.getEfeitosTexto().replace("\n","<br>"));

        return sb.toString();
    }
}
