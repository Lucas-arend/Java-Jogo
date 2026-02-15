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
		            Raridade.LENDARIO,
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
			habilidades[0] = new Habilidade("Rasgo Protetor", descricaoHabilidade1(), Arrays.asList(CaracteristicasHabilidade.DANO), 0, 0);
		    habilidades[1] = new Habilidade("Escamas Protetoras", descricaoHabilidade2(), Arrays.asList(CaracteristicasHabilidade.DEFESA), 1, 3); // começa em CD
		    habilidades[2] = new Habilidade("Sopro Flamejante", descricaoHabilidade3(), Arrays.asList(CaracteristicasHabilidade.DANO_ALTO, CaracteristicasHabilidade.DOT_ALTO), 5, 7);
		    habilidades[3] = new Habilidade("Rugido Aterrorizante", descricaoHabilidade4(), Arrays.asList(CaracteristicasHabilidade.AUMENTO_DE_ATAQUE), 2, 3); // começa em CD
		    
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
		protected void aoAtacar(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2, int dano) {
			// TODO Auto-generated method stub
			
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
		    setCaminhoImagem("/resurces/Dragao.png");
			
		}




		@Override
		protected String descricaoPassivas() {
			// TODO Auto-generated method stub
			return "Sem passivas disponíveis!";
		}




		@Override
		protected boolean Habilidade1(Habilidade habilidade1, Personagem alvo, Personagem atacante,
				List<Personagem> time1, List<Personagem> time2) {
			System.out.println(this.getNome() + " usa Rasgo em " + alvo.getNome() + "!");
			int danoInicial = this.getAtaqueFinal(); // Dano base da investida
			alvo.receberDano(danoInicial, this, time1, time2);
			System.out.println(alvo.getNome() + " recebe " + danoInicial +
			        " de dano!");
			return true;
		}




		@Override
		protected boolean Habilidade2(Habilidade habilidade2, Personagem alvo, Personagem atacante,
				List<Personagem> time1, List<Personagem> time2) {
			System.out.println(this.getNome() + " usa Escamas Protetoras!");
			this.aplicarEfeito(ListaEfeitos.aumentoDefesa(50, 3)); // +75 defesa por 2 turnos
			System.out.println(this.getNome() + " recebe um aumento de defesa de 50% por 3 turnos!");
			return true;
		}




		@Override
		protected boolean Habilidade3(Habilidade habilidade3, Personagem alvo, Personagem atacante,
				List<Personagem> time1, List<Personagem> time2) {
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
			}			return true;
		}




		@Override
		protected boolean Habilidade4(Habilidade habilidade4, Personagem alvo, Personagem atacante,
				List<Personagem> time1, List<Personagem> time2) {
			System.out.println(this.getNome() + " usa Rugido Aterrorizante!");
			int aumentoAtaque = (int) (this.getAtaqueFinal() * 0.5);
			this.aplicarEfeito(ListaEfeitos.aumentoAtaque(50, 2)); // +50% ataque por 2 turnos

			System.out.println(this.getNome() + " tem seu ataque aumentado em "
			        + aumentoAtaque + " pontos!");
			return true;
		}


		@Override
		protected String descricaoHabilidade1() {
			// TODO Auto-generated method stub
			return "Causa " + this.getAtaqueFinal() + " dano ao seu adversário.";
		}


		@Override
		protected String descricaoHabilidade2() {
			// TODO Auto-generated method stub
			return "Aumenta a defesa do Dragão Ancião em 50% por 3 turnos.";
		}


		@Override
		protected String descricaoHabilidade3() {
			// TODO Auto-generated method stub
			return "Um ataque poderoso que causa " + (int)(this.getAtaqueFinal() * 3) + " de dano ao time inimigo e os deixa em chamas ( causa "+ (int) (this.getAtaqueFinal() * 1.5) + " de dano por 2 turnos).";
		}


		@Override
		protected String descricaoHabilidade4() {
			// TODO Auto-generated method stub
			return "Aumenta o ataque em 50% por 2 turnos.";
		}

		
}
