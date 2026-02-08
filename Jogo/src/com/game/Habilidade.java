package com.game;

import java.util.ArrayList;
import java.util.List;

public class Habilidade {

	private int cooldownInicial;
    private int cooldownMax;
    private int cooldownAtual;
    private String descricao;
    private String nome;
    private int dano;
    private int cura;
    private int danoDOT;
    private int duracaoDOT;
    private List<CaracteristicasHabilidade> CH = new ArrayList<>();


    private final List<Efeito> efeitos = new ArrayList<>();

    public Habilidade(String nome, String descricao, List<CaracteristicasHabilidade> cH2, int cooldown, int cooldownMax) {
        this.nome = nome;
        this.descricao = descricao;
        this.cooldownInicial = cooldown;
        this.cooldownMax = cooldownMax;
        this.cooldownAtual = cooldown;
        this.CH = new ArrayList<>();
        this.CH.addAll(cH2);
    }

    /* ================= EFEITOS ================= */

    public void adicionarEfeito(Efeito efeito) {
        efeitos.add(efeito);
    }

    public List<Efeito> getEfeitos() {
        return efeitos;
    }

    public boolean aplicaCura() {
        return efeitos.stream().anyMatch(e ->
            e.getKind() == Efeito.Kind.HEAL_OVER_TIME ||
            e.getTag() == Efeito.TagEfeito.CURA
        );
    }

    public boolean aplicaDano() {
        return efeitos.stream().anyMatch(e ->
            e.getKind() == Efeito.Kind.DAMAGE_OVER_TIME ||
            e.getTag() == Efeito.TagEfeito.DANO
        );
    }
    
    public List<CaracteristicasHabilidade> getCaracteristicasHabilidade(){
    	return CH;
    }

    /* ================= COOLDOWN ================= */

    public boolean podeUsar() {
        return cooldownAtual == 0;
    }

    public void usar() {
        cooldownAtual = cooldownMax;
    }

    public void reduzirCooldown() {
        if (cooldownAtual > 0) cooldownAtual--;
    }

    public int getCooldownAtual() {
        return cooldownAtual;
    }
    
    public void voltarCooldownInicial() {
    	cooldownAtual = cooldownInicial;
    }
    
    public void resetarCooldown() {
        this.cooldownAtual = 0;
    }
    public int getCooldownInicial() {
    	return cooldownInicial;
    }
    
    public int getCooldownMax() {
    	return cooldownMax;
    }

    /* ================= GETTERS ================= */

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public int getDano() {
        return dano;
    }

    public int getCura() {
        return cura;
    }

    public int getDanoDOT() {
        return danoDOT;
    }

    public int getDuracaoDOT() {
        return duracaoDOT;
    }

    public int getRecargaAtual() {
        return cooldownAtual;
    }

    public boolean estaDisponivel() {
        return cooldownAtual == 0;
    }


    public Habilidade copiar() {
        Habilidade copia = new Habilidade(
            nome,
            descricao,
            CH,
            cooldownInicial, // ✅ correto
            cooldownMax
        );
        copia.cooldownAtual = cooldownInicial; // começa limpa
        efeitos.forEach(e -> copia.adicionarEfeito(e.copiar()));
        return copia;
    }

}
