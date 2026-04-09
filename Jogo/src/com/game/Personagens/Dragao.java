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

public class Dragao extends Personagem {
	public long id;

	
		public Dragao(long id) {
			super(
		            id,
		            "Dragão",
		            1,
		            Tipo.FOGO,
		            Classe.TANQUE,
		            Familia.DRAGAO,
		            Raridade.ÉPICO,
		            "Uma lendária criatura conhecida por suas escamas resistentes e a capacidade de assoprar chamas.",
		            new StatusBase(
		                3000, // vidaMaxima
		                3000,  // vida
		                600,  // ataque
		                25,   // defesa %
		                80,   // velocidade
		                0,   // proteção
		                25,   // chance de crítico
		                1.25   // dano crítico
		            )
		        );
			habilidades[0] = new Habilidade("Presença draconica", Arrays.asList(CaracteristicasHabilidade.PROTECAO,CaracteristicasHabilidade.REDUCAO_DE_VELOCIDADE), 0, 0, this);
		    habilidades[1] = new Habilidade("Escamas Protetoras", Arrays.asList(CaracteristicasHabilidade.DEFESA, CaracteristicasHabilidade.AUMENTO_DE_ATAQUE), 1, 3, this); // começa em CD
		    habilidades[2] = new Habilidade("Sopro Flamejante", Arrays.asList(CaracteristicasHabilidade.DANO_LETAL, CaracteristicasHabilidade.DOT_ALTO, CaracteristicasHabilidade.DANO_EM_AREA), 7, 5, this);
		    habilidades[3] = new Habilidade("Rugido Aterrorizante", Arrays.asList(CaracteristicasHabilidade.DANO_MEDIANO, CaracteristicasHabilidade.MENOS_DEFESA), 1, 3, this); // começa em CD
		    
		    setResisDilaceracao(0.5);
		    setResisDOT(0.5);
		    

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
			this.aplicarEfeito(ListaEfeitos.aumentoDefesa("Aumento de Defesa", 5, 3), this);
			adversario.aplicarEfeito(ListaEfeitos.reducaoCura("Reução de Cura", 25, 2), this);
			
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
			return "Ao sofrer um ataque, o Dragão recebe um aumento de 5% na defesa por 3 turnos e o adversário tem sua cura reduzida em 25% por 2 turnos!";
		}




		@Override
		protected boolean Habilidade1(Habilidade habilidade1, Personagem alvo, Personagem atacante,
				List<Personagem> time1, List<Personagem> time2) {
			alvo.aplicarEfeito(ListaEfeitos.danoProlongado("Queimaduras", (int)(this.getAtaqueFinal() / 3), 4), atacante);
			alvo.aplicarEfeito(ListaEfeitos.desaceleracao("Desaceleração", 25, 2), atacante);
			return true;
		}




		@Override
		protected boolean Habilidade2(Habilidade habilidade2, Personagem alvo, Personagem atacante,
				List<Personagem> time1, List<Personagem> time2) {
			this.aplicarEfeito(ListaEfeitos.aumentoDefesa("Aumento de Defesa", 25, 3), this); // +25% defesa por 3 turnos
			this.aplicarEfeito(ListaEfeitos.aumentoAtaque("Aumento de Ataque", 25, 3), this); // +25% ataque por 3 turnos
			return true;
		}




		@Override
		protected boolean Habilidade3(Habilidade habilidade3, Personagem alvo, Personagem atacante,
				List<Personagem> time1, List<Personagem> time2) {
			int dano = (int) (this.getAtaqueFinal() * 2); // Dano base da investida
			int danoQueimadura = (int) (this.getAtaqueFinal());
			int val = time2.size();
			for(int i = 0; i < val; i++) {
				time2.get(i).receberDano(dano, this, time1, time2);
				time2.get(i).aplicarEfeito(ListaEfeitos.danoProlongado("Queimaduras", danoQueimadura, 3), this);

			}			return true;
		}




		@Override
		protected boolean Habilidade4(Habilidade habilidade4, Personagem alvo, Personagem atacante,
				List<Personagem> time1, List<Personagem> time2) {
			int dano = (int) (this.getAtaqueFinal() *1.5);
			alvo.receberDano(dano, atacante, time1, time2);
			alvo.aplicarEfeito(ListaEfeitos.reducaoDefesa("Redução de Defesa", 25, 3), atacante);
			return true;
		}


		@Override
		protected String descricaoHabilidade1() {
			// TODO Auto-generated method stub
			return "O adversário perde 25% da velocidade por 2 turnos e recebe Queimaduras(sofre " + (int)(this.getAtaqueFinal() / 3) + " de dano por 4 turnos)!";
		}


		@Override
		protected String descricaoHabilidade2() {
			// TODO Auto-generated method stub
			return "Aumenta a defesa e o dano do Dragão Ancião em 25% por 3 turnos.";
		}


		@Override
		protected String descricaoHabilidade3() {
			// TODO Auto-generated method stub
			return "Um ataque poderoso que causa " + (int)(this.getAtaqueFinal() * 2) + " de dano ao time inimigo e causa Queimaduras(sofre "+ (int) (this.getAtaqueFinal()) + " de dano por 3 turnos).";
		}


		@Override
		protected String descricaoHabilidade4() {
			// TODO Auto-generated method stub
			return "Causa " + (int)(this.getAtaqueFinal() * 1.5) + " de dano e reduz a defesa do adversário em 25% por 3 turnos.";
		}

		
}
