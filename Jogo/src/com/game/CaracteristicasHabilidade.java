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
    DANO_EM_AREA,

    DOT,
    DOT_ALTO,
    
    /* ===== CURA ===== */
    REDUCAO_DE_CURA,
    AUMENTO_DE_CURA,
    CURA_BAIXA,
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
    AUMENTO_DA_CHANCE_DE_CRITICO,
    AUMENTO_DO_DANO_CRITICO,

    /* ===== DEBUFFS ===== */
    REDUCAO_DE_ATAQUE,
    REDUCAO_DE_VIDA_MAXIMA,
    REDUCAO_DE_VELOCIDADE,
    REDUCAO_DA_CHANCE_DE_CRITICO,
    REDUCAO_DO_DANO_CRITICO,

    /* ===== ESPECIAIS ===== */
    REVIVER,
    REDUZIR_COOLDOWN,
    AUMENTAR_COOLDOWN,
    ATORDOAR,
    INVOCAR
    
    
    
    ,NULO
}
