package com.game.Telas;

import java.awt.*;
import javax.swing.*;

import com.game.Batalha;
import com.game.Habilidade;
import com.game.Personagem;

@SuppressWarnings("serial")
public class TelaBatalha extends JFrame {

    private Batalha batalha;

    private JTextArea txtStatusP1;
    private JTextArea txtStatusP2;
    private JTextArea txtStatusTimeP1;  
    private JTextArea txtStatusTimeP2;
    private JTextArea txtHabilidadesP1;
    private JTextArea txtHabilidadesP2;


    private JButton[] botoesHabilidades = new JButton[5];

    public TelaBatalha(Batalha batalha) {
        this.batalha = batalha;

        setTitle("Batalha");
        setSize(900, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /* ================= TOPO ================= */
        JPanel topo = new JPanel(new GridLayout(1, 2));
        JPanel painelP1 = criarPainelPersonagem(true);
        JPanel painelP2 = criarPainelPersonagem(false);

        topo.add(painelP1);
        topo.add(painelP2);

        add(topo, BorderLayout.CENTER);

        /* ================= BOTÕES ================= */
        JPanel painelBotoes = new JPanel(new GridLayout(1, 4));

        for (int i = 0; i < 4; i++) {
            int idx = i + 1;
            botoesHabilidades[i] = new JButton("—");

            botoesHabilidades[i].addActionListener(e -> {
                batalha.turnoJogador(idx);
                atualizarTela();

                if (batalha.batalhaEncerrada()) {
                    JOptionPane.showMessageDialog(this, batalha.getResultado());
                    dispose();
                    new TelaInicial(batalha.getConta()); // ⬅ VOLTA PRA TELA INICIAL
                }


            });

            painelBotoes.add(botoesHabilidades[i]);
        }
        botoesHabilidades[4] = new JButton("Trocar");

        botoesHabilidades[4].addActionListener(e -> {
            batalha.turnoJogador(5);
            atualizarTela();

            if (batalha.batalhaEncerrada()) {
                JOptionPane.showMessageDialog(this, batalha.getResultado());
                dispose();
                new TelaInicial(batalha.getConta()); // ⬅ VOLTA PRA TELA INICIAL
            }


        });
        painelBotoes.add(botoesHabilidades[4]);

        add(painelBotoes, BorderLayout.SOUTH);

        atualizarTela();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private JTextArea criarTextArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        return area;
    }
    private JTextArea StatusTime() {
    	JTextArea area = new JTextArea();
    	area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        return area;
    }
    private JTextArea Habilidades() {
    	JTextArea area = new JTextArea();
    	area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
    	return area;
    }

    /* ================= PAINEL PERSONAGEM ================= */
    private JPanel criarPainelPersonagem(boolean jogador) {

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        JTextArea txtStatus = criarTextArea();
        JTextArea txtStatusTime = StatusTime();
        JTextArea txtHabilidades = Habilidades();

        painel.add(new JLabel("STATUS TIME"));
        painel.add(new JScrollPane(txtStatusTime));
        painel.add(new JLabel("STATUS"));
        painel.add(new JScrollPane(txtStatus));
        painel.add(new JLabel("HABILIDADES"));
        painel.add(new JScrollPane(txtHabilidades));


        if (jogador) {
            txtStatusP1 = txtStatus;
            txtStatusTimeP1 = txtStatusTime;
            txtHabilidadesP1 = txtHabilidades;

        } else {
            txtStatusP2 = txtStatus;
            txtStatusTimeP2 = txtStatusTime;
            txtHabilidadesP2 = txtHabilidades;


        }

        return painel;
    }

    /* ================= ATUALIZAÇÃO ================= */
    private void atualizarTela() {

        Personagem p1 = batalha.getAtivo1();
        Personagem p2 = batalha.getAtivo2();

        /* ===== PLAYER ===== */
        if (p1 != null) {
            txtStatusP1.setText(p1.listarStatus());
            txtStatusTimeP1.setText("");
            int val = batalha.time1.size();
            for (int i = 0; i < val; i++) {
            	txtStatusTimeP1.setText(txtStatusTimeP1.getText() + batalha.time1.get(i).listarResumoVida() + "\n" );
            }
            txtHabilidadesP1.setText(p1.listarHabilidades());
            
            //System.out.println("Jogador:\n" + p1.listarStatus());


            String[] habilidades = p1.getHabilidadesTexto();
            for (int i = 0; i < 4; i++) {
                botoesHabilidades[i].setText(habilidades[i]);

                Habilidade h = p1.getHabilidade(i);
                botoesHabilidades[i].setEnabled(h != null && h.podeUsar());
            }
        }


        /* ===== INIMIGO ===== */
        if (p2 != null) {
            txtStatusP2.setText(p2.listarStatus());
            txtStatusTimeP2.setText("");
            int val = batalha.time2.size();
            for (int i = 0; i < val; i++) {
            	txtStatusTimeP2.setText(txtStatusTimeP2.getText() + batalha.time2.get(i).listarResumoVida() + "\n" );
            }
            txtHabilidadesP2.setText(p2.listarHabilidades());
            //System.out.println("IA:\n" + p2.listarStatus());

        }

    }
}
