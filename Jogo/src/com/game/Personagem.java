package com.game;

import java.util.*;
import com.game.Efeito.TagEfeito;

public abstract class Personagem {
	
	@SuppressWarnings("unused")
	private String caminhoImagem;
	private String nome;
	private long id;
	private int ataqueInicial;
	private int vidaInicial;
	private int defesaInicial;
	private int velocidadeInicial;
	private int protecaoInicial = 0;
	private int vidaMaximaInicial;
	private String descricao;
	private Raridade raridade;	
	private Tipo tipo;
	private Classe classe;
	private int nivel = 1;
	private int experiencia = 0;
	private int metaExperiencia = 100;
	private int grau = 0;
	private int copias = 0;
	private int metaCopias = 2;
	private int defesa;
	private int velocidade;
	private int protecao;
	private int vida;
	private int vidaMaxima;
	private int ataque;
	private int chanceCritico = 0;
	private double danoCritico = 1.25;
	private boolean vivo = true;
	private boolean desbloqueado = false;
	protected final StatusBase statusBase;
	private Personagem ultimoAtacante;
	
	private double multiplicadorDano = 1;
	private double multiplicadorCura = 1;
	
	//--RESISTÊNCIAS--//
	private double resisReducaoDano = 0;
	private double resisReducaoVelocidade = 0;
	private double resisReducaoDanoCritico = 0;
	private double resisReducaoChanceCritico = 0;
	private double resisReducaoCura = 0;
	private double resisDOT = 0;
	private double resisDilaceracao = 0;


	
	// Lista de efeitos ativos no personagem
	private List<Efeito> efeitosAtivos = new ArrayList<>();
	protected Habilidade[] habilidades = new Habilidade[4];


	//Construtor
	protected Personagem(
			long id,
	        String nome,
	        int nivel,
	        Tipo tipo,
	        Classe classe,
	        Raridade raridade,
	        String descricao,
	        StatusBase statusBase
	        ) {
	    this.statusBase = statusBase;
		this.id = id;
	    this.nome = nome;
	    this.tipo = tipo;
	    this.classe = classe;
	    this.raridade = raridade;
	    this.descricao = descricao;
	    // Status iniciais
	    this.vidaMaximaInicial = statusBase.vidaMaxima;
	    this.vidaInicial = statusBase.vida;
	    this.ataqueInicial = statusBase.ataque;
	    this.defesaInicial = statusBase.defesa;
	    this.velocidadeInicial = statusBase.velocidade;
	    this.protecaoInicial = statusBase.protecao;
	    this.chanceCritico = statusBase.chanceCritico;
	    this.danoCritico = statusBase.danoCritico;
	    
	    if(this.nivel > 1) {
	    	this.nivel = nivel;
	    } else this.nivel = 1;
	    this.setGrau(raridade.getGrauInicial());
	    resetarStatus();
	    adicionarImagem();
	}

	public void resistencias(double resisReducaoDano, double resisReducaoVelocidade, double resisReducaoDanoCritico, double resisReducaoChanceCritico, double resisReducaoCura, double resisDOT, double resisDilaceracao) {
		this.resisReducaoDano = resisReducaoDano;
		this.resisReducaoVelocidade = resisReducaoVelocidade;
		this.resisReducaoDanoCritico = resisReducaoDanoCritico;
		this.resisReducaoChanceCritico = resisReducaoChanceCritico;
		this.resisReducaoCura = resisReducaoCura;
		this.resisDOT = resisDOT;
		this.resisDilaceracao =resisDilaceracao;
	}
	
	//Getters

