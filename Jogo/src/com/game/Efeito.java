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
        NERF_HEAL,

        BUFF_CRIT_CHANCE,
        NERF_CRIT_CHANCE,
        BUFF_CRIT_DAMAGE,
        NERF_CRIT_DAMAGE,
        STUN,
        SILENCE,
        ROOT,
        TAUNT,

        INVULNERABLE,
        IMMUNITY,

        REFLECT_DAMAGE,
        LIFESTEAL,

        
        NEUTRO
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
        REDUCAO_VELOCIDADE,
        ATAQUE,
        DEFESA,
        PROTECAO,
        CURA,
        AUMENTO_CURA,
        REDUCAO_CURA,
        CONTROLE,
        DOT,
        HOT,
        DILACERACAO,
        CRITICO_CHANCE,
        CRITICO_DANO,
        CONTROLE_TOTAL,
        CONTROLE_PARCIAL,
        UTILIDADE,
        REFLEXAO,
        ROUBO_VIDA, STUN, SILENCIO, PROVOCADO,
        NEUTRO
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
        case DAMAGE_OVER_TIME -> {
            int valorFinal = calcularValorFinal(alvo);
            alvo.danoDOT(valorFinal, atacante, time1, time2);
        }

        case HEAL_OVER_TIME -> alvo.curar(valor);
        default -> {}
        }
    }

    // =====================
    // BUFF / NERF
    // =====================
    public void applyImmediate(Personagem alvo) {
        if (applied) return;

        int valorFinal = calcularValorFinal(alvo);

        switch (kind) {

            case BUFF_ATTACK -> {
                valorAplicado = valorFinal;
                alvo.setMultiplicadorDano(
                    alvo.getMultiplicadorDano() + (valorAplicado / 100.0)
                );
            }

            case NERF_ATTACK -> {
                valorAplicado = valorFinal;
                alvo.setMultiplicadorDano(
                    alvo.getMultiplicadorDano() - (valorFinal / 100.0)
                );
            }

            case BUFF_DEFENSE -> {
                valorAplicado = valorFinal;
                alvo.setMultiplicadorDefesa(
                    alvo.getMultiplicadorDefesa() + (valorFinal / 100.0)
                );
            }

            case NERF_DEFENSE -> {
                valorAplicado = valorFinal;
                alvo.setMultiplicadorDefesa(
                    alvo.getMultiplicadorDefesa() - (valorFinal / 100.0)
                );
            }

            case BUFF_SPEED -> {
                valorAplicado = valorFinal;
                alvo.setMultiplicadorVelocidade(
                    alvo.getMultiplicadorVelocidade() + (valorFinal / 100.0)
                );
            }

            case NERF_SPEED -> {
                valorAplicado = valorFinal;
                alvo.setMultiplicadorVelocidade(
                    alvo.getMultiplicadorVelocidade() - (valorFinal / 100.0)
                );
            }

            case BUFF_HEAL -> {
                valorAplicado = valorFinal;
                alvo.setMultiplicadorCura(
                    alvo.getMultiplicadorCura() + (valorFinal / 100.0)
                );
            }

            case NERF_HEAL -> {
                valorAplicado = valorFinal;
                alvo.setMultiplicadorCura(
                    alvo.getMultiplicadorCura() - (valorFinal / 100.0)
                );
            }

            case BUFF_SHIELD -> {
                valorAplicado = valorFinal;
                alvo.ganharProtecao(valorAplicado);
            }

            case NERF_SHIELD -> {
                valorAplicado = Math.min(valorFinal, alvo.getProtecao());
                alvo.perderProtecao(valorAplicado);
            }
            case BUFF_CRIT_CHANCE -> {
                valorAplicado = valorFinal;
                alvo.setChanceCritico(
                    alvo.getChanceCritico() + valorFinal
                );
            }

            case NERF_CRIT_CHANCE -> {
                valorAplicado = valorFinal;
                alvo.setChanceCritico(
                    alvo.getChanceCritico() - valorFinal
                );
            }

            case BUFF_CRIT_DAMAGE -> {
                valorAplicado = valorFinal;
                alvo.setMultiplicadorCritico(
                    alvo.getMultiplicadorCritico() + (valorFinal / 100.0)
                );
            }

            case NERF_CRIT_DAMAGE -> {
                valorAplicado = valorFinal;
                alvo.setMultiplicadorCritico(
                    alvo.getMultiplicadorCritico() - (valorFinal / 100.0)
                );
            }
            case REFLECT_DAMAGE -> {
                valorAplicado = valorFinal;
                alvo.setRefletirDano(valorAplicado);
            }

            case LIFESTEAL -> {
                valorAplicado = valorFinal;
                alvo.setRouboDeVida(valorAplicado);
            }

            case INVULNERABLE -> alvo.setInvulneravel(true);

            case IMMUNITY -> alvo.setImune(true);
            
            case STUN -> alvo.setStun(true);

            case SILENCE -> alvo.setSilenciado(true);

            case ROOT -> alvo.setEnraizado(true);

            case TAUNT -> alvo.setProvocado(true);


            default -> {}
        }

        applied = true;
    }

    public int calcularValorFinal(Personagem alvo) {

        double valor = this.valor;

        switch (this.tag) {

            case REDUCAO_DANO:
                valor *= (1 - alvo.getResisRecucaoDano());
                break;

            case REDUCAO_VELOCIDADE:
                valor *= (1 - alvo.getResisReducaoVelocidade());
                break;

            case REDUCAO_CURA:
                valor *= (1 - alvo.getResisReducaoCura());
                break;

            case DOT:
                valor *= (1 - alvo.getResisDOT());
                break;

            case DILACERACAO:
                valor *= (1 - alvo.getResisDilaceracao());
                break;
        }

        return (int) valor;
    }

    
    public Categoria getCategoria() {
        return switch (kind) {

            case BUFF_ATTACK, BUFF_DEFENSE, BUFF_SPEED,
                 BUFF_CRIT_CHANCE, BUFF_CRIT_DAMAGE,
                 BUFF_HEAL, BUFF_SHIELD,
                 INVULNERABLE, IMMUNITY,
                 LIFESTEAL, REFLECT_DAMAGE -> Categoria.BUFF;

            case NERF_ATTACK, NERF_DEFENSE, NERF_SPEED,
                 NERF_CRIT_CHANCE, NERF_CRIT_DAMAGE,
                 DAMAGE_OVER_TIME,
                 STUN, SILENCE, ROOT, TAUNT -> Categoria.NERF;

            default -> Categoria.NEUTRO;
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

            case BUFF_DEFENSE ->
                alvo.setMultiplicadorDefesa(
                    alvo.getMultiplicadorDefesa() - (valorAplicado / 100.0)
                );

            case NERF_DEFENSE ->
                alvo.setMultiplicadorDefesa(
                    alvo.getMultiplicadorDefesa() + (valorAplicado / 100.0)
                );

            case BUFF_SPEED ->
                alvo.setMultiplicadorVelocidade(
                    alvo.getMultiplicadorVelocidade() - (valorAplicado / 100.0)
                );

            case NERF_SPEED ->
                alvo.setMultiplicadorVelocidade(
                    alvo.getMultiplicadorVelocidade() + (valorAplicado / 100.0)
                );

            case BUFF_HEAL ->
                alvo.setMultiplicadorCura(
                    alvo.getMultiplicadorCura() - (valorAplicado / 100.0)
                );

            case NERF_HEAL ->
                alvo.setMultiplicadorCura(
                    alvo.getMultiplicadorCura() + (valorAplicado / 100.0)
                );
            case BUFF_CRIT_CHANCE ->
            alvo.setChanceCritico(
                alvo.getChanceCritico() - valorAplicado
            );

           case NERF_CRIT_CHANCE ->
               alvo.setChanceCritico(
                    alvo.getChanceCritico() + valorAplicado
            );

        case BUFF_CRIT_DAMAGE ->
            alvo.setMultiplicadorCritico(
                alvo.getMultiplicadorCritico() - (valorAplicado / 100.0)
            );

        case NERF_CRIT_DAMAGE ->
            alvo.setMultiplicadorCritico(
                alvo.getMultiplicadorCritico() + (valorAplicado / 100.0)
            );


            case BUFF_SHIELD -> alvo.perderProtecao(valorAplicado);
            case NERF_SHIELD -> alvo.ganharProtecao(valorAplicado);
            
            case STUN -> alvo.setStun(false);
            case SILENCE -> alvo.setSilenciado(false);
            case ROOT -> alvo.setEnraizado(false);
            case TAUNT -> alvo.setProvocado(false);
            case REFLECT_DAMAGE -> alvo.setRefletirDano(0);
            case LIFESTEAL -> alvo.setRouboDeVida(0);
            case INVULNERABLE -> alvo.setInvulneravel(false);
            case IMMUNITY -> alvo.setImune(false);
            
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
