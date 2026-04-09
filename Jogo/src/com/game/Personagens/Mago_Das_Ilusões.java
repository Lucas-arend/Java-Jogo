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

public class Mago_Das_Ilusões extends Personagem{
	public long id;

	public Mago_Das_Ilusões(long id) {
		super (id,
				"Mago da Ilusões",
				1,
				Tipo.LUZ,
				Classe.MAGO,
				Familia.MAGIA,
				Raridade.ÉPICO,
				"Um mago que usa ilusões para distrair seus inimigos",
				new StatusBase(
						1800, // vidaMaxima
		                1800,  // vida
		                500,  // ataque
		                0,   // defesa %
		                100,   // velocidade
		                0,   // proteção
		                10,   // chance de crítico
		                1.25   // dano crítico)
				));
		habilidades[0] = new com.game.Habilidade("Golpe confuso", Arrays.asList(CaracteristicasHabilidade.DANO_BAIXO, CaracteristicasHabilidade.REDUCAO_DE_CURA, CaracteristicasHabilidade.REDUCAO_DO_DANO_CRITICO), 0, 0,this); // começa disponível
        habilidades[1] = new com.game.Habilidade("Ilusão", Arrays.asList(CaracteristicasHabilidade.DANO, CaracteristicasHabilidade.REDUCAO_DO_DANO_CRITICO, CaracteristicasHabilidade.AUMENTO_DA_CHANCE_DE_CRITICO, CaracteristicasHabilidade.REDUCAO_DE_ATAQUE), 0, 3, this); // começa em CD
	    habilidades[2] = new com.game.Habilidade("Nova realidade", Arrays.asList(CaracteristicasHabilidade.AUMENTAR_COOLDOWN, CaracteristicasHabilidade.REDUCAO_DE_VIDA_MAXIMA, CaracteristicasHabilidade.REDUCAO_DE_ATAQUE), 1, 5, this); // começa em CD
	    habilidades[3] = new com.game.Habilidade("Choque de realidade", Arrays.asList(CaracteristicasHabilidade.AUMENTO_DO_DANO_CRITICO, CaracteristicasHabilidade.AUMENTO_DA_CHANCE_DE_CRITICO, CaracteristicasHabilidade.DEFESA, CaracteristicasHabilidade.DANO_ALTO), 2, 4, this); // começa em CD;
	    
	
		// TODO Auto-generated constructor stub
	}

	@Override
	public Personagem copiar() {
	     Mago_Das_Ilusões copia = new Mago_Das_Ilusões(id);

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
		setCaminhoImagem("/resurces/Mago_Das_Ilusões.png");
		
	}

	@Override
	protected void adicionarHabilidade(Habilidade copiar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean Habilidade1(Habilidade habilidade1, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
		 int dano = (int) (this.getAtaqueFinal() * 0.5);
		 alvo.receberDano(dano, atacante, time1, time2);
		 alvo.aplicarEfeito(ListaEfeitos.reducaoCura("Redução de Cura", 25, 2), this);
		 alvo.aplicarEfeito(ListaEfeitos.reducaoAtaque("Redução de Dano", 25, 2), this);
		return true;
	}

	@Override
	protected boolean Habilidade2(Habilidade habilidade2, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
		alvo.receberDano(this.getAtaqueFinal(), atacante, time1, time2);
		alvo.aplicarEfeito(ListaEfeitos.reducaoDanoCritico("Redução de Dano", 50, 3), this);
		alvo.aplicarEfeito(ListaEfeitos.aumentoChanceCritico("Aumento da Chance de Crítico", 50, 3), this);
		alvo.aplicarEfeito(ListaEfeitos.reducaoAtaque("Redução de Dano",50, 2), this);
		return true;
	}

	@Override
	protected boolean Habilidade3(Habilidade habilidade3, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
		alvo.getHabilidade(1).aumentarCooldown();
		alvo.getHabilidade(2).aumentarCooldown();
		alvo.getHabilidade(3).aumentarCooldown();
		alvo.getHabilidade(1).aumentarCooldown();
		alvo.getHabilidade(2).aumentarCooldown();
		alvo.getHabilidade(3).aumentarCooldown();
		
		alvo.setVidaMaxima((int)(alvo.getVidaMaxima() * 0.85));
		alvo.setVida((int)(alvo.getVida() * 0.85));
		alvo.setAtaque((int)(alvo.getAtaque() * 0.85));
		return true;
	}

	@Override
	protected boolean Habilidade4(Habilidade habilidade4, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
		this.aplicarEfeito(ListaEfeitos.aumentoChanceCritico("Aumento da Chance de Crítico", 100, 1), this);
		this.aplicarEfeito(ListaEfeitos.aumentoDefesa("Aumento de Defesa", (int)(100), 1), this);
		alvo.receberDano(getAtaqueFinal() * 2, atacante, time1, time2);
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
		return "Causa " + (int)(this.getAtaqueFinal() * 0.5) + " de dano, reduz a cura do adversário em 25% por 2 turnos e reduz o dano do adversário em 25% por 2 turnos.";
	}

	@Override
	protected String descricaoHabilidade2() {
		// TODO Auto-generated method stub
		return "Causa " + this.getAtaqueFinal() + " de dano, reduz o ataque do adversário em 50% por 2 turnos, reduz o dano crítico do adversário em 50% por 3 turnos e aumenta a chance de dano crítico do adversário em 50% por 3 turnos.";
	}

	@Override
	protected String descricaoHabilidade3() {
		// TODO Auto-generated method stub
		return "Aumenta o tempo de recarga de todas as habilidades do adversário (exceto a principal) em 2 turnos e o ele tem sua vida máxima e ataque reduzidos em 15%.";
	}

	@Override
	protected String descricaoHabilidade4() {
		// TODO Auto-generated method stub
		return "O " + this.getNome() + " recebe um aumento de 100% na chance de crítico por 1 turno, aumenta a defesa em 100% por 1 turno e causa " + (int)(this.getAtaqueFinal() * 2) + " de dano!";
	}

	@Override
	protected String descricaoPassivas() {
		// TODO Auto-generated method stub
		return "Sem passivas disponíveis!";
	}

}