	public String getCaminhoImagem() {return caminhoImagem;}
	public String getNome() {return nome;}
	public int getVida() {return vida;}
	public int getAtaque() {return ataque;}
	public int getNivel() {return nivel;}
	public int getExperiencia() {return experiencia;}
	public int getMetaExperiencia() {return metaExperiencia;}
	public int getGrau() {return grau;}
	public int getCopias() {return copias;}
	public int getMetaCopias() {return metaCopias;}
	public Tipo getTipo() {return tipo;}
	public Classe getClasse() {return classe;}
	public String getDescricao() {return descricao;}
	public Raridade getRaridade() {return raridade;}
	public boolean getDesbloqueado() {return desbloqueado;} 
	public int getDefesa() {return defesa;}
	public int getVelocidade() {return velocidade;}
	public int getProtecao() {return protecao;}
	public long getId() {return id;}
	public int getVidaMaxima() {return vidaMaxima;}
    public int getVidaMaximaInicial() {return vidaMaximaInicial;}
    public int getVidaInicial() {return vidaInicial;}
    public int getAtaqueInicial() {return ataqueInicial;}
	public int getDefesaInicial() {return defesaInicial;}
	public int getVelocidadeInicial() {return velocidadeInicial;}
	public int getProtecaoInicial() {return protecaoInicial;}
    public int getValorDano(Habilidade h) {return h.getDano();}
    public int getValorCura(Habilidade h) {return h.getCura();}
    public int getDanoDOTPorTurno(Habilidade h) {return h.getDanoDOT();}
    public double getPorcentagemVida() {return (double) vida / vidaMaxima;}
    public double getMultiplicadorDano() {return multiplicadorDano;}
    public double getMultiplicadorCura() {return multiplicadorCura;}
	public boolean isVivo() {return vivo;}
	public String getHabilidadeNome1() {return habilidades[0].getNome();}
	public String getHabilidadeNome2() {return habilidades[1].getNome();}
	public String getHabilidadeNome3() {return habilidades[2].getNome();}
	public String getHabilidadeNome4() {return habilidades[3].getNome();}
	public String getHabilidadeDescricao1() {return habilidades[0].getDescricao();}
	public String getHabilidadeDescricao2() {return habilidades[1].getDescricao();}
	public String getHabilidadeDescricao3() {return habilidades[2].getDescricao();}
	public String getHabilidadeDescricao4() {return habilidades[3].getDescricao();}
	public List<Efeito> getEfeitosAtivos() { return new ArrayList<>(efeitosAtivos); }
	
	//--gets Resistências--//
	public double getResisRecucaoDano() {return this.resisReducaoDano;}
	public double getResisReducaoVelocidade() {return this.resisReducaoVelocidade;}
	public double getResisReducaoDanoCritico() {return this.resisReducaoDanoCritico;}
	public double getResisReducaoChanceCritico() {return this.resisReducaoChanceCritico;}
	public double getResisReducaoCura() {return this.resisReducaoCura;}
	public double getResisDOT() {return this.resisDOT;}
	public double getResisDilaceracao() {return this.resisDilaceracao;}
	
	
	//Setters
	protected void setCaminhoImagem(String caminhoImagem) {
	    if (caminhoImagem == null || caminhoImagem.isEmpty()) {
	        this.caminhoImagem = "/imagens/personagens/placeholder.png";
	    } else {
	        this.caminhoImagem = caminhoImagem;
	    }
	}
	public void setNome(String nome) {this.nome = nome;}
	public void setVida(int vida) {this.vida = vida;}
	public void setAtaque(int ataque) {this.ataque = ataque;}
	public void setNivel(int nivel) {this.nivel = nivel;}
	public void setExperiencia(int experiencia) {this.experiencia = experiencia;}
	public void setGrau(int grau) {this.grau = grau;}
	public void setCopias(int copias) {this.copias = copias;}
	public void setDescricao(String descricao) {this.descricao = descricao;}
	public void setHabilidades(Habilidade[] habilidades) {this.habilidades = habilidades;}
	public void setDefesa(int defesa) {this.defesa = defesa;}
	public void setVelocidade(int velocidade) {this.velocidade = velocidade;}
	public void setProtecao(int protecao) {this.protecao = protecao;}
	public void setVidaMaxima(int vidaMaxima) {this.vidaMaxima = vidaMaxima;}
	public void setMultiplicadorDano(double MD) {this.multiplicadorDano = MD;}
	public void setMultiplicadorCura(double MC) {this.multiplicadorCura = MC;}
	public void setVivo(boolean vivo) {this.vivo = vivo;}  
	
	//Gerenciamento
	public int ganharExperiencia(int exp) {
	    this.experiencia += exp;
	    int niveisGanhos = 0;

	    while (this.experiencia >= metaExperiencia) {
	        this.experiencia -= metaExperiencia;
	        this.nivel++;
	        niveisGanhos++;

	        metaExperiencia += (int)(metaExperiencia * 0.3);
	    }

	    return niveisGanhos;
	}

	
	public int ganharCopias(int cop) {
		this.copias += cop;
		return 0;
	}
	
