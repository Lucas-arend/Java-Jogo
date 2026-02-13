package com.game;

import java.util.List;

public class Efeito {

    private final String nomeEfeito;
    private final Kind kind;

    private int valor;
    private int duracao;
    private int valorAplicado = 0;
    private final TagEfeito tag;
    private boolean applied;
    private String descricao;

    // Enum
    public enum Kind {
        DAMAGE_OVER_TIME,
        HEAL_OVER_TIME,

        BUFF_ATTACK,
        NERF_ATTACK,

        BUFF_DEFENSE,
        NERF_DEFENSE,

        BUFF_SPEED,
        NERF_SPEED,

        BUFF_SHIELD,
        NERF_SHIELD,

        BUFF_HEAL,
        NERF_HEAL
    }

    
    public enum Categoria{
    	BUFF,
    	NERF,
		NEUTRO
    }
    public enum TagEfeito {
        DANO,
        REDUCAO_DANO,
        VELOCIDADE,
        ATAQUE,
        DEFESA,
        PROTECAO,
        CURA,
        REDUCAO_CURA,
        CONTROLE,
        DOT,
        HOT
    }


    public Efeito(String nomeEfeito, int valor, Kind kind, TagEfeito tag, int duracao, String descricao) {
        this.nomeEfeito = nomeEfeito;
        this.valor = valor;
        this.kind = kind;
        this.tag = tag;
        this.duracao = duracao;
        this.applied = false;
        this.descricao = descricao;
    }


    // =====================
    // GETTERS
    // =====================
    public int getValor() {
        return valor;
    }

    public int getDuracao() {
        return duracao;
    }

    public Kind getKind() {
        return kind;
    }

    public String getNomeEfeito() {
        return nomeEfeito;
    }
    
    public TagEfeito getTag() {
        return tag;
    }
    
    public String getDescricao() {
    	return descricao;
    }


    public boolean isApplied() {
        return applied;
    }

    // =====================
    // CICLO DO EFEITO
    // =====================
    public void reduzirDuracao() {
        duracao--;
    }

    public boolean expirou() {
        return duracao <= 0;
    }

    // =====================
    // DOT / HOT
    // =====================
    public void applyTick(Personagem alvo, Personagem atacante, List<Personagem> time1, List<Personagem> time2) {
        switch (kind) {
            case DAMAGE_OVER_TIME -> alvo.danoDOT(valor, atacante, time1, time2);
            case HEAL_OVER_TIME -> alvo.curar(valor);
            default -> {}
        }
    }

    // =====================
    // BUFF / NERF
    // =====================
    public void applyImmediate(Personagem alvo) {
        if (applied) return;

        switch (kind) {
        
        case BUFF_ATTACK -> {
            valorAplicado = valor;
            alvo.setMultiplicadorDano(
                alvo.getMultiplicadorDano() + (valor / 100.0)
            );
        }

        case NERF_ATTACK -> {
            valorAplicado = valor;
            alvo.setMultiplicadorDano(
                alvo.getMultiplicadorDano() - ((valor / 100.0) /*/ alvo.getResisRecucaoDano()*/)
            );
        }

        case BUFF_HEAL -> {
            valorAplicado = valor;
            alvo.setMultiplicadorCura(
                alvo.getMultiplicadorCura() + (valor / 100.0)
            );
        }

        case NERF_HEAL -> {
            valorAplicado = valor;
            alvo.setMultiplicadorCura(
                alvo.getMultiplicadorCura() - (valor / 100.0)
            );
        }

        case BUFF_SHIELD -> {
            valorAplicado = valor;
            alvo.ganharProtecao(valorAplicado);
        }

        case NERF_SHIELD -> {
            valorAplicado = Math.min(valor, alvo.getProtecao());
            alvo.perderProtecao(valorAplicado);
        }

 
            case BUFF_DEFENSE, NERF_DEFENSE -> {
                valorAplicado = valor;
            }

            case BUFF_SPEED, NERF_SPEED -> {
                valorAplicado = valor;
            }
            
            default -> {}
        }
        applied = true;
    }
    
    public Categoria getCategoria() {
        return switch (kind) {

            case BUFF_ATTACK,
                 BUFF_DEFENSE,
                 BUFF_SPEED,
                 BUFF_SHIELD -> Categoria.BUFF;

            case NERF_ATTACK,
                 NERF_DEFENSE,
                 NERF_SPEED,
                 NERF_SHIELD -> Categoria.NERF;

            default -> Categoria.NEUTRO; // DOT / HOT
        };
    }


    public void removeEffect(Personagem alvo) {
        if (!applied) return;

        switch (kind) {

            case BUFF_ATTACK ->
                alvo.setMultiplicadorDano(
                    alvo.getMultiplicadorDano() - (valorAplicado / 100.0)
                );

            case NERF_ATTACK ->
                alvo.setMultiplicadorDano(
                    alvo.getMultiplicadorDano() + (valorAplicado / 100.0)
                );

            case BUFF_HEAL ->
                alvo.setMultiplicadorCura(
                    alvo.getMultiplicadorCura() - (valorAplicado / 100.0)
                );

            case NERF_HEAL ->
                alvo.setMultiplicadorCura(
                    alvo.getMultiplicadorCura() + (valorAplicado / 100.0)
                );

            case BUFF_SHIELD -> alvo.perderProtecao(valorAplicado);
            case NERF_SHIELD -> alvo.ganharProtecao(valorAplicado);

            default -> {}
        }

        applied = false;
    }


    public void anular(Personagem alvo) {
        removeEffect(alvo); // remove impacto
        duracao = 0;
    }

    public void purificar(Personagem alvo) {
        anular(alvo);
    }

    
    //Setters
    public void setValor(int valor) {this.valor = valor;}
    public void setDuracao(int duracao) {this.duracao = duracao;}
    public void setApplied(boolean applied) {this.applied = applied;}
    public void setValorAplicado(int valorAplicado) {this.valorAplicado = valorAplicado;}
    
    
    //Getters
    public int getValorAplicado() {return valorAplicado;}
    public boolean getApplied() {return applied;}
    public int getDuracaoAtual() {return duracao;}
    public int getValorAtual() {return valor;}
    
    //Incrementar / Decrementar
    public void incrementarDuracao(int incremento) {this.duracao += incremento;}
    public void incrementarValor(int incremento) {this.valor += incremento;}
    public void decrementarValor(int decremento) {this.valor -= decremento;}
    

    // =====================
    // CÓPIA SEGURA
    // =====================
    public Efeito copiar() {
        return new Efeito(
            this.nomeEfeito,
            this.valor,
            this.kind,
            this.tag,
            this.duracao,
            this.descricao
        );
    }

    // =====================
    // DEBUG
    // =====================
    public void listar() {
        System.out.println(
            "Efeito: " + nomeEfeito +
            " | Tipo: " + kind +
            " | Valor: " + valor +
            " | Duração: " + duracao
        );
    }

	public Kind getTipo() {
		// TODO Auto-generated method stub
		return kind;
	}
}
