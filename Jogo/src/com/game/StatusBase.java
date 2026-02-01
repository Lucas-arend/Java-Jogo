package com.game;

public class StatusBase {

    public final int vidaMaxima;
    public final int vida;
    public final int ataque;
    public final int defesa;
    public final int velocidade;
    public final int protecao;
    public final int chanceCritico;
    public final double danoCritico;

    public StatusBase(int vidaMaxima,int vida, int ataque, int defesa, int velocidade, int protecao, int chanceCritico, double danoCritico) {
        this.vidaMaxima = vidaMaxima;
        this.vida = vida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.velocidade = velocidade;
        this.protecao = protecao;
        this.chanceCritico = chanceCritico;
		this.danoCritico = danoCritico;
    }
    
    public StatusBase(StatusBase outro) {
        this.vidaMaxima = outro.vidaMaxima;
        this.vida = outro.vida;
        this.ataque = outro.ataque;
        this.defesa = outro.defesa;
        this.velocidade = outro.velocidade;
        this.protecao = outro.protecao;
        this.chanceCritico = outro.chanceCritico;
		this.danoCritico = outro.danoCritico;
    }
}
