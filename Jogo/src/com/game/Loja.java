package com.game;

import java.awt.Component;

import javax.swing.JOptionPane;

public class Loja {

    public enum TipoLoja {
        BAU, OFERTA
    }

    public static ItemLoja[] listarBaus() {
        return new ItemLoja[] {
            new ItemLoja("bau comum", TipoLoja.BAU),
            new ItemLoja("bau lendário", TipoLoja.BAU)
        };
    }

    public static ItemLoja[] listarOfertas() {
        return new ItemLoja[] {
            new ItemLoja("Oferta Inicial", TipoLoja.OFERTA)
        };
    }

    public static void comprar(Conta conta, ItemLoja item) {
        if (item.tipo == TipoLoja.BAU) {
            comprarBau(conta, item.nome);
        } else {
            comprarOferta(conta, item.nome);
        }
    }

    private static void comprarBau(Conta conta, String nome) {
        if ("bau comum".equals(nome)) {
        	JOptionPane.showMessageDialog(null, conta.desbloquearPersonagemAleatorio(7490, 2000, 500, 10));
        } else if ("bau lendário".equals(nome)) {
        	JOptionPane.showMessageDialog(null, conta.desbloquearPersonagemAleatorio(0, 0, 0, 100));
        }
    }

    private static void comprarOferta(Conta conta, String nome) {
        // futuras ofertas
    }
}