	public void subirGrau() {
		this.grau++;
		this.copias -= metaCopias;
		this.metaCopias += metaCopias;
	}
	
	
	//Gerenciamento dos Status pelo nível (para ser mais fácil de balancear)
	public void StatusNivel() {
		resetarStatus();
		if (this.nivel < 1) {
			for(int i = 0; i <= this.nivel; i++) {
				this.ataque += (int) (statusBase.ataque * 1.1);
		        this.vidaMaxima += (int) (statusBase.vidaMaxima * 1.1);
		        this.vida += (int) (statusBase.vida * 1.1);
		        this.velocidade +=  nivel;
		        this.vidaInicial += (int) (statusBase.vida * 1.1);
		        this.ataqueInicial += (int)(statusBase.ataque * 1.1);
		        this.vidaMaximaInicial += (int)(statusBase.vidaMaxima * 1.1);
			}
		
		
		}
		
		/*resetarStatus();
		double valor1 = ((this.nivel - 1) * 0.1);
		this.ataque += (int) (statusBase.ataque * valor1);
		this.vidaMaxima += (int) (statusBase.vidaMaxima * valor1);
		this.vida += (int) (statusBase.vida * valor1);
		this.velocidade +=  nivel;
		this.vidaInicial += (int) (statusBase.vida * valor1);
		this.ataqueInicial += (int)(statusBase.ataque * valor1);
		this.vidaMaximaInicial += (int)(statusBase.vidaMaxima * valor1);*/
	}
	
	public void StatusGrau() {
		double valor1 = (this.grau * 0.3);
		for(int i = 0; i <= this.nivel; i++) {
			this.ataque += (int) (statusBase.ataque * 1.3);
		    this.vidaMaxima += (int) (statusBase.vidaMaxima * 1.3);
		    this.vida += (int) (statusBase.vida * 1.3);
		    this.velocidade += (grau * 3);
		    this.vidaInicial += (int) (statusBase.vida * 1.3);
		    this.ataqueInicial += (int)(statusBase.ataque * 1.3);
		    this.vidaMaximaInicial += (int)(statusBase.vidaMaxima * 1.3);
		}
		
	}
	
	protected void desbloquear() {
			    this.desbloqueado = true;
	}
	
	protected void resetarStatus() {
	    this.vidaMaxima = statusBase.vidaMaxima;
	    this.vida = statusBase.vida;
	    this.ataque = statusBase.ataque;
	    this.defesa = statusBase.defesa;
	    this.velocidade = statusBase.velocidade;
	    this.protecao = statusBase.protecao;
	    this.vivo = true;
	    this.multiplicadorDano = 1;
	    this.multiplicadorCura = 1;
	    efeitosAtivos.clear();

	    for (Habilidade h : habilidades) {
	        if (h != null) {
	            h.voltarCooldownInicial();
	        }
	    }
	}


	public void desbostar() {
	    resetarStatus();
	    efeitosAtivos.clear();
	}

	public abstract Personagem copiar();
	protected abstract void adicionarImagem();
	
	protected abstract void adicionarHabilidade(Habilidade copiar);
	
	protected abstract boolean Habilidade1(Habilidade habilidade1, Personagem alvo, Personagem atacante, List<Personagem> time1, List<Personagem> time2);
	
	protected abstract boolean Habilidade2(Habilidade habilidade2, Personagem alvo, Personagem atacante, List<Personagem> time1, List<Personagem> time2);

	protected abstract boolean Habilidade3(Habilidade habilidade3, Personagem alvo, Personagem atacante, List<Personagem> time1, List<Personagem> time2);

	protected abstract boolean Habilidade4(Habilidade habilidade4, Personagem alvo, Personagem atacante, List<Personagem> time1, List<Personagem> time2);

	
	protected abstract void aoAtacar(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2, int dano);
	
	protected abstract void aoSerAtacado(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2, int dano);
	
	protected abstract void AoNocautear(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2, int dano);
	
	protected abstract void Nocauteado(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2, int dano);
	
	protected abstract void inicioDoTurno(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2);
	
	protected abstract void fimDoTurno(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2);
	
	protected abstract String descricaoHabilidade1();
	protected abstract String descricaoHabilidade2();
	protected abstract String descricaoHabilidade3();
	protected abstract String descricaoHabilidade4();
	
	protected abstract String descricaoPassivas();

	//Batalha
	
