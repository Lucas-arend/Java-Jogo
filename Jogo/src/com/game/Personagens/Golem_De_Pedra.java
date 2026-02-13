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

public class Golem_De_Pedra extends Personagem{

	public Golem_De_Pedra(long id) {
		super(
	            id,
	            "Golem de Pedra",
	            1,
	            Tipo.TERRA,
	            Classe.TANQUE,
	            Raridade.RARO,
	            "Tem uma resistencia enorme e protege o time.",
	            new StatusBase(
	                5000, // vidaMaxima
	                5000,  // vida
	                200,  // ataque
	                50,   // defesa %
	                40,   // velocidade
	                0,   // proteção
	                25,   // chance de crítico
	                1.25   // dano crítico
	            )
	        );
		habilidades[0] = new Habilidade("Golpe resiliente", " causa " + this.getAtaqueFinal() + " dedano e aumenta sua defesa em 10% por 1 turno.", Arrays.asList(CaracteristicasHabilidade.DANO, CaracteristicasHabilidade.DEFESA), 0, 0);
	    habilidades[1] = new Habilidade("Absorção de defesa", "Reduz a defesa de todo o time adversário em 100% e aumenta a propria defesa de acordo com o total de defeza reduzido por 2 turnos.", Arrays.asList(CaracteristicasHabilidade.DEFESA, CaracteristicasHabilidade.MENOS_DEFESA), 1, 5); // começa em CD
	    habilidades[2] = new Habilidade("Proteção do time", "Distribui sua defesa entre os aliados por 2 turnos, porém o golem fica com sua defesa anulada por 2 turnos.", Arrays.asList(CaracteristicasHabilidade.DEFESA, CaracteristicasHabilidade.MENOS_DEFESA), 3, 4);
	    habilidades[3] = new Habilidade("Deslisamento", "Causa " + (int) (this.getAtaqueFinal() * 5) + " de dano em todos os adversários.", Arrays.asList(CaracteristicasHabilidade.DANO_LETAL), 4, 2); // começa em CD

	}

	@Override
	public Personagem copiar() {
	    Golem_De_Pedra copia = new Golem_De_Pedra(this.getId());

	    for (int i = 0; i < this.habilidades.length; i++) {
	        if (this.habilidades[i] != null) {
	            copia.habilidades[i] = this.habilidades[i].copiar();
	        }
	    }

	    return copia;
	}
	
	@Override
	public boolean usarHabilidades(Personagem alvo, int valor, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
		Habilidade habilidade = habilidades[valor - 1];
		
		

	    if (!habilidade.podeUsar()) {
	        System.out.println(habilidade.getNome() + " está em recarga por "
	                + habilidade.getCooldownAtual() + " turno(s).");
	        return false; // ❌ NÃO gastou turno
	    }

	    habilidade.usar(); // ativa cooldown
	    
	    
	    
	    
	 // Implementação das habilidades do Golem de Perdra
	 			if (valor == 1) {
					int danoInicial = this.getAtaqueFinal(); // Dano base da investida
					this.aplicarEfeito(ListaEfeitos.aumentoDefesa(10, 1));
	 				alvo.receberDano(danoInicial, this, time1, time2);
	 			}else if(valor == 2) {
	 				int val = time2.size();
	 				int def = 0;
	 				for(int i = 0; i < val; i++) {
	 					if(time2.get(i).getDefesa() > 0) {
	 						int temp = time2.get(i).getDefesa();
	 						time2.get(i).aplicarEfeito(ListaEfeitos.reducaoDefesa(temp, 2));
	 						def += temp;
	 					System.out.println(time2.get(i).getNome() + " Tem sua defesa reduzida em "+ temp + "% por "+ 1 + " turno).");
	 					}
	 					
	 				}
	 				this.aplicarEfeito(ListaEfeitos.aumentoDefesa(def, 2));
	 			}else if(valor == 3) {
	 				int val = this.getDefesaFinal();
	 				int def = (int) (val / (time1.size() - 1));
	 				this.aplicarEfeito(ListaEfeitos.reducaoDefesa(val, 2));
	 				for(int i = 0; i < time1.size(); i++) {
	 					if(time1.get(i).getNome() != this.getNome()) {
	 						time1.get(i).aplicarEfeito(ListaEfeitos.aumentoDefesa(def, 2));
	 					}
	 				}
	 			}else if(valor == 4) {
	 				int val = time2.size();
	 				int dano = (int) (this.getAtaqueFinal() * 5);
	 				for(int i = 0; i < val; i++) {
	 					if(time2.get(i).getDefesa() > 0) {
	 						time2.get(i).receberDano(dano, atacante, time1, time2);
	 					System.out.println(time2.get(i).getNome() + " recebe "+ dano + " de dano.");
	 					}
	 					
	 				}
	 			}
	 			else {
		System.out.println("Habilidade inválida.");
	}
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
	protected void aoAtacar(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void aoSerAtacado(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void AoNocautear(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void Nocauteado(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void inicioDoTurno(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		if (this.estaVivo()) {
					int val = time2.size();
		for(int i = 0; i < val; i++) {
			time2.get(i).aplicarEfeito(ListaEfeitos.reducaoCura(100, 1));
			System.out.println(time2.get(i).getNome() + " Tem sua cura reduzida em "+ 100 + "% por "+ 1 + " turno).");
		}
		int val2 = time1.size();
		for(int i = 0; i < val2; i++) {
			time1.get(i).aplicarEfeito(ListaEfeitos.reducaoCura(100, 1));
			if (time1.get(i).getNome() != this.getNome()) {
				System.out.println(time1.get(i).getNome() + " Tem sua cura reduzida em "+ 100 + "% por "+ 1 + " turno.");
				
			}else System.out.println(time1.get(i).getNome() + " Tem sua cura reduzida em "+ 100 + "% por "+ 1 + " turno).");
		}
		}

		
	}

	@Override
	protected void fimDoTurno(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		// TODO Auto-generated method stub
		
	}



	@Override
	protected void adicionarImagem() {
	    setCaminhoImagem("/resurces/Golem_De_Pedra.png");
		
	}

	@Override
	protected String descricaoPassivas() {
		// TODO Auto-generated method stub
		return "O " + getNome() + " reduz a cura de todos na batalha em 100%!";
	}

}
