/*package com.game.Telas;

import java.awt.*;
import javax.swing.*;
import com.game.Personagem;

@SuppressWarnings("serial")
public class CartaPersonagem extends JPanel {

    private JLabel lblImagem = new JLabel();
    private JLabel lblNome = new JLabel();
    private JLabel lblNivel = new JLabel();
    private JProgressBar barraVida = new JProgressBar();

    public CartaPersonagem() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(140, 220));
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        setBackground(new Color(40, 40, 40));

        lblImagem.setHorizontalAlignment(JLabel.CENTER);

        lblNome.setForeground(Color.WHITE);
        lblNome.setHorizontalAlignment(JLabel.CENTER);
        lblNome.setFont(new Font("Arial", Font.BOLD, 14));

        lblNivel.setForeground(Color.LIGHT_GRAY);
        lblNivel.setHorizontalAlignment(JLabel.CENTER);

        barraVida.setStringPainted(true);

        add(lblNome, BorderLayout.NORTH);
        add(lblImagem, BorderLayout.CENTER);

        JPanel rodape = new JPanel(new GridLayout(2,1));
        rodape.setOpaque(false);
        rodape.add(lblNivel);
        rodape.add(barraVida);

        add(rodape, BorderLayout.SOUTH);
    }

    public void atualizar(Personagem p, boolean ativo) {
        if (p == null) return;

        lblNome.setText(p.getNome());
        lblNivel.setText("Nv " + p.getNivel());

        barraVida.setMaximum(p.getVidaMaxima());
        barraVida.setValue(p.getVida());
        barraVida.setString(p.getVida() + "/" + p.getVidaMaxima());

        ImageIcon icon = new ImageIcon(getClass().getResource(p.getCaminhoImagem()));
        Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        lblImagem.setIcon(new ImageIcon(img));

        if (ativo)
            setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        else
            setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
    }
}
*/