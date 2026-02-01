package com.game.Personagens;

import java.util.Arrays;
import java.util.List;

import com.game.CaracteristicasHabilidade;
import com.game.Classe;
import com.game.Habilidade;
import com.game.ListaEfeitos;
import com.game.Personagem;
import com.game.Raridade;
import com.game.StatusBase;
import com.game.Tipo;

public class Vampiro extends Personagem {
	private long id;

	public Vampiro(long id) {
		super(
			id,
			"Vampiro",
			1,
			Tipo.SOMBRA,
			Classe.ASSASSINO,
			Raridade.RARO,
			"Um ser noturno que se alimenta da energia vital dos outros.",
			new StatusBase(
				1300, // vidaMaxima
				1300, // vida
				550,  // ataque
				0,   // defesa %
				90,   // velocidade
				0,     // proteção
                25,   // chance de crítico
                1.25   // dano crítico
			)
		);
		habilidades[0] = new Habilidade("Mordida Sanguinária", "Causa dano e recupera parte da vida perdida.", Arrays.asList(CaracteristicasHabilidade.DANO, CaracteristicasHabilidade.CURA), 0, 0);
		habilidades[1] = new Habilidade("Neblina Sombria", "Cura vida com o tempo e aumenta a vida máxima.", Arrays.asList(CaracteristicasHabilidade.AUMENTO_DE_VIDA_MAXIMA, CaracteristicasHabilidade.CURA_ALTA), 3, 4); // começa em CD
		habilidades[2] = new Habilidade("Garras Vorazes", "Um ataque rápido que causa dano e reduz a defesa do inimigo.", Arrays.asList(CaracteristicasHabilidade.DANO_CONSIDERAVEL, CaracteristicasHabilidade.MENOS_DEFESA), 2, 2);
		habilidades[3] = new Habilidade("Chama da Noite", "Causa dano contínuo ao inimigo por vários turnos.", Arrays.asList(CaracteristicasHabilidade.DOT), 0, 1); // começa em CD
	}

	@Override
	public Personagem copiar() {
		Vampiro copia = new Vampiro(id);

		// copia habilidades (cooldown isolado)
		for (int i = 0; i < this.habilidades.length; i++) {
			if (this.habilidades[i] != null) {
				copia.habilidades[i] = this.habilidades[i].copiar();
			}
		}

		return copia;
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
	    			System.out.println(this.getNome() + " usa Mordida Sanguinária em " + alvo.getNome() + "!");
	    			int danoInicial = this.getAtaqueFinal(); // Dano base da mordida
	    			alvo.receberDano(danoInicial, this, time1, time2);
	    			int vidaRecuperada = danoInicial / 2; // Recupera 50% do dano causado
	    			this.curar(vidaRecuperada);
	    			System.out.println(alvo.getNome() + " recebe " + danoInicial +
	    			        " de dano!");
	    			System.out.println(this.getNome() + " recupera " + vidaRecuperada +
	    			        " de vida!");
	    			return true;
	    		}
	    		else if (valor == 2) {
	    			System.out.println(this.getNome() + " usa Neblina Sombria!");
	    			// Aplica efeito de aumento de evasão
	    			int cura = (int) (this.getVidaMaxima() * 0.1);
	    			this.setVidaMaxima((int) (this.getVidaMaxima() + cura)); // Exemplo de aumento de vida máxima
	    			this.curar((int) (this.getVidaMaxima() / 2));
	    			System.out.println(this.getNome() + " recebe aumento de 10% de vida máxima e cura 50% da vida máxima!");
	    			return true;
	    		} else if (valor == 3) {
	    			System.out.println(this.getNome() + " usa Garras Vorazes em " + alvo.getNome() + "!");
	    			int danoInicial = (int) (this.getAtaqueFinal() * 1.5); // Dano base do ataque
	    			alvo.receberDano(danoInicial, this, time1, time2);
	    			// Reduz a defesa do alvo
	    			alvo.aplicarEfeito(ListaEfeitos.reducaoDefesa(50, 3).copiar());
	    			System.out.println(alvo.getNome() + " recebe " + danoInicial +
	    			        " de dano e tem sua defesa reduzida!");
	    			return true;
	    		} else if (valor == 4) {
	    			System.out.println(this.getNome() + " usa Chama da Noite em " + alvo.getNome() + "!");
	    			// Aplica efeito de dano contínuo
	    			int dano = (int) (this.getAtaqueFinal() / 3);
	    			alvo.aplicarEfeito(ListaEfeitos.danoProlongado("Chama da Noite", dano, 3).copiar());
	    			System.out.println(alvo.getNome() + " sofrerá " + dano + "por 3 turnos!");
	    			return true;
	    		}
	    		else {
	    			System.out.println("Habilidade inválida.");
	    		}
	    		
	    
	
		return true;
	}
	


	@Override
	protected void adicionarHabilidade(Habilidade copiar) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void aoAtacar(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void aoSerAtacado(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void AoNocautear(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		this.curar((int) (adversario.getVidaMaxima() * 4));
		this.setAtaque((int)(getAtaque() * 1.1));		
	}

	@Override
	protected void Nocauteado(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
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

	

}
