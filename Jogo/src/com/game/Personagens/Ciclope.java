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
public class Ciclope extends Personagem {
	public long id;
	
	public Ciclope(long id) {
		super(
				id,
				"Ciclope",
				1,
	            Tipo.TERRA,
	            Classe.GUERREIRO,
	            Raridade.EPICO,
	            "Um enorme guerreiro com apenas um olho e uma fome insaciavel",
	            new StatusBase(
	                2200, // vidaMaxima
	                2200,  // vida
	                800,  // ataque
	                15,   // defesa %
	                50,   // velocidade
	                0,   // proteção
	                25,   // chance de crítico
	                1.25   // dano crítico
	            )
	        );
		habilidades[0] = new Habilidade("Golpe brutal", " causa " + this.getAtaqueFinal() + " de dano.", Arrays.asList(CaracteristicasHabilidade.DANO), 0, 0);
	    habilidades[1] = new Habilidade("Golpe duplo desacelerador", "Causa pouco dano 2 vezes e reduz a velocidade do alvo em 100% por 2 turnos.", Arrays.asList(CaracteristicasHabilidade.DANO_BAIXO, CaracteristicasHabilidade.REDUCAO_DE_VELOCIDADE)
,0, 0); // começa em CD
	    habilidades[2] = new Habilidade("Fome", "Reduz o tempo de carga de Devorar em 1!",Arrays.asList(CaracteristicasHabilidade.REDUZIR_COOLDOWN), 0, 3);
	    habilidades[3] = new Habilidade("Devorar", "Noucateia o alvo instantaneamente e se cura com a Vida Máxima do alvo.", Arrays.asList( CaracteristicasHabilidade.GOLPE_DIRETO, CaracteristicasHabilidade.DANO_LETAL,CaracteristicasHabilidade.CURA), 5, 5); // começa em CD
				
	}
	
	public boolean usarHabilidades(Personagem alvo, int valor, Personagem atacante, List<Personagem> time1, List<Personagem> time2) {
		Habilidade habilidade = habilidades[valor - 1];
		
		if(!habilidade.podeUsar()) {
	        System.out.println(habilidade.getNome() + " está em recarga por "
	                + habilidade.getCooldownAtual() + " turno(s).");
	        return false; // ❌ NÃO gastou turno
	    }

	    habilidade.usar(); // ativa cooldown
		
	    if(valor == 1) {
	    	int dano = (int) (this.getAtaqueFinal() * 1.2);
	    	alvo.receberDano(dano, this, time1, time2);
	    	System.out.println(alvo.getNome() + " recebe " + dano + " de dano!");
	    } else if (valor == 2) {
	    	alvo.aplicarEfeito(com.game.ListaEfeitos.desaceleracao(100, 2));
	    	int dano = (int) (this.getAtaqueFinal() * 0.6);
	    	alvo.receberDano(dano, this, time1, time2);
	    	alvo.receberDano(dano, this, time1, time2);
	    	System.out.println(alvo.getNome() + " recebe " + dano + " de dano 2 vezes e perde 100% da velocidade por 2 turnos!");
	    } else if (valor == 3) {
	    	this.habilidades[3].reduzirCooldown();
	    	this.aplicarEfeito(ListaEfeitos.aumentoDefesa(25, 2));
	    } else {
	    	int cura = (int) (alvo.getVidaMaxima());
	    	alvo.danoDireto(alvo.getVidaMaxima(), this, time1, time2);
	    	this.setVidaMaxima((int) (this.getVidaMaxima() + (this.getVidaMaximaInicial() * 0.2)));
	    	this.curar(cura);
	    	System.out.println(alvo.getNome() + " é nocauteado e devorado por " + this.getNome() + " que rcupera vida equivalente à vida máxima de " + alvo.getNome() + " (" + cura + ")!");
	    }
	    
		
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected void adicionarHabilidade(Habilidade h) {
	    for (int i = 0; i < habilidades.length; i++) {
	        if (habilidades[i] == null) {
	            habilidades[i] = h;
	            return;
	        }
	    }
	}

	@Override
	public Personagem copiar() {
	    Ciclope copia = new Ciclope(this.getId());

	    for (int i = 0; i < this.habilidades.length; i++) {
	        if (this.habilidades[i] != null) {
	            copia.habilidades[i] = this.habilidades[i].copiar();
	        }
	    }

	    return copia;
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
		this.aplicarEfeito(ListaEfeitos.aumentoAtaque(100, 2));
		
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