	public final Personagem clonarParaBatalha() {
	    Personagem clone = this.copiar(); // cada subclasse já sabe se copiar

	    // 🔹 herdar progressão
	    clone.setNivel(this.getNivel());
	    clone.setGrau(this.getGrau());

	    // 🔹 recalcular status com base nisso
	    clone.resetarStatus();
	    clone.StatusNivel();
	    clone.StatusGrau();

	    return clone;
	}
	public void resetarCooldowns() {
	    for (Habilidade h : habilidades) {
	        h.resetarCooldown();
	    }
	}


	public boolean usarHabilidades(Personagem alvo, int valor, Personagem atacante, List<Personagem> time1, List<Personagem> time2) {
		Habilidade habilidade = habilidades[valor - 1];
		
		
	    if (!habilidade.podeUsar()) {
	        System.out.println(habilidade.getNome() + " está em recarga por "
	                + habilidade.getCooldownAtual() + " turno(s).");
	        return false; // ❌ NÃO gastou turno
	    }

	    habilidade.usar(); // ativa cooldown
	    
	    		// Implementação das habilidades do Vampiro
	    		if (valor == 1) {
	    			Habilidade1(habilidade, alvo, atacante, time1, time2);
	    			return true;
	    		}
	    		else if (valor == 2) {
	    			Habilidade2(habilidade, alvo, atacante, time1, time2);
	    			return true;
	    		} else if (valor == 3) {
	    			Habilidade3(habilidade, alvo, atacante, time1, time2);
	    			return true;
	    		} else if (valor == 4) {
	    			Habilidade4(habilidade, alvo, atacante, time1, time2);
	    			return true;
	    		}
	    		else {
	    			System.out.println("Habilidade inválida.");
	    		}
	    		
	    
	
		return true;
		
	};
	
	public void curar(int quantidade) {
	    if (!vivo) return;

	    quantidade = (int)(quantidade * multiplicadorCura);

	    vida = Math.min(vida + quantidade, vidaMaxima);
	}


	
	public void ganharProtecao(int quantidade) {
		this.protecao += quantidade;
	}
	
	public void perderProtecao(int quantidade) {
		this.protecao -= quantidade;
		if (this.protecao < 0) {
			this.protecao = 0;
		}
	}
	
	public boolean estaVivo() {
	    return vida > 0;
	}

	public void nocautear(Personagem adversario, List<Personagem> time1, List<Personagem> time2, boolean ativar, int dano) {
		if (vida > 0) {
			vida = 0;
		}
        if(vida == 0) {
			this.vida = 0;
			this.vivo = false;
		}
        if(ativar) this.Nocauteado(adversario, this, time1, null, dano);
        
		
	}
	
	public void reviver() {
		if (!this.vivo) {
			this.vivo = true;
			this.vida = (int) (vidaMaxima * 0.25); // Valor padrão de vida ao reviver
		}
	}
	
	public void setUltimoAtacante(Personagem Atacante) {
	    this.ultimoAtacante = Atacante;
	}
	
	public Personagem getultimoAtacante() {
		return this.ultimoAtacante;
	}
	
