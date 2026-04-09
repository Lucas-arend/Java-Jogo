package com.game.Personagens;

import java.util.Arrays;
import java.util.List;

import com.game.CaracteristicasHabilidade;
import com.game.Classe;
import com.game.Familia;
import com.game.Habilidade;
import com.game.ListaEfeitos;
import com.game.Personagem;
import com.game.Raridade;
import com.game.StatusBase;
import com.game.Tipo;

public class T_Rex extends Personagem{
	public long id;

	public T_Rex(long id) {
		super(
	            id,
	            "T.Rex",
	            1,
	            Tipo.TERRA,
	            Classe.ASSASSINO,
	            Familia.DINOSSAURO,
	            Raridade.LENDÁRIO,
	            "ROAR",
	            new StatusBase(
	                2000, // vidaMaxima
	                2000,  // vida
	                600,  // ataque
	                0,   // defesa %
	                80,   // velocidade
	                0,   // proteção
	                40,   // chance de crítico
	                1.25   // dano crítico
	            )
	        );
		habilidades[0] = new Habilidade("Ataque de Fratura", Arrays.asList(CaracteristicasHabilidade.DANO), 0, 0, this);
	    habilidades[1] = new Habilidade("Lagarto Tirano", Arrays.asList(CaracteristicasHabilidade.AUMENTO_DE_VELOCIDADE, CaracteristicasHabilidade.PROTECAO), 0, 2, this); // começa em CD
	    habilidades[2] = new Habilidade("Rugido Dominante", Arrays.asList(CaracteristicasHabilidade.DANO_ALTO), 0, 2, this);
	    habilidades[3] = new Habilidade("Golpe de Devorar", Arrays.asList(CaracteristicasHabilidade.REDUCAO_DE_ATAQUE), 2, 3, this); // começa em CD
	    
	    setResisReducaoDano(1);
    }
    
    
	@Override
    public Personagem copiar() {
        T_Rex copia = new T_Rex(id);

        // copia habilidades (cooldown isolado)
        for (int i = 0; i < this.habilidades.length; i++) {
            if (this.habilidades[i] != null) {
                copia.habilidades[i] = this.habilidades[i].copiar();
            }
        }

        return copia;
    }

	
	@Override
	protected void adicionarHabilidade(Habilidade copiar) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void aoAtacar(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2, int dano) {
		
	}


	@Override
	protected void aoSerAtacado(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2, int dano) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void AoNocautear(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2, int dano) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void Nocauteado(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2, int dano) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void inicioDoTurno(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void fimDoTurno(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void adicionarImagem() {
	    setCaminhoImagem("/resurces/T_Rex.png");
		
	}


	@Override
	protected String descricaoPassivas() {
		// TODO Auto-generated method stub
		return "Sem passivas disponíveis!";
	}


	@Override
	protected boolean Habilidade1(Habilidade habilidade1, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
        alvo.receberDano(this.getAtaqueFinal(), this, time1, time2);
        alvo.aplicarEfeito(ListaEfeitos.reducaoDefesa("Redução de Defesa", 34, 2), this);
        alvo.aplicarEfeito(ListaEfeitos.reducaoCura("Redução de Cura", 34, 2), this);
        alvo.aplicarEfeito(ListaEfeitos.desaceleracao("Desaceleração", 34, 2), this);
        return true;
	}


	@Override
	protected boolean Habilidade2(Habilidade habilidade2, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
		this.aplicarEfeito(ListaEfeitos.aumentoDefesa("Aumento de Defesa", 30, 3), this);
		this.aplicarEfeito(ListaEfeitos.imunidade("Imunidade", 3), this);
		this.aplicarEfeito(ListaEfeitos.aumentoDanoCritico("Aumento do Dano Critico", 75, 3), this);
        return true;
	}


	@Override
	protected boolean Habilidade3(Habilidade habilidade3, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
        int dano = (int)(this.getAtaqueFinal() * 0.25);
        alvo.receberDano(dano, this, time1, time2);
        this.aplicarEfeito(ListaEfeitos.aumentoAtaque("Aumento de Dano", 50, 2), this);
        this.aplicarEfeito(ListaEfeitos.aumentoVelocidade("Aumento de Velocidade", 20, 2), this);
        
        return true;
	}


	@Override
	protected boolean Habilidade4(Habilidade habilidade4, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
		int dano = (int)(this.getAtaqueFinal() * 2);
        this.aplicarEfeito(ListaEfeitos.curaProlongada("Cura de Devorar", (int)(alvo.danoDireto(dano, this, time1, time2) * 0.2), 2), atacante);
        return true;
	}


	@Override
	protected String descricaoHabilidade1() {
		// TODO Auto-generated method stub
		return "Causa " + this.getAtaqueFinal() + " de dano ao alvo e reduz o sua defesa, velocidade e cura em 34% por 2 turnos.";
	}


	@Override
	protected String descricaoHabilidade2() {
		// TODO Auto-generated method stub
		return "Aumenta a defesa em 30% por 3 turnos, aumenta o dano crítico em 75 por 3 turnos e fica imuni a efeitos negativos por 3 turnos.";
	}


	@Override
	protected String descricaoHabilidade3() {
		// TODO Auto-generated method stub
		return "Causa " + (int) (this.getAtaqueFinal() * 0.25) + " de dano ao alvo e recebe +50% de dano por 2 turnos e 25% de velocidade por 2 turnos.";
	}


	@Override
	protected String descricaoHabilidade4() {
		// TODO Auto-generated method stub
		return "Um ataque poderoso que causa " + (int) (this.getAtaqueFinal() * 2) + " de dano ao alvo e recupera (20% do dano causado) em vida por 2 turnos.";
	}
    
}
