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

public class Zumbi extends Personagem{
	public long id;
	int bando = 3;

	public Zumbi(long id) {
		super(
				id,
				"Zumbi",
				1,
				Tipo.SOMBRA, 
				Classe.ASSASSINO, 
				Raridade.LENDARIO, 
				"Um grupo de zumbis, que aumenta seu bando ao derotar seu adverario. Quanto mais zumbis mais letal.",
				new StatusBase(
					    800, // vidaMaxima
		                800,  // vida
		                300,  // ataque
		                0,   // defesa %
		                70,   // velocidade
		                0,   // proteção
		                25,   // chance de crítico
		                1.25   // dano crítico
		            )
		        );
			habilidades[0] = new Habilidade("Ataque de infecção", "Um ataque que causa baixo dano, porém causa infecção.", Arrays.asList(CaracteristicasHabilidade.DANO_BAIXO, CaracteristicasHabilidade.DOT_ALTO), 0, 0);
		    habilidades[1] = new Habilidade("Ataque em bando", "Um ataque do bando de zumbis, quanto mais zumbis mais dano.", Arrays.asList(CaracteristicasHabilidade.DANO_ALTO), 1, 2); // começa em CD
		    habilidades[2] = new Habilidade("Horda infinita", "Aumenta a quantidade de zumbis do bando em 2.", Arrays.asList(CaracteristicasHabilidade.REVIVER), 2, 4);
		    habilidades[3] = new Habilidade("Devorar em grupo", "Causa dano de 20% da vida máxima do alvo por cada zumbi do bando.", Arrays.asList(CaracteristicasHabilidade.DANO_LETAL, CaracteristicasHabilidade.CURA_ALTA), 3, 3); // começa em CD
	}
    @Override
	public boolean usarHabilidades(Personagem alvo, int valor, Personagem atacante, List<Personagem> time1, List<Personagem> time2) {
		Habilidade habilidade = habilidades[valor - 1];

		if(alvo == null) {
			return false;
		}
		

	    if (!habilidade.podeUsar()) {
	        System.out.println(
	            habilidade.getNome() + " está em recarga por "
	            + habilidade.getCooldownAtual() + " turno(s)."
	        );
	        return false; // ❌ NÃO gastou turno
	    }

	    habilidade.usar(); // ativa cooldown
	    
	    if(valor == 1) {
	    	int dano = (int)(this.getAtaqueFinal()*0.75);
	    	int dot = (int)(this.getAtaqueFinal() * 0.25);
	    	alvo.receberDano(dano, this, time1, time2);
	    	if (alvo.nomeEfeito("infecção")) {
	    		return true;
	    	}
	    	else alvo.aplicarEfeito(ListaEfeitos.danoProlongado("Infecção",dot, 2).copiar());
	    	
	    }
	    else if (valor == 2) {
	    	int dano = (int)(this.getAtaqueFinal());
	    	for(int i = 0; i < bando; i++) {
	    		if(!alvo.isVivo()) return true;
	    		alvo.receberDano(dano, this, time1, time2);
	    	}
	    	
	    }
	    else if (valor == 3) {
	    	bando ++;
	    	bando ++;
	    	System.out.println("Um bando de " + bando + " zumbis!");
	    	
	    }
	    else if (valor == 4) {
	    	for(int i = 0; i < bando; i++) {
	    		if(!alvo.isVivo()) return true;
	    		alvo.danoDireto((int)(alvo.getVidaMaxima() / 5), this, time1, time2);	    	
	    		}
	    }
	    else System.out.println("Habilidade invalida!");

		return true;
	}
	@Override
	   public Personagem copiar() {
        Zumbi copia = new Zumbi(id);

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
	protected void aoAtacar(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void aoSerAtacado(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void AoNocautear(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		if(!adversario.isVivo()) {
			this.bando ++;
			this.setVidaMaxima(this.getVidaMaxima());	
			this.aplicarEfeito(ListaEfeitos.protecao(this.getVidaMaxima(), 2));
		}
	}
	@Override
	protected void Nocauteado(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		if (bando <= 0) return;
		this.setVidaMaxima(getVidaMaximaInicial());
	    this.setVida(this.getVidaInicial());
	    this.setAtaque(getAtaqueInicial());
	    this.purificarEfeitos();
	    this.anularEfeitos();
	    this.setVivo(true);
	    bando --;		
	}
	@Override
	protected void inicioDoTurno(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		if(this.isVivo()) {
			int efeitos = adversario.getEfeitosAtivos().size();
		for(int i = 0; i < efeitos; i++) {
			if(adversario.nomeEfeito("Infecção")) {
	    	int dot = (int)(adversario.valorEfeito("Infecção") * 2);
	    	adversario.removerEfeitosPorNome("Infecção");
	    	adversario.aplicarEfeito(ListaEfeitos.danoProlongado("Infecção", dot, 2).copiar());
		}
		}
		}
				
	}
	@Override
	protected void fimDoTurno(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {

		
	}

	

}
