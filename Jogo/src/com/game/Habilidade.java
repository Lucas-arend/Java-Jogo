package com.game;

import java.util.ArrayList;
import java.util.List;

public class Habilidade {
	public enum TipoAlvo {
	    INIMIGO,
	    TODOS_INIMIGOS,
	    ALIADO,
	    TODOS_ALIADOS,
	    SI_MESMO
	}

	public enum TipoAnimacao {
	    ATAQUE,
	    MAGIA,
	    CURA,
	    BUFF,
	    DEBUFF,
	    ULTIMATE
	}

	public enum CategoriaHabilidade {
	    OFENSIVA,
	    DEFENSIVA,
	    SUPORTE,
	    CONTROLE,
	    FINALIZADORA
	}

    private int cooldownInicial;
    private int cooldownMax;
    private int cooldownAtual;
    private int tamanho = 0;

    
    private String nome;
    private Personagem personagem;
    //private String descricao;

    private List<CaracteristicasHabilidade> CH = new ArrayList<>();
    private final List<Efeito> efeitos = new ArrayList<>();

    // 🆕 sistema moderno
    private int prioridade = 0;
    private boolean ignoraSilencio = false;
    private boolean ignoraControle = false;

    private TipoAlvo alvo = TipoAlvo.INIMIGO;
    private TipoAnimacao animacao = TipoAnimacao.ATAQUE;
    private CategoriaHabilidade categoria = CategoriaHabilidade.OFENSIVA;

    private int pesoIA = 1; // importância para IA

    public Habilidade(String nome,
                      List<CaracteristicasHabilidade> ch,
                      int cooldownInicial,
                      int cooldownMax, 
                      Personagem p) {

        this.nome = nome;
        this.personagem = p;
        this.cooldownInicial = cooldownInicial;
        this.cooldownMax = cooldownMax;
        this.cooldownAtual = cooldownInicial;

        tamanho++;
        this.CH.addAll(ch);
    }

    /* ================= EFEITOS ================= */

    public void adicionarEfeito(Efeito efeito) {
        efeitos.add(efeito);
    }

    public List<Efeito> getEfeitos() {
        return efeitos;
    }

    public boolean causaDano() {
        return efeitos.stream().anyMatch(e ->
                e.getKind() == Efeito.Kind.DAMAGE_OVER_TIME ||
                e.getTag() == Efeito.TagEfeito.DANO
        );
    }

    public boolean causaCura() {
        return efeitos.stream().anyMatch(e ->
                e.getKind() == Efeito.Kind.HEAL_OVER_TIME ||
                e.getTag() == Efeito.TagEfeito.CURA
        );
    }

    public boolean aplicaControle() {
        return efeitos.stream().anyMatch(e ->
                e.getTag() == Efeito.TagEfeito.CONTROLE ||
                e.getTag() == Efeito.TagEfeito.CONTROLE_TOTAL ||
                e.getTag() == Efeito.TagEfeito.CONTROLE_PARCIAL
        );
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

    public void aumentarCooldown() {
    	cooldownAtual++;
    }
    public void resetarCooldown() {
        cooldownAtual = cooldownInicial;
    }
    
    public int size() {
    	return tamanho;
    }

    /* ================= EVENTOS ================= */

    public void onHit(Personagem usuario, Personagem alvo) {}

    public void onCrit(Personagem usuario, Personagem alvo) {}

    public void onKill(Personagem usuario, Personagem alvo) {}

    /* ================= GETTERS ================= */

    public String getNome() { return nome; }
    public int getCooldownAtual() { return cooldownAtual; }
    public int getCooldownMax() { return cooldownMax; }

    public int getPrioridade() { return prioridade; }
    public boolean ignoraSilencio() { return ignoraSilencio; }
    public boolean ignoraControle() { return ignoraControle; }

    public TipoAlvo getAlvo() { return alvo; }
    public TipoAnimacao getAnimacao() { return animacao; }
    public CategoriaHabilidade getCategoria() { return categoria; }

    public int getPesoIA() { return pesoIA; }

    /* ================= SETTERS (builder style) ================= */

    public Habilidade prioridade(int valor) {
        this.prioridade = valor;
        return this;
    }

    public Habilidade alvo(TipoAlvo alvo) {
        this.alvo = alvo;
        return this;
    }

    public Habilidade animacao(TipoAnimacao animacao) {
        this.animacao = animacao;
        return this;
    }

    public Habilidade categoria(CategoriaHabilidade categoria) {
        this.categoria = categoria;
        return this;
    }

    public Habilidade pesoIA(int peso) {
        this.pesoIA = peso;
        return this;
    }

    public Habilidade ignorarSilencio() {
        this.ignoraSilencio = true;
        return this;
    }

    public Habilidade ignorarControle() {
        this.ignoraControle = true;
        return this;
    }

    /* ================= CÓPIA ================= */

    public Habilidade copiar() {
        Habilidade copia = new Habilidade(
                nome,
                CH,
                cooldownInicial,
                cooldownMax,
                personagem
        );

        copia.cooldownAtual = cooldownInicial;
        copia.prioridade = prioridade;
        copia.ignoraSilencio = ignoraSilencio;
        copia.ignoraControle = ignoraControle;
        copia.alvo = alvo;
        copia.animacao = animacao;
        copia.categoria = categoria;
        copia.pesoIA = pesoIA;

        efeitos.forEach(e -> copia.adicionarEfeito(e.copiar()));

        return copia;
    }
    



    public List<CaracteristicasHabilidade> getCaracteristicasHabilidade(){
    	return CH;
    }
    
    public boolean estaDisponivel() {
        return cooldownAtual <= 0;
    }



}