	// Aplica dano com consideração de defesa e proteção
	public int receberDano(int dano, Personagem atacante,  List<Personagem> time1, List<Personagem> time2) {
	    // 🔒 Segurança extra
	    if (!vivo) return 0;
	    int danoSofrido = this.vida;
	    int defesaFinal = Math.max(-100, Math.min(getDefesaFinal(), 100));
	    double fatorDefesa = 1.0 - (defesaFinal / 100.0);
	    int danoFinal;        
	    
	    danoFinal = (int) Math.floor(dano * fatorDefesa);
	    Random random = new Random();
	    int sorteio = random.nextInt(100);
        if (sorteio <= chanceCritico) {
        	danoFinal = (int) (danoFinal * this.danoCritico);
        	System.out.println("\n\n\n\nCritico\n\n\n\n");
        }
	    
	    // Proteção absorve primeiro
	    if (protecao > 0) {
	        int absorvido = Math.min(protecao, danoFinal);
	        protecao -= absorvido;
	        danoFinal -= absorvido;
	    }
	    // 🔥 Aplica dano
	    vida -= danoFinal;
	    danoSofrido -= vida;
	    
	    // 🔒 Nunca deixar HP negativo aqui
	    if (vida < 0) vida = 0;     
	    // 🔥 MORTE
	    if (vida == 0) {
	    	nocautear(atacante, time1, time2, true, danoSofrido);
	    }   
	    if (!vivo && atacante != null) {
	        atacante.AoNocautear(this, atacante, time1, time2, danoSofrido);
	    }

	    // 🔹 Passivas AO ATACAR
	    if (atacante != null) {
	        atacante.aoAtacar(this, atacante, time1, time2, danoSofrido);
	    }

	    // 🔹 Passivas AO RECEBER DANO
	    if (vida > 0 && atacante != null) {
	        this.aoSerAtacado(atacante, this, time1, time2, danoSofrido);
	    }

	    setUltimoAtacante(atacante);	
	    return danoSofrido;
	}
	

	
	public int danoDireto(int dano, Personagem atacante, List<Personagem> time1, List<Personagem> time2) {
		dano *= (int)(multiplicadorDano);
	    Random random = new Random();
	    int danoSofrido = this.vida;
	    int sorteio = random.nextInt(100);
        if (sorteio <= chanceCritico) {
        	dano = (int) (dano * this.danoCritico);
        	System.out.println("\n\n\n\nCritico\n\n\n\n");
        }
		this.vida -= dano;
		danoSofrido -= vida;
	    // 🔒 Nunca deixar HP negativo aqui
	    if (vida < 0) vida = 0;     
	    // 🔥 MORTE
	    if (vida == 0) {
	    	nocautear(atacante, time1, time2, true, danoSofrido);
	    }   
	    if (!vivo && atacante != null) {
	        atacante.AoNocautear(this, atacante, time1, time2, danoSofrido);
	    }

	    // 🔹 Passivas AO ATACAR
	    if (atacante != null) {
	        atacante.aoAtacar(this, atacante, time1, time2, danoSofrido);
	    }

	    // 🔹 Passivas AO RECEBER DANO
	    if (vida > 0 && atacante != null) {
	        this.aoSerAtacado(atacante, this, time1, time2, danoSofrido);
	    }

	    setUltimoAtacante(atacante);	
	    return danoSofrido;
	}

	public int danoDOT(int dano, Personagem atacante,  List<Personagem> time1, List<Personagem> time2) {
		if(!this.isVivo()) return 0;
		int danoSofrido = this.vida;
	    this.vida -= dano;
	    danoSofrido -= vida;
	    // 🔒 Nunca deixar HP negativo aqui
	    if (vida < 0) vida = 0;     
	    // 🔥 MORTE
	    if (vida == 0) {
	    	nocautear(atacante, time1, time2, true, danoSofrido);
	    }   
	    setUltimoAtacante(atacante);	
	    return danoSofrido;
	}


	/*public List<Passiva> getPassivas() {
	    return new ArrayList<>(passivas);
	}*/


	// Novos métodos para gerenciar efeitos
    public void aplicarEfeito(Efeito efeito) {
        Efeito copia = efeito.copiar();
        copia.applyImmediate(this);
        efeitosAtivos.add(copia);
    }
	
	// Processa um ciclo/turno: aplica ticks, decrementa duração, remove efeitos expirados
    public void processarEfeitosPorTurno() {
        Iterator<Efeito> it = efeitosAtivos.iterator();
        while (it.hasNext()) {
            Efeito e = it.next();
            List<Personagem> time1 = null;
			List<Personagem> time2 = null;
			e.applyTick(this, ultimoAtacante, time1, time2); // apenas DOT / HOT
            e.setDuracao(e.getDuracao() - 1);
            if (e.getDuracao() <= 0) {
                e.removeEffect(this);
                it.remove();
            }
        }
    }
    
    /* ================= DOT ================= */

    /**
     * Verifica se o personagem está sofrendo Dano por Turno (DOT)
     */
    public boolean estaSofrendoDOT() {
        return efeitosAtivos.stream().anyMatch(e ->
        e.getKind() == Efeito.Kind.DAMAGE_OVER_TIME
        );
    }

    /**
     * Verifica se o personagem morrerá apenas pelos DOTs atuais
     */
    public boolean previsaoMortePorDOT() {

        int danoTotalDOT = efeitosAtivos.stream()
            .filter(e ->
            e.getKind() == Efeito.Kind.DAMAGE_OVER_TIME
            )
            .mapToInt(e -> e.getValor() * e.getDuracao())
            .sum();

        return danoTotalDOT >= getVida();
    }

   
    public boolean morreNoProximoTurnoPorDOT() {

        int danoTurno = efeitosAtivos.stream()
            .filter(e ->
            e.getKind() == Efeito.Kind.DAMAGE_OVER_TIME
            )
            .mapToInt(Efeito::getValor)
            .sum();

        return getVida() - danoTurno <= 0;
    }


    
    public int getAtaqueFinal() {
        int ataqueFinal = ataque;
        ataqueFinal *= (int) (multiplicadorDano);
        return Math.max(0, ataqueFinal);
    }


