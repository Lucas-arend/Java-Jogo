package com.game.Personagens;

import com.game.Personagem;
import com.game.Raridade;
import com.game.StatusBase;
import com.game.Tipo;

import java.util.Arrays;
import java.util.List;

import com.game.CaracteristicasHabilidade;
import com.game.Classe;
import com.game.Habilidade;
import com.game.ListaEfeitos;

public class Raptor extends Personagem {
	public long id;

	public Raptor(long id) {
		super(
	            id,
	            "Raptor",
	            1,
	            Tipo.TERRA,
	            Classe.ASSASSINO,
	            Raridade.COMUM,
	            "Causa dano rápido e aplica efeitos de controle de multidão",
	            new StatusBase(
	                1200, // vidaMaxima
	                1200,  // vida
	                700,  // ataque
	                0,   // defesa %
	                150,   // velocidade
	                0,   // proteção
	                25,   // chance de crítico
	                1.25   // dano crítico
	            )
	        );
		habilidades[0] = new Habilidade("Ataque Furtivo", Arrays.asList(CaracteristicasHabilidade.DANO), 0, 0, this);
	    habilidades[1] = new Habilidade("Investida Rápida", Arrays.asList(CaracteristicasHabilidade.AUMENTO_DE_VELOCIDADE, CaracteristicasHabilidade.PROTECAO), 1, 3, this); // começa em CD
	    habilidades[2] = new Habilidade("Garras Cortantes", Arrays.asList(CaracteristicasHabilidade.DANO_ALTO), 2, 2, this);
	    habilidades[3] = new Habilidade("Rugido Intimidante", Arrays.asList(CaracteristicasHabilidade.REDUCAO_DE_ATAQUE), 2, 2, this); // começa em CD
	    
	    setResisReducaoDano(1);
    }
    
    
	@Override
    public Personagem copiar() {
        Raptor copia = new Raptor(id);

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
	    setCaminhoImagem("/resurces/Raptor.png");
		
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
        alvo.aplicarEfeito(ListaEfeitos.reducaoAtaque("Redução de Dano", 25, 2), this);
        return true;
	}


	@Override
	protected boolean Habilidade2(Habilidade habilidade2, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
		System.out.println(this.getNome() + " usa Investida Rápida!");
        this.aplicarEfeito(ListaEfeitos.aumentoVelocidade("Aumento de Velocidade", 30, 2), this);
        int protecao = (int) (this.getAtaqueFinal() * 1.5);
        this.aplicarEfeito(ListaEfeitos.protecao("Absorção", protecao, 2), this); // escudo temporário
        System.out.println(this.getNome() + " aumenta sua velocidade em 30 pontos por 2 turnos e ganha um escudo temporário de " + protecao + "!");
        return true;
	}


	@Override
	protected boolean Habilidade3(Habilidade habilidade3, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
		System.out.println(this.getNome() + " usa Garras Cortantes em " + alvo.getNome() + "!");
        int dano = this.getAtaqueFinal() * 2;
        alvo.receberDano(dano, this, time1, time2);
        System.out.println(alvo.getNome() + " recebe " + dano + " de dano!");		return true;
	}


	@Override
	protected boolean Habilidade4(Habilidade habilidade4, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
		System.out.println(this.getNome() + " usa Rugido Intimidante!");
        int reducaoAtaque = (int) (alvo.getAtaqueFinal());// reduz 100% do ataque
        alvo.aplicarEfeito(ListaEfeitos.reducaoAtaque("Redução de Dano", reducaoAtaque, 1), this);
        System.out.println(alvo.getNome() + " tem seu ataque reduzido em " + reducaoAtaque + " pontos por 1 turno!");	
        return true;
	}


	@Override
	protected String descricaoHabilidade1() {
		// TODO Auto-generated method stub
		return "Um ataque rápido que causa " + this.getAtaqueFinal() + " de dano ao alvo e reduz o seu dano em 25% por 2 turnos.";
	}


	@Override
	protected String descricaoHabilidade2() {
		// TODO Auto-generated method stub
		return "Aumenta a velocidade em 30% por 2 turnos e ganha " + (int) (this.getAtaqueFinal() * 1.5) + " de protecao por 2 turnos.";
	}


	@Override
	protected String descricaoHabilidade3() {
		// TODO Auto-generated method stub
		return "Um ataque poderoso que causa " + (int) (this.getAtaqueFinal() * 2) + " de dano ao alvo.";
	}


	@Override
	protected String descricaoHabilidade4() {
		// TODO Auto-generated method stub
		return "Reduz o ataque do alvo em 100% por 1 turno.";
	}
    
}
