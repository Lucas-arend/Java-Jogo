package com.game.Telas;

import java.awt.GridLayout;
import javax.swing.*;

import com.game.Conta;
import com.game.Personagem;

import java.util.List;

@SuppressWarnings("serial")
public class TelaSelecaoDeck extends JFrame {


	JButton btnSalvar = new JButton("Salvar");

    private Conta conta;

    public TelaSelecaoDeck(Conta conta) {
        this.conta = conta;

        setTitle("Selecione seu Deck");
        setSize(600, 400);

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvar());

        carregarPersonagens();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void carregarPersonagens() {
        getContentPane().removeAll();
        setLayout(new GridLayout(3, 2));

        for (Personagem p : conta.getTodosPersonagens()) {

            if (p.getDesbloqueado()) {
                JButton btn = new JButton(p.getNome());

                if (conta.getDeck().contains(p)) {
                    btn.setText("✔ " + p.getNome());
                }

                btn.addActionListener(e -> {
                    if (conta.getDeck().contains(p)) {
                        conta.removerDoDeck(p.getId());
                        btn.setText(p.getNome());
                    } else {
                        if (!conta.adicionarAoDeck(p.getId())) {
                            JOptionPane.showMessageDialog(this,
                                    "Deck cheio ou personagem inválido");
                            return;
                        }
                        btn.setText("✔ " + p.getNome());
                    }
                });

                add(btn);
            }
        }

        add(btnSalvar);

        revalidate();
        repaint();
    }

	private void salvar() {
        if (conta.getDeck().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Selecione pelo menos 1 personagem!");
            setVisible(false);
        }

        @SuppressWarnings("unused")
		List<Personagem> timeJogador = conta.criarDeckParaBatalha();
        setVisible(false);
        new TelaInicial(conta);
    }
    /*private void desbloquearAleatorio() {
    	conta.desbloquearPersonagemAleatorio(60, 29, 10, 1);
        carregarPersonagens(); // 🔥 agora funciona
    }*/


}