    public int getVelocidadeFinal() {
        int velocidadeFinal = velocidade;
        for (Efeito e : efeitosAtivos) {
            if (e.getTipo() == Efeito.Kind.BUFF_SPEED)
                velocidadeFinal += e.getValor();
            if (e.getTipo() == Efeito.Kind.NERF_SPEED)
                velocidadeFinal -= e.getValor();
        }
        return Math.max(1, velocidadeFinal);
    }


    public int getDefesaFinal() {
        int defesaFinal = statusBase.defesa;
        for (Efeito e : efeitosAtivos) {
            if (e.getTipo() == Efeito.Kind.BUFF_DEFENSE)
                defesaFinal += e.getValor();

            if (e.getTipo() == Efeito.Kind.NERF_DEFENSE)
                defesaFinal -= e.getValor();
        }

        return Math.max(-1000, Math.min(defesaFinal, 100));
    }

    public Habilidade[] getHabilidades() {
        return habilidades;
    }
    
    public Habilidade getHabilidades(int index) {
        if (index < 0 || index >= habilidades.length) return null;
        return habilidades[index];
    }


    public boolean temEfeito(String b) {
        return efeitosAtivos.stream()
                .anyMatch(e -> e.getNomeEfeito().equalsIgnoreCase(b));
    }

    public List<Habilidade> getHabilidadesLista() {
        return Arrays.asList(habilidades);
    }
    
    public boolean temEfeito(Efeito.TagEfeito tag) {
        return efeitosAtivos.stream()
                .anyMatch(e -> e.getTag() == tag);
    }


    public String anularEfeitos() {
        Iterator<Efeito> it = efeitosAtivos.iterator();
        while (it.hasNext()) {
            Efeito e = it.next();
            if (e.getCategoria() == Efeito.Categoria.BUFF) {
                e.anular(this);
                it.remove();
            }
        }
        return nome + " teve seus buffs anulados!";
    }

    public String purificarEfeitos() {
        Iterator<Efeito> it = efeitosAtivos.iterator();
        while (it.hasNext()) {
            Efeito e = it.next();
            if (e.getCategoria() == Efeito.Categoria.NERF) {
                e.purificar(this);
                it.remove();
            }
        }

        return nome + " foi purificado!";
    }

    public void removerEfeitosPorTag(TagEfeito tag) {
        Iterator<Efeito> it = efeitosAtivos.iterator();
        while (it.hasNext()) {
            Efeito e = it.next();

            if (e.getTag() == tag) {
                e.anular(this);
                it.remove();
            }
        }
    }

    public void removerEfeitosPorNome(String nome) {
        Iterator<Efeito> it = efeitosAtivos.iterator();
        while (it.hasNext()) {
            Efeito e = it.next();

            if (e.getNomeEfeito() == nome) {
                e.anular(this);
                it.remove();
            }
        }
    }
	
	public void listarEfeitosAtivos() {
		if (efeitosAtivos.isEmpty()) {
			System.out.println("Nenhum efeito ativo em " + this.nome);
			return;
		}
		System.out.println("Efeitos ativos em " + this.nome + ":");
		for (Efeito e : efeitosAtivos) {
			e.listar();
		}
	}

	public boolean nomeEfeito(String nome) {
		for (Efeito e : efeitosAtivos) {
			if(e.getNomeEfeito() == nome) {
							return true;
			}
		}
		return false;
	}
	public int valorEfeito(String nome) {
		for (Efeito e : efeitosAtivos) {
			if(e.getNomeEfeito() == nome) {
				return e.getValor();
			}
		}
		return 0;
	}



	
	public void reduzirCooldowns() {
	    for (Habilidade h : habilidades) {
	        if (h != null) {
	            h.reduzirCooldown();
	        }
	    }
	}


	public String getEfeitosTexto() {
	    if (efeitosAtivos.isEmpty()) {
	        return "Nenhum efeito ativo";
	    }
	    StringBuilder sb = new StringBuilder();
	    for (Efeito e : efeitosAtivos) {
	        sb.append("- ")
	          .append(e.getNomeEfeito())
	          .append(" (")
	          .append(e.getDuracao())
	          .append(" turnos)")
	          .append(e.getDescricao())
	          .append("\n");
	    }
	    return sb.toString();
	}

