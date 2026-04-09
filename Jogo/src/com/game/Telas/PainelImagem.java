package com.game.Telas;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PainelImagem extends JPanel {

    private Image imagem;

    public PainelImagem(ImageIcon icon) {
        this.imagem = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagem == null) return;

        int w = getWidth();
        int h = getHeight();

        int imgW = imagem.getWidth(null);
        int imgH = imagem.getHeight(null);

        double scale = Math.min(
            (double) w / imgW,
            (double) h / imgH
        );

        int newW = (int)(imgW * scale);
        int newH = (int)(imgH * scale);

        int x = (w - newW) / 2;
        int y = (h - newH) / 2;

        g.drawImage(imagem, x, y, newW, newH, this);
    }
}
