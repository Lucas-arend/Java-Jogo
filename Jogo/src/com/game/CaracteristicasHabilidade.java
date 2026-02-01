package com.game;

public enum CaracteristicasHabilidade {

	GOLPE_DIRETO,
	
    /* ===== DANO ===== */
    DANO_BAIXO,
    DANO,
    DANO_MEDIANO,
    DANO_CONSIDERAVEL,
    DANO_ALTO,
    DANO_LETAL,

    DOT,
    DOT_ALTO,
    
    /* ===== CURA ===== */
    BAIXA_CURA,
    CURA,
    CURA_MEDIANA,
    CURA_ALTA,
    CURA_PROLONGADA,

    /* ===== DEFESA / PROTEÇÃO ===== */
    PROTECAO,
    MENOS_PROTECAO,
    DEFESA,
    MENOS_DEFESA,

    /* ===== BUFFS ===== */
    AUMENTO_DE_ATAQUE,
    AUMENTO_DE_VIDA_MAXIMA,
    AUMENTO_DE_VELOCIDADE,

    /* ===== DEBUFFS ===== */
    REDUCAO_DE_ATAQUE,
    REDUCAO_DE_VIDA_MAXIMA,
    REDUCAO_DE_VELOCIDADE,

    /* ===== ESPECIAIS ===== */
    REVIVER,
    REDUZIR_COOLDOWN,
    AUMENTAR_COOLDOWN
    
    
    
    ,NULO
}