	public Habilidade getHabilidade(int index) {
	    if (index < 0 || index >= habilidades.length) return null;
	    return habilidades[index];
	}
	
	public String[] getHabilidadesTexto() {
	    String[] textos = new String[4];
	    for (int i = 0; i < 4; i++) {
	        Habilidade h = habilidades[i];
	        if (h == null) {
	            textos[i] = "—";
	        } else if (h.podeUsar()) {
	            textos[i] = (i + 1) + " - " + h.getNome();
	        } else {
	            textos[i] = (i + 1) + " - " + h.getNome() +
	                        " (CD: " + h.getCooldownAtual() + ")";
	        }
	    }
	    return textos;
	}

	
	public String listarHabilidades() {
		String listaHabilidades = "\nHabilidades de " + this.nome + ": ";
		for (int i = 0; i < habilidades.length; i++) {
			Habilidade h = habilidades[i];
			if (h != null) {
				listaHabilidades += "\n" + (i + 1) + " - " + h.getNome() + ": " + h.getDescricao() +
						(h.podeUsar() ? "" : " (CD: " + h.getCooldownAtual() + ")");
			} else {
				listaHabilidades += (i + 1) + " - Vazio";
			}
		}
		return listaHabilidades;
	}
	
	public String barraDeVida() {
		int totalBarras = 20;
		int barrasCheias = (int) Math.round(((double) vida / vidaMaxima ) * totalBarras);
		int barrasVazias = totalBarras - barrasCheias;
		StringBuilder barra = new StringBuilder("[");
		if(!this.isVivo()) { barra.append("----------NOCAUTEADO!----------");}
		else {
			for (int i = 0; i < barrasCheias; i++) {
			barra.append("█");
		}
		for (int i = 0; i < barrasVazias; i++) {
			barra.append("░");
		}
		}
		
		
		barra.append("]");
		return barra.toString() + vida + "/" + vidaMaxima;
	}
	
	public String listarStatus() {
		return 	this.getNome() + "\n" +"Nível: " + getNivel() + "\n" +
				"Defesa:" + getDefesaFinal() + "%\n" + 
				"Protecão:" + getProtecao() + "\n" +
			    "Vida: " + barraDeVida()+
				"Ataque: " + getAtaqueFinal() + "\n" +
				"Velocidade: " + this.velocidade + "\n " + 
				"Efeitos: " + getEfeitosTexto();
				//listarHabilidades();
	}
	 public String listarResumoVida() {
		 return this.getNome() + "   Defesa:" + getDefesaFinal() + "%  Protecão:" + getProtecao() + "  Nível: " + getNivel() + "\nVida: " + barraDeVida();
	 }
	
	 
	 public String listarStatusUI() {
		    StringBuilder sb = new StringBuilder();
		    String grauS = "";
            for(int i = 0; this.getGrau() >= i; i++) {
            	grauS += "🌟";
            }

		    sb.append(nome).append("\n");
		    sb.append("Raridade: ").append(getRaridade()).append("\n");
		    sb.append("Grau: ").append(grauS).append("\nCartas obtidas: " + copias + "/" + metaCopias + "\n");
		    sb.append("Nível: ").append(nivel).append("   Xp: " + experiencia + "/" + metaExperiencia).append("\n");
		    sb.append("Tipo: ").append(getTipo()).append("\n");
		    sb.append("Classe: ").append(getClasse()).append("\n");
		    sb.append("Vida: ").append(barraDeVida()).append("\n");
		    sb.append("Ataque: ").append(getAtaqueFinal()).append("\n");
		    sb.append("Chance de critico: ").append(chanceCritico).append("%\n");
		    sb.append("Dano Crítico: ").append((int)(getAtaqueFinal() * danoCritico)).append(" = " + danoCritico * 100 + "%\n");
		    sb.append("Defesa: ").append(getDefesaFinal()).append("%\n");
		    sb.append("Proteção: ").append(protecao).append("\n");
		    sb.append("Velocidade: ").append(getVelocidadeFinal());

		    return sb.toString();
		}

