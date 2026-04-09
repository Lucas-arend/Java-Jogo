package com.game.Personagens;

	import java.util.ArrayList;
import java.util.Arrays;
	import java.util.List;
import java.util.Random;

import com.game.CaracteristicasHabilidade;
	import com.game.Classe;
import com.game.Familia;
import com.game.Habilidade;
	import com.game.ListaEfeitos;
	import com.game.Personagem;
	import com.game.Raridade;
	import com.game.StatusBase;
	import com.game.Tipo;

public class Mago_Eletrico extends Personagem{


		public long id;

		public Mago_Eletrico(long id) {
			super (id,
					"Mago Elétrico",
					1,
					Tipo.LUZ,
					Classe.MAGO,
					Familia.MAGIA,
					Raridade.MÍTICO,
					"Um mago que estudou a eletricidade e agora aprendeu como manipilar ela.",
					new StatusBase(
							1600, // vidaMaxima
			                1600,  // vida
			                400,  // ataque
			                0,   // defesa %
			                100,   // velocidade
			                0,   // proteção
			                5,   // chance de crítico
			                1.25   // dano crítico)
					));
			habilidades[0] = new com.game.Habilidade("Choque das mãos", Arrays.asList(CaracteristicasHabilidade.DANO, CaracteristicasHabilidade.AUMENTAR_COOLDOWN), 0, 0,this); // começa disponível
	        habilidades[1] = new com.game.Habilidade("absorção elética", Arrays.asList(CaracteristicasHabilidade.PROTECAO, CaracteristicasHabilidade.REDUCAO_DE_ATAQUE), 0, 3, this); // começa em CD
		    habilidades[2] = new com.game.Habilidade("Descarga elétrica", Arrays.asList(CaracteristicasHabilidade.AUMENTAR_COOLDOWN, CaracteristicasHabilidade.DANO_EM_AREA), 1, 3, this); // começa em CD
		    habilidades[3] = new com.game.Habilidade("Relâmpago", Arrays.asList(CaracteristicasHabilidade.DANO_LETAL, CaracteristicasHabilidade.AUMENTAR_COOLDOWN, CaracteristicasHabilidade.MENOS_DEFESA), 3, 4, this); // começa em CD;
		    
		
		    setResisReducaoDano(0.5);
		    setResisDOT(0.75);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Personagem copiar() {
		     Mago_Eletrico copia = new Mago_Eletrico(id);

		        // copia habilidades (cooldown isolado)
		        for (int i = 0; i < this.habilidades.length; i++) {
		            if (this.habilidades[i] != null) {
		                copia.habilidades[i] = this.habilidades[i].copiar();
		            }
		        }

		        return copia;
		}

		@Override
		protected void adicionarImagem() {
			setCaminhoImagem("/resurces/Mago_Eletrico.png");
			
		}

		@Override
		protected void adicionarHabilidade(Habilidade copiar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected boolean Habilidade1(Habilidade habilidade1, Personagem alvo, Personagem atacante, List<Personagem> time1,
				List<Personagem> time2) {
			 int dano = (int) (this.getAtaqueFinal() * 0.6);
			 alvo.receberDano(dano, atacante, time1, time2);
			 if(alvo.nomeEfeito("Choque")) {
				 alvo.habilidades[1].aumentarCooldown();
			     alvo.habilidades[2].aumentarCooldown();
		   	     alvo.habilidades[3].aumentarCooldown();
			 }
			 
			 List<Personagem> vivos = new ArrayList<>();

			 for (Personagem p : time2) {
			     if (p.estaVivo()) {
			         vivos.add(p);
			     }
			 }

			 if (!vivos.isEmpty()) {
			     Random random = new Random();
			     Personagem alvoAleatorio = vivos.get(random.nextInt(vivos.size()));

			     alvoAleatorio.receberDano(dano, atacante, time1, time2);
			     if(alvoAleatorio.nomeEfeito("Choque")) {
			    	 alvoAleatorio.habilidades[1].aumentarCooldown();
			         alvoAleatorio.habilidades[2].aumentarCooldown();
			         alvoAleatorio.habilidades[3].aumentarCooldown();
			     }
			     
			 }

				
			return true;
		}

		@Override
		protected boolean Habilidade2(Habilidade habilidade2, Personagem alvo, Personagem atacante, List<Personagem> time1,
				List<Personagem> time2) {
			this.aplicarEfeito(ListaEfeitos.protecao(getHabilidadeNome1(), (int)(this.getAtaqueFinal() * 1.5), 3), this);
			for(int i = 0; time2.size() > i; i ++){
				time2.get(i).aplicarEfeito(ListaEfeitos.efeitoNulo("Choque", 3, ""), this);

			}
			this.habilidades[1].aumentarCooldown();
			this.habilidades[2].aumentarCooldown();
			this.habilidades[3].aumentarCooldown();
			return true;
		}

		@Override
		protected boolean Habilidade3(Habilidade habilidade3, Personagem alvo, Personagem atacante, List<Personagem> time1,
				List<Personagem> time2) {
			for(int i = 0; time2.size() > i; i++) {
				time2.get(i).receberDano((int)(getAtaqueFinal() * 0.5), atacante, time1, time2);
				if(time2.get(i).nomeEfeito("Choque")) {
					for(int j = 0; time2.size() > j; j++) {
						time2.get(j).receberDano((int)(getAtaqueFinal() * 0.2), atacante, time1, time2);
					}
				}
					
			}
			

			return true;
		}

		@Override
		protected boolean Habilidade4(Habilidade habilidade4, Personagem alvo, Personagem atacante, List<Personagem> time1,
				List<Personagem> time2) {
			if(alvo.nomeEfeito("Choque")) {
				alvo.aplicarEfeito(ListaEfeitos.reducaoDefesa("", 50, 3), this);
				alvo.aplicarEfeito(ListaEfeitos.reducaoCura("Redução de cura", 50,3), this);
			}
			alvo.receberDano((int)(getAtaqueFinal() * 2.5), atacante, time1, time2);
			alvo.aplicarEfeito(ListaEfeitos.stun("Atordoamento", 1), this);
			
			
			return true;
		}

		@Override
		protected void aoAtacar(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2,
				int dano) {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void aoSerAtacado(Personagem adversario, Personagem aliado, List<Personagem> time1,
				List<Personagem> time2, int dano) {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void AoNocautear(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2,
				int dano) {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void Nocauteado(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2,
				int dano) {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void inicioDoTurno(Personagem adversario, Personagem aliado, List<Personagem> time1,
				List<Personagem> time2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void fimDoTurno(Personagem adversario, Personagem aliado, List<Personagem> time1,
				List<Personagem> time2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected String descricaoHabilidade1() {
			// TODO Auto-generated method stub
			return "Causa " + (int)(this.getAtaqueFinal() * 0.6) + " de dano no alvo e " + (int)(this.getAtaqueFinal() * 0.6) + " de dano em um adversário aleatório, aumenta o cooldown de suas abilidades em 1 se o alvo tiver com o efeito (Choque).";
		}

		@Override
		protected String descricaoHabilidade2() {
			// TODO Auto-generated method stub
			return "Ganha " + (int)(this.getAtaqueFinal() * 1.5) + " de proteção por 3 turnos e deixa todos os alvos com o efeito (Choque), mas aumenta o tempo de recarga das próprias habilidades em 1.";
		}

		@Override
		protected String descricaoHabilidade3() {
			// TODO Auto-generated method stub
			return "Causa " + (int)(this.getAtaqueFinal() * 0.5) + " de dano em todos os adversários e causa " + (int)(this.getAtaqueFinal() * 0.2) + " de dano extra para cada adversário com o efeito (Choque), elém de aumentar o tempo de recarga das habilidades em 1.";
		}

		@Override
		protected String descricaoHabilidade4() {
			// TODO Auto-generated method stub
			return "Causa " + (int)(this.getAtaqueFinal() * 2.5) + " de dano e atordoa o alvo por 1 turno, se o alvo tiver o efeito (Choque) ele perde 50% da proteção e da cura por 3 turnos";
		}

		@Override
		protected String descricaoPassivas() {
			// TODO Auto-generated method stub
			return "Sem passivas disponíveis!";
		}

	}