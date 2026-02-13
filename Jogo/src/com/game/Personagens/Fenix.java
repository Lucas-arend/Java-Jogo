package com.game.Personagens;

import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

import com.game.CaracteristicasHabilidade;
import com.game.Classe;
import com.game.Habilidade;
import com.game.ListaEfeitos;
import com.game.Personagem;
import com.game.Raridade;
import com.game.StatusBase;
import com.game.Tipo;

public class Fenix extends Personagem {
	public long id;
	public boolean Reviver = true;
	
	public Fenix(long id) {
		super(
	            id,
	            "Fênix Flamejante",
	            1,
	            Tipo.FOGO,
	            Classe.SUPORTE,
	            Raridade.LENDARIO,
	            "Cura a si mesma e causa dano em área",
	            new StatusBase(
	                1800, // vidaMaxima
	                1800,  // vida
	                700,  // ataque
	                10,   // defesa %
	                110,   // velocidade
	                0,   // proteção
	                25,   // chance de crítico
	                1.25   // dano crítico
	            )
	        );
		habilidades[0] = new com.game.Habilidade("Bicada quente", "Causa " + (int)(this.getAtaqueFinal()) + " de dano e reduz a defesa do adversário em 20% por 3 turnos.", Arrays.asList(CaracteristicasHabilidade.DANO_BAIXO, CaracteristicasHabilidade.MENOS_DEFESA), 0, 0); // começa disponível
        habilidades[1] = new com.game.Habilidade("Explosão de Fogo", "Causa" + (int) (this.getAtaqueFinal()) + " de dano ao inimigo e deixa o alvo em chamas ( causa " + (int) (this.getAtaqueFinal()/3) + " de dano por 4 turnos).", Arrays.asList(CaracteristicasHabilidade.DANO, CaracteristicasHabilidade.DOT), 0, 3); // começa em CD
	    habilidades[2] = new com.game.Habilidade("Alta vitalidade", "A Fênix cura 60% de sua vida e cura 30% da vida de seus aliados.", Arrays.asList(CaracteristicasHabilidade.CURA_ALTA), 2, 3); // começa em CD
	    habilidades[3] = new com.game.Habilidade("Renascimento", "A Fênix Flamejante emite uma chama curativa que regenera " + (int)(this.getAtaqueFinal() / 3) + " sua vida por 4 turnos e ela renascera na próxima vez que for nocauteada.", Arrays.asList(CaracteristicasHabilidade.REVIVER,CaracteristicasHabilidade.CURA_PROLONGADA), 3, 3); // começa em CD;
	    
	    //adicionarImagem();
	
	}
		
	@Override
    public Personagem copiar() {
        Fenix copia = new Fenix(id);

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
		
		
		// Implementação das habilidades da Fênix Flamejante
		if (valor == 1) {
			System.out.println(this.getNome() + " usa Chama Revitalizante!");
			// Cura imediata
			int dano = (int) (this.getAtaqueFinal());
			alvo.aplicarEfeito(ListaEfeitos.reducaoDefesa(20, 3).copiar());
			alvo.receberDano(dano, this, time1, time2);
			System.out.println(alvo.getNome() + " recebe " + dano + " de dano e perde 20% de sua defesa por 3 turnos!");
		} else if (valor == 2) {
			System.out.println(this.getNome() + " usa Explosão de Fogo em " + alvo.getNome() + "!");
			int dano = this.getAtaqueFinal(); // Dano base da explosão de fogo
			alvo.receberDano(dano, this, time1, time2);
			int danoQueimadura = (int) (this.getAtaqueFinal() / 4); // Dano de queimadura por turno
			alvo.aplicarEfeito(com.game.ListaEfeitos.danoProlongado("Queimaduras", danoQueimadura, 3)); // Aplica queimadura por 5 turnos
			System.out.println(alvo.getNome() + " recebe " + dano + " de dano e sofre " + danoQueimadura + " de dano por 3 turnos!");
		} else if (valor == 3) {
			System.out.println(this.getNome() + " usa Asas Flamejantes!");
			int curaInstantanea = (int) (this.getVidaMaxima() * 0.3); // Cura instantânea de 30% da vida máxima
			int time = time1.size();
			for(int i = 0; i < time; i++) {
				int cura = (int) (time1.get(i).getVidaMaxima() * 3);
				time1.get(i).curar(cura);
				System.out.println(time1.get(i).getNome() + " recupera " + cura + " de vida instantaneamente!");
			}
			this.curar(curaInstantanea);			

		} else if (valor == 4) {
			System.out.println(this.getNome() + " usa Renascimento!");
			//this.passivas.get(0).addReviver(1); // Adiciona a habilidade de renascer
			Reviver = true;
			int cura = (int) (getAtaqueFinal() / 3); // Cura base da habilidade
			this.aplicarEfeito(com.game.ListaEfeitos.curaProlongada(cura, 4)); 
			System.out.println(this.getNome() + " recebe " + cura + " de vida por 3 turnos e vai renascer na(s) "+ Reviver + " próxima vez que for derrotada!");

			
		} else {
			System.out.println("Habilidade inválida.");
		}
		return true; // ✅ gastou turno
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
			
	}

	@Override
	protected void Nocauteado(Personagem adversario, Personagem aliado, List<Personagem> time1, List<Personagem> time2) {
		if (!Reviver) return;
		int vidaReviver = Math.max(1, (int)(this.getVidaMaxima() * 0.25));
		this.setVidaMaxima((int)(this.getVidaMaxima() * 0.8));
	    this.setVida(vidaReviver);
	    this.setVivo(true);

	    Reviver = false;

	    System.out.println("🔥 " + this.getNome() + " renasce das cinzas! :)");

	    return;	
		
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
	    setCaminhoImagem("/resurces/Fenix_flamejante.png");
	}

	@Override
	protected String descricaoPassivas() {
		if (Reviver) return "A " + getNome() + " revive com 25% da vida váxima ao ser nocauteada, porém ela perde 20% de sua vida máxima!";
		return "Habilidade desativada";
	}



}
