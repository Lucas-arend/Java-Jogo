package com.game.Combate;

public class Evento {

    public EventoCombate tipo;
    public String origem;
    public String alvo;
    public int valor;

    public Evento(EventoCombate tipo, String origem, String alvo, int valor) {
        this.tipo = tipo;
        this.origem = origem;
        this.alvo = alvo;
        this.valor = valor;
    }
}