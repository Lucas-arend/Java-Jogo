package com.game;

import com.game.Loja.TipoLoja;
import com.game.Loja.TipoValor;

public class ItemLoja {
    public String nome;
    public TipoLoja tipo;
    public TipoValor tipoVal;
    public int valor;

    public ItemLoja(String nome, TipoLoja tipo, TipoValor tipoVal, int valor) {
        this.nome = nome;
        this.tipo = tipo;
        this.tipoVal = tipoVal;
        this.valor = valor;
    }
}
