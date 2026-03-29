package com.game;

import java.awt.Component;

import javax.swing.JOptionPane;

public class Loja {

    public enum TipoLoja {
        BAU, OFERTA
    }
    public enum TipoValor {
    	MOEDA, GEMA
    }

    public static ItemLoja[] listarBaus() {
        return new ItemLoja[] {
            new ItemLoja("bau comum", TipoLoja.BAU, TipoValor.MOEDA, 200),
            new ItemLoja("bau épico", TipoLoja.BAU, TipoValor.MOEDA, 5000),
            new ItemLoja("bau lendário", TipoLoja.BAU, TipoValor.GEMA, 500)
        };
    }

    public static ItemLoja[] listarOfertas() {
        return new ItemLoja[] {
        		new ItemLoja("1000 moedas", TipoLoja.OFERTA, TipoValor.GEMA, 60),
        };
    }

    public static void comprar(Conta conta, ItemLoja item) {
        if (item.tipo == TipoLoja.BAU) {
        	if(item.tipoVal == TipoValor.MOEDA) {
        		if (conta.gastarMoedas(item.valor) == true) comprarBau(conta, item.nome);
        		else System.out.println("Moedas insuficientes!");
        	}
        	else if(item.tipoVal == TipoValor.GEMA) {
        		if (conta.gastarGemas(item.valor)) comprarBau(conta, item.nome);
        		else System.out.println("Gemas insuficientes!");
        	}
            
        } else {
        	if (item.tipoVal == TipoValor.MOEDA) {
        		if (conta.gastarMoedas(item.valor) == true) comprarOferta(conta, item.nome);
        		else System.out.println("Moedas insuficientes!");
        	}
        	else if(item.tipoVal == TipoValor.GEMA) {
        		if (conta.gastarGemas(item.valor)) comprarOferta(conta, item.nome);
        		else System.out.println("Gemas insuficientes!");
        	}
            
        }
    }

    private static void comprarBau(Conta conta, String nome) {
        if ("bau comum".equals(nome)) {
        	JOptionPane.showMessageDialog(null, conta.desbloquearPersonagemAleatorio(7490, 2000, 500, 10));
        } else if ("bau lendário".equals(nome)) {
        	JOptionPane.showMessageDialog(null, conta.desbloquearPersonagemAleatorio(0, 0, 0, 100));
        } else if ("bau épico".equals(nome)) {
        	JOptionPane.showMessageDialog(null, conta.desbloquearPersonagemAleatorio(0, 0, 100, 0));
        }
    }

    private static void comprarOferta(Conta conta, String nome) {
        if ("1000 moedas".equals(nome)) {
        	conta.ganharMoedas(1000);
        }
    }
}
