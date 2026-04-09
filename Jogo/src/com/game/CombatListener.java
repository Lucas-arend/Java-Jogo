package com.game;

public interface CombatListener {

    void onHit(Personagem alvo, int dano);

    void onCrit(Personagem atacante);

    void onDeath(Personagem morto);

    void onStun(Personagem personagem);

    void onEffectApplied(Personagem alvo, String efeito);

    void onHeal(Personagem alvo, int valor);
    
    void reviver(Personagem alvo, int valor);

}