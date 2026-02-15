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
					    600, // vidaMaxima
		                600,  // vida
		                200,  // ataque
		                0,   // defesa %
		                60,   // velocidade
		                0,   // proteção
		                10,   // chance de crítico
		                1.25   // dano crítico
		            )
		        );
			habilidades[0] = new Habilidade("Ataque de infecção", descricaoHabilidade1(), Arrays.asList(CaracteristicasHabilidade.DANO_BAIXO, CaracteristicasHabilidade.DOT_ALTO), 0, 0);
		    habilidades[1] = new Habilidade("Ataque em bando", descricaoHabilidade2(), Arrays.asList(CaracteristicasHabilidade.DANO_ALTO), 1, 2); // começa em CD
		    habilidades[2] = new Habilidade("Horda infinita", descricaoHabilidade3(), Arrays.asList(CaracteristicasHabilidade.REVIVER), 2, 4);
		    habilidades[3] = new Habilidade("Devorar em grupo", descricaoHabilidade4(), Arrays.asList(CaracteristicasHabilidade.DANO_LETAL, CaracteristicasHabilidade.CURA_ALTA), 3, 3); // começa em CD
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
	protected void aoAtacar(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2, int dano) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void aoSerAtacado(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2, int dano) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void AoNocautear(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2, int dano) {

	}
	@Override
	protected void Nocauteado(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2, int dano) {
		if (bando <= 0) return;
	    this.setVida((int)(this.getVidaMaxima()));
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
	    	int dot = (int)(adversario.valorEfeito("Infecção") * 1.5);
	    	adversario.removerEfeitosPorNome("Infecção");
	    	adversario.aplicarEfeito(ListaEfeitos.danoProlongado("Infecção", dot, 2).copiar());
		}
		}
		}
				
	}
	@Override
	protected void fimDoTurno(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {

		
	}
	@Override
	protected void adicionarImagem() {
	    setCaminhoImagem("/resurces/Zumbi.png");
		
	}
	@Override
	protected String descricaoPassivas() {
		// TODO Auto-generated method stub
		return "O " + getNome() + " começa com uma horda de " + (bando + 1) + " zumbis, ao ser nocauteado outro zumbi do bando entra em seu lugar! \n"
				+ "O efeito infeccção dobra o seu dano no início de cada turno, e recebe + 1 turno de duração, (esse efeito para de afetar a infecção no momento que o último zumbi da horda for derrotado!";
	}
	@Override
	protected boolean Habilidade1(Habilidade habilidade1, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
    	int dano = (int)(this.getAtaqueFinal()*0.75);
    	int dot = (int)(this.getAtaqueFinal() * 0.25);
    	alvo.receberDano(dano, this, time1, time2);
    	if (alvo.nomeEfeito("infecção")) {
    		return true;
    	}
    	else alvo.aplicarEfeito(ListaEfeitos.danoProlongado("Infecção",dot, 2).copiar());		return true;
	}
	@Override
	protected boolean Habilidade2(Habilidade habilidade2, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
		int dano = (int)(this.getAtaqueFinal());
    	for(int i = 0; i < bando + 1; i++) {
    		if(!alvo.isVivo()) return true;
    		alvo.receberDano(dano, this, time1, time2);
    	}
		return true;
	}
	@Override
	protected boolean Habilidade3(Habilidade habilidade3, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
    	bando ++;
    	this.aplicarEfeito(ListaEfeitos.curaProlongada((int)(this.getAtaqueFinal() / 2), 3));
    	alvo.aplicarEfeito(ListaEfeitos.danoProlongado("Decomposição", (int)(this.getAtaqueFinal() / 2), 3));
    	System.out.println("Um bando de " + bando + " zumbis!");		return true;
	}
	@Override
	protected boolean Habilidade4(Habilidade habilidade4, Personagem alvo, Personagem atacante, List<Personagem> time1,
			List<Personagem> time2) {
    	int dilaceracao = 0;
    	for(int i = 0; i < bando + 1; i++) {
    		if(!alvo.isVivo()) return true;
    		dilaceracao += ((int)(alvo.getVidaMaxima() / 5));	    	
    		}
    	alvo.danoDireto((int)(dilaceracao), this, time1, time2);
    	if (!alvo.isVivo()) {
    		this.bando ++;
    		System.out.println(bando);
    	}		return true;
	}

	@Override
	protected String descricaoHabilidade1() {
		// TODO Auto-generated method stub
		return "Um ataque que causa " + (int) (this.getAtaqueFinal() + 0.75) + " de dano e deixa o adversário co infecção ( causa " + (int) (this.getAtaqueFinal() * 0.25) + " de dano por 2 turnos.";
	}

	@Override
	protected String descricaoHabilidade2() {
		// TODO Auto-generated method stub
		return "Causa " + this.getAtaqueFinal() + " de dano para cada zumbi da horda " + (bando + 1) + " ( causa " + (int) (this.getAtaqueFinal() * (bando + 1)) + " de dano).";
	}

	@Override
	protected String descricaoHabilidade3() {
		// TODO Auto-generated method stub
		return "Causa " + (int) (this.getAtaqueFinal() * 0.5) + " de dano por 2 turnos, recupera " + (int) (this.getAtaqueFinal() * 0.5) + " de vida por 2 turnos e aumenta a quantidade de zumbis da horda em 1.";
	}

	@Override
	protected String descricaoHabilidade4() {
		// TODO Auto-generated method stub
		return "Causa dano de 20% da vida máxima do alvo por cada zumbi da horda de " + (bando + 1) + " zumbis ( causa " + (20 * (bando + 1)) + "% da vida máxima do adversário de dano).";
	}



	

}
