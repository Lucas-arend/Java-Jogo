package com.game;

import com.game.Efeito.TagEfeito;

public abstract class ListaEfeitos {

    // =====================
    // DANO / CURA AO LONGO DO TEMPO
    // =====================

    public static Efeito danoProlongado(String nome, int dano, int duracao) {
        return new Efeito(
            nome,
            dano,
            Efeito.Kind.DAMAGE_OVER_TIME,
            TagEfeito.DOT,
            duracao,
            "Sofre " + dano + " de dano por " + duracao + " turnos."
        );
    }

    public static Efeito curaProlongada(int valor, int duracao) {
        return new Efeito(
            "Cura Rápida",
            valor,
            Efeito.Kind.HEAL_OVER_TIME,
            TagEfeito.HOT,
            duracao,
            "Cura " + valor + " de vida por " + duracao + " turnos."
        );
    }

    // =====================
    // ATAQUE
    // =====================

    public static Efeito aumentoAtaque(int valor, int duracao) {
        return new Efeito(
            "Berserk",
            valor,
            Efeito.Kind.BUFF_ATTACK,
            TagEfeito.ATAQUE,
            duracao,
            "Aumenta o ataque em " + valor + " por " + duracao + " turnos."
        );
    }

    public static Efeito reducaoAtaque(int valor, int duracao) {
        return new Efeito(
            "Intimidação",
            valor,
            Efeito.Kind.NERF_ATTACK,
            TagEfeito.ATAQUE,
            duracao,
            "Reduz o ataque em " + valor + " por " + duracao + " turnos."
        );
    }

    // =====================
    // DEFESA / REDUÇÃO DE DANO
    // =====================

    public static Efeito aumentoDefesa(int valor, int duracao) {
        return new Efeito(
            "Fortificação",
            valor,
            Efeito.Kind.BUFF_DEFENSE,
            TagEfeito.DEFESA,
            duracao,
            "Aumenta a defesa em " + valor + " por " + duracao + " turnos."
        );
    }

    public static Efeito reducaoDefesa(int valor, int duracao) {
        return new Efeito(
            "Desmoralização",
            valor,
            Efeito.Kind.NERF_DEFENSE,
            TagEfeito.REDUCAO_DANO,
            duracao,
            "Reduz a defesa em " + valor + " por " + duracao + " turnos."
        );
    }

    // =====================
    // VELOCIDADE
    // =====================

    public static Efeito aumentoVelocidade(int valor, int duracao) {
        return new Efeito(
            "Vento Acelerado",
            valor,
            Efeito.Kind.BUFF_SPEED,
            TagEfeito.VELOCIDADE,
            duracao,
            "Aumenta a velocidade em " + valor + " por " + duracao + " turnos."
        );
    }

    public static Efeito desaceleracao(int valor, int duracao) {
        return new Efeito(
            "Desaceleração",
            valor,
            Efeito.Kind.NERF_SPEED,
            TagEfeito.VELOCIDADE,
            duracao,
            "Reduz a velocidade em " + valor + " por " + duracao + " turnos."
        );
    }

    // =====================
    // PROTEÇÃO / ESCUDO
    // =====================

    public static Efeito protecao(int valor, int duracao) {
        return new Efeito(
            "Escudo",
            valor,
            Efeito.Kind.BUFF_SHIELD,
            TagEfeito.PROTECAO,
            duracao,
            "Aumenta a proteção em " + valor + " por " + duracao + " turnos."
        );
    }

    public static Efeito reducaoProtecao(int valor, int duracao) {
        return new Efeito(
            "Quebra Escudo",
            valor,
            Efeito.Kind.NERF_SHIELD,
            TagEfeito.PROTECAO,
            duracao,
            "Reduz a proteção em " + valor + " por " + duracao + " turnos."
        );
    }
    public static Efeito reducaoCura(int valor, int duracao) {
    	return new Efeito(
    			"Redução de cura",
    			valor,
    			Efeito.Kind.NERF_HEAL,
    			TagEfeito.REDUCAO_CURA,
    			duracao,
    			"Reduz a cura em " + valor + " por " + duracao + " turnos."
    			);
    			
    }
}
