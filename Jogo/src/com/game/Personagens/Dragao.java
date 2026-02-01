package com.game.Personagens;

import java.util.Arrays;
import java.util.List;

import com.game.CaracteristicasHabilidade;
import com.game.Classe;
import com.game.Efeito.TagEfeito;
import com.game.Habilidade;
import com.game.ListaEfeitos;
import com.game.Personagem;
import com.game.Raridade;
import com.game.StatusBase;
import com.game.Tipo;

public class Dragao extends Personagem {
	public long id;

	
		public Dragao(long id) {
			super(
		            id,
		            "Dragão",
		            1,
		            Tipo.FOGO,
		            Classe.TANQUE,
		            Raridade.EPICO,
		            "Causa dano em área e aplica queimadura",
		            new StatusBase(
		                3000, // vidaMaxima
		                3000,  // vida
		                500,  // ataque
		                30,   // defesa %
		                80,   // velocidade
		                0,   // proteção
		                25,   // chance de crítico
		                1.25   // dano crítico
		            )
		        );
			habilidades[0] = new Habilidade("Rasgo Protetor", " causa um dano normal e purifica redução de dano.", Arrays.asList(CaracteristicasHabilidade.DANO), 0, 0);
		    habilidades[1] = new Habilidade("Escamas Protetoras", "Aumenta a defesa do Dragão Ancião.", Arrays.asList(CaracteristicasHabilidade.DEFESA), 1, 3); // começa em CD
		    habilidades[2] = new Habilidade("Sopro Flamejante", "Um ataque poderoso que causa dano significativo ao time inimigo e os deixa em chamas.", Arrays.asList(CaracteristicasHabilidade.DANO_ALTO, CaracteristicasHabilidade.DOT_ALTO), 5, 7);
		    habilidades[3] = new Habilidade("Rugido Aterrorizante", "Aumenta o ataque temporariamente.", Arrays.asList(CaracteristicasHabilidade.AUMENTO_DE_ATAQUE), 2, 3); // começa em CD
		    
	}
		

		
		
		public boolean usarHabilidades(Personagem alvo, int valor, Personagem atacante, List<Personagem> time1, List<Personagem> time2) {
			Habilidade habilidade = habilidades[valor - 1];
			
			

		    if (!habilidade.podeUsar()) {
		        System.out.println(habilidade.getNome() + " está em recarga por "
		                + habilidade.getCooldownAtual() + " turno(s).");
		        return false; // ❌ NÃO gastou turno
		    }

		    habilidade.usar(); // ativa cooldown
			
			// Implementação das habilidades do Dragão Ancião
			if (valor == 1) {
				System.out.println(this.getNome() + " usa Rasgo em " + alvo.getNome() + "!");
				this.removerEfeitosPorTag(TagEfeito.DANO);
				int danoInicial = this.getAtaqueFinal(); // Dano base da investida
				alvo.receberDano(danoInicial, this, time1, time2);
				System.out.println(alvo.getNome() + " recebe " + danoInicial +
				        " de dano!");
			} else if (valor == 2) {
				System.out.println(this.getNome() + " usa Escamas Protetoras!");
				this.aplicarEfeito(ListaEfeitos.aumentoDefesa(50, 3)); // +75 defesa por 2 turnos
				System.out.println(this.getNome() + " recebe um aumento de defesa de 50% por 3 turnos!");
			} else if (valor == 3) {
				System.out.println(this.getNome() + " usa Sopro Flamejante!");
				int dano = (int) (this.getAtaqueFinal() * 3); // Dano base da investida
				int danoQueimadura = (int) (dano/2);
				int val = time2.size();
				for(int i = 0; i < val; i++) {
					System.out.println(time2.get(i).getNome() + " Vida: " + time2.get(i).getVida());
					time2.get(i).receberDano(dano, this, time1, time2);
					time2.get(i).aplicarEfeito(ListaEfeitos.danoProlongado("Queimaduras", danoQueimadura, 2));
					System.out.println("/" + time2.get(i).getVida());
					System.out.println(time2.get(i).getNome() + " sofre "+ dano + " de dano e queimadura ("+ danoQueimadura + "/turno).");
				}
				
			} else if (valor == 4) {
				System.out.println(this.getNome() + " usa Rugido Aterrorizante!");
				int aumentoAtaque = (int) (this.getAtaqueFinal() * 0.5);
				this.aplicarEfeito(ListaEfeitos.aumentoAtaque(50, 2)); // +50% ataque por 3 turnos

				System.out.println(this.getNome() + " tem seu ataque aumentado em "
				        + aumentoAtaque + " pontos!");

			} else {
				System.out.println("Habilidade inválida.");
			}
			return true; // ✅ gastou turno
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
		    Dragao copia = new Dragao(this.getId());

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
			// TODO Auto-generated method stub
			
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
