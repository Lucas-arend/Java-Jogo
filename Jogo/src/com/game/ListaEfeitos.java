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

    public static Efeito curaProlongada(String nome,int valor, int duracao) {
        return new Efeito(
            nome,
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

    public static Efeito aumentoAtaque(String nome,int valor, int duracao) {
        return new Efeito(
            nome,
            valor,
            Efeito.Kind.BUFF_ATTACK,
            TagEfeito.DANO,
            duracao,
            "Aumenta o ataque em " + valor + "% por " + duracao + " turnos."
        );
    }

    public static Efeito reducaoAtaque(String nome,int valor, int duracao) {
        return new Efeito(
            nome,
            valor,
            Efeito.Kind.NERF_ATTACK,
            TagEfeito.REDUCAO_DANO,
            duracao,
            "Reduz o ataque em " + valor + "% por " + duracao + " turnos."
        );
    }

    // =====================
    // DEFESA / REDUÇÃO DE DANO
    // =====================

    public static Efeito aumentoDefesa(String nome,int valor, int duracao) {
        return new Efeito(
            nome,
            valor,
            Efeito.Kind.BUFF_DEFENSE,
            TagEfeito.DEFESA,
            duracao,
            "Aumenta a defesa em " + valor + " por " + duracao + " turnos."
        );
    }

    public static Efeito reducaoDefesa(String nome,int valor, int duracao) {
        return new Efeito(
            nome,
            valor,
            Efeito.Kind.NERF_DEFENSE,
            TagEfeito.DEFESA,
            duracao,
            "Reduz a defesa em " + valor + " por " + duracao + " turnos."
        );
    }

    // =====================
    // VELOCIDADE
    // =====================

    public static Efeito aumentoVelocidade(String nome,int valor, int duracao) {
        return new Efeito(
            nome,
            valor,
            Efeito.Kind.BUFF_SPEED,
            TagEfeito.VELOCIDADE,
            duracao,
            "Aumenta a velocidade em " + valor + "% por " + duracao + " turnos."
        );
    }

    public static Efeito desaceleracao(String nome,int valor, int duracao) {
        return new Efeito(
            nome,
            valor,
            Efeito.Kind.NERF_SPEED,
            TagEfeito.VELOCIDADE,
            duracao,
            "Reduz a velocidade em " + valor + "% por " + duracao + " turnos."
        );
    }

    // =====================
    // PROTEÇÃO / ESCUDO
    // =====================

    public static Efeito protecao(String nome,int valor, int duracao) {
        return new Efeito(
            nome,
            valor,
            Efeito.Kind.BUFF_SHIELD,
            TagEfeito.PROTECAO,
            duracao,
            "Aumenta a proteção em " + valor + " por " + duracao + " turnos."
        );
    }

    public static Efeito reducaoProtecao(String nome,int valor, int duracao) {
        return new Efeito(
            nome,
            valor,
            Efeito.Kind.NERF_SHIELD,
            TagEfeito.PROTECAO,
            duracao,
            "Reduz a proteção em " + valor + " por " + duracao + " turnos."
        );
    }
    
    
    
    public static Efeito reducaoCura(String nome,int valor, int duracao) {
    	return new Efeito(
    			nome,
    			valor,
    			Efeito.Kind.NERF_HEAL,
    			TagEfeito.REDUCAO_CURA,
    			duracao,
    			"Reduz a cura em " + valor + "% por " + duracao + " turnos."
    			);
    			
    }
    
    public static Efeito aumentoCura(String nome,int valor, int duracao) {
    	return new Efeito(
    			nome,
    			valor,
    			Efeito.Kind.BUFF_HEAL,
    			TagEfeito.AUMENTO_CURA,
    			duracao,
    			"Aumenta a cura em " + valor + "% por " + duracao + " turnos."
    			);
    			
    }
    
    
    
    // =====================
    // CRÍTICO
    // =====================

    public static Efeito aumentoChanceCritico(String nome,int valor, int duracao) {
        return new Efeito(
    			nome,
            valor,
            Efeito.Kind.BUFF_CRIT_CHANCE,
            TagEfeito.CRITICO_CHANCE,
            duracao,
            "Aumenta a chance de crítico em " + valor + "% por " + duracao + " turnos."
        );
    }

    public static Efeito reducaoChanceCritico(String nome,int valor, int duracao) {
        return new Efeito(
    			nome,
            valor,
            Efeito.Kind.NERF_CRIT_CHANCE,
            TagEfeito.CRITICO_CHANCE,
            duracao,
            "Reduz a chance de crítico em " + valor + "% por " + duracao + " turnos."
        );
    }

    public static Efeito aumentoDanoCritico(String nome,int valor, int duracao) {
        return new Efeito(
    			nome,
            valor,
            Efeito.Kind.BUFF_CRIT_DAMAGE,
            TagEfeito.CRITICO_DANO,
            duracao,
            "Aumenta o dano crítico em " + valor + "% por " + duracao + " turnos."
        );
    }

    public static Efeito reducaoDanoCritico(String nome,int valor, int duracao) {
        return new Efeito(
    			nome,
            valor,
            Efeito.Kind.NERF_CRIT_DAMAGE,
            TagEfeito.CRITICO_DANO,
            duracao,
            "Reduz o dano crítico recebido em " + valor + "% por " + duracao + " turnos."
        );
    }
    
    
 // =====================
 // CONTROLE DE MULTIDÃO
 // =====================

 public static Efeito stun(String nome,int duracao) {
     return new Efeito(
 			nome,
         0,
         Efeito.Kind.STUN,
         TagEfeito.STUN,
         duracao,
         "Fica incapacitado por " + duracao + " turnos."
     );
 }

 public static Efeito silence(String nome,int duracao) {
     return new Efeito(
 			nome,
         0,
         Efeito.Kind.SILENCE,
         TagEfeito.SILENCIO,
         duracao,
         "Não pode usar habilidades por " + duracao + " turnos."
     );
 }

 public static Efeito root(String nome,int duracao) {
     return new Efeito(
 			nome,
         0,
         Efeito.Kind.ROOT,
         TagEfeito.CONTROLE_PARCIAL,
         duracao,
         "Não pode trocar ou se mover por " + duracao + " turnos."
     );
 }

 public static Efeito provocacao(String nome,int duracao) {
     return new Efeito(
 			nome,
         0,
         Efeito.Kind.TAUNT,
         TagEfeito.CONTROLE,
         duracao,
         "É forçado a atacar o provocador."
     );
 }
 
 public static Efeito invulneravel(String nome,int duracao) {
	    return new Efeito(
    			nome,
	        0,
	        Efeito.Kind.INVULNERABLE,
	        TagEfeito.UTILIDADE,
	        duracao,
	        "Não recebe dano por " + duracao + " turnos."
	    );
	}

	public static Efeito imunidade(String nome,int duracao) {
	    return new Efeito(
    			nome,
	        0,
	        Efeito.Kind.IMMUNITY,
	        TagEfeito.UTILIDADE,
	        duracao,
	        "Imune a efeitos negativos."
	    );
	}
    
   
    public static Efeito rouboVida(String nome,int porcentagem, int duracao) {
        return new Efeito(
    			nome,
            porcentagem,
            Efeito.Kind.LIFESTEAL,
            TagEfeito.ROUBO_VIDA,
            duracao,
            "Recupera " + porcentagem + "% do dano causado."
        );
    }

    public static Efeito refletirDano(String nome,int porcentagem, int duracao) {
        return new Efeito(
    			nome,
            porcentagem,
            Efeito.Kind.REFLECT_DAMAGE,
            TagEfeito.REFLEXAO,
            duracao,
            "Reflete " + porcentagem + "% do dano recebido."
        );
    }
    public static Efeito efeitoNulo(String nome, int duracao, String descricao) {
    	return new Efeito(
    			nome,
    			0,
    			Efeito.Kind.NEUTRO,
    			TagEfeito.NEUTRO,
    			duracao, 
    			descricao
    			);
    }
}