	 public String listarHabilidadesUI() {
		    StringBuilder sb = new StringBuilder();

		    sb.append("Habilidades:\n\n");

		    for (int i = 0; i < habilidades.length; i++) {
		        Habilidade h = habilidades[i];

		        sb.append(i + 1).append(") ");

		        if (h == null) {
		            sb.append("Vazio\n\n");
		            continue;
		        }

		        sb.append(h.getNome()).append("\n");
		        sb.append(h.getDescricao()).append("\n");

		        sb.append("Cooldown Inicial: ")
		          .append(h.getCooldownInicial())
		          .append("\nCooldown:")
		          .append(h.getCooldownMax());

		        sb.append("\n\n");
		    }

		    return sb.toString();
	 }
	 public String listarPassivasUI() {
		 return "Passivas:\n" + descricaoPassivas();
		}


	 public String listarStatusUIBatalha() {
		    StringBuilder sb = new StringBuilder();
		    String grauS = "";
         for(int i = 0; this.getGrau() >= i; i++) {
         	grauS += "🌟";
         }

		    sb.append(nome).append("\n");
		    sb.append("Nível: ").append(nivel).append("\n");
		    sb.append("Grau: ").append(grauS).append("\n");

		    sb.append("Vida: ").append(barraDeVida()).append("\n");
		    sb.append("Ataque: ").append(getAtaqueFinal()).append("\n");
		    sb.append("Chance de critico: ").append(chanceCritico).append("%\n");
		    sb.append("Dano Crítico: ").append((int)(getAtaqueFinal() * danoCritico)).append(" = " + danoCritico * 100 + "%\n");
		    sb.append("Defesa: ").append(getDefesaFinal()).append("%\n");
		    sb.append("Proteção: ").append(protecao).append("\n");
		    sb.append("Velocidade: ").append(getVelocidadeFinal()).append("\n\n");
		    
		    sb.append("Passivas:\n").append(descricaoPassivas()).append("\n\n\n");

		    sb.append("Efeitos ativos:\n");

		    sb.append(getEfeitosTexto());

		    return sb.toString();
		}
	 
	 public String listarHabilidadesUIBatalha() {
		    StringBuilder sb = new StringBuilder();

		    sb.append("Habilidades:\n\n");

		    for (int i = 0; i < habilidades.length; i++) {
		        Habilidade h = habilidades[i];

		        sb.append(i + 1).append(") ");

		        if (h == null) {
		            sb.append("Vazio\n\n");
		            continue;
		        }

		        sb.append(h.getNome()).append("\n");
		        if(i == 0) {sb.append(descricaoHabilidade1()).append("\n");}
		        else if(i == 1) {sb.append(descricaoHabilidade2()).append("\n");}
		        else if(i == 2) {sb.append(descricaoHabilidade3()).append("\n");}
		        else sb.append(descricaoHabilidade4()).append("\n");
		        
		        sb.append("Cooldown: ")
		          .append(h.getRecargaAtual())
		          .append("/")
		          .append(h.getCooldownMax());

		        if (h.podeUsar()) {
		            sb.append("  ✔ Disponível");
		        } else {
		            sb.append("  ⏳ Em recarga");
		        }

		        sb.append("\n\n");
		    }

		    return sb.toString();
		}
	
	
	
	
	//#IA#//
	public boolean vidaBaixa() {
	    return vida <= vidaMaxima * 0.3;
	}

	public boolean temEfeitoNegativo() {
	    return efeitosAtivos.stream()
	        .anyMatch(e ->
	            e.getCategoria() == Efeito.Categoria.NERF ||
	            e.getKind() == Efeito.Kind.DAMAGE_OVER_TIME
	        );
	}

	public int getIndiceHabilidadeCura() {
	    for (int i = 0; i < habilidades.length; i++) {
	        if (habilidades[i] != null &&
	            habilidades[i].podeUsar() &&
	            habilidades[i].aplicaCura()) {
	            return i + 1;
	        }
	    }
	    return -1;
	}

	public int getIndiceHabilidadeDano() {
	    for (int i = 0; i < habilidades.length; i++) {
	        if (habilidades[i] != null &&
	            habilidades[i].podeUsar() &&
	            habilidades[i].aplicaDano()) {
	            return i + 1;
	        }
	    }
	    return -1;
	}
	public int getIndiceHabilidade(Habilidade habilidade) {
	    for (int i = 0; i < habilidades.length; i++) {
	        if (habilidades[i] != null &&
	            habilidades[i].getNome().equals(habilidade.getNome())) {
	            return i;
	        }
	    }
	    return -1;
	}
	
	public boolean compararId(long id) {
		if(this.id == id) return true;
		return false;
	}

}