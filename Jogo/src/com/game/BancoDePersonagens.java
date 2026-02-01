package com.game;
import java.util.*;
import com.game.Personagens.*;
public class BancoDePersonagens {
	List<Personagem> personagensDisponiveis;

	public BancoDePersonagens() {
		personagensDisponiveis = new ArrayList<>();
		inicializarPersonagens();
	}

	private void inicializarPersonagens() {
		personagensDisponiveis.add(new Raptor(1L));
		personagensDisponiveis.add(new Dragao(2L));
		personagensDisponiveis.add(new Fenix(3L));
		personagensDisponiveis.add(new Vampiro(4L));
		personagensDisponiveis.add(new Ciclope(5L));
		personagensDisponiveis.add(new Zumbi(6L));
		personagensDisponiveis.add(new Golem_De_Pedra(7L));
		// Adicione mais personagens conforme necessário
	}

	public List<Personagem> getPersonagensDisponiveis() {
		return personagensDisponiveis;
	}

	public Personagem getPersonagemPorId(long id) {
		for (Personagem p : personagensDisponiveis) {
			if (p.getId() == id) return p;
		}
		return null; // Retorna null se o personagem não for encontrado
	}
	
	public Personagem getPersonagemModeloPorId(long id) {
		for (Personagem p : personagensDisponiveis) {
			if (p.getId() == id) return p;
		}
		return null; // Retorna null se o personagem não for encontrado
	}
	public Personagem getPersonagemPorNome(String name) {
		for (Personagem p : personagensDisponiveis) {
			if(p.getNome() == name) return p;	
		}
		return null;
	}
	
	public List<Personagem> getTodosPersonagens() {
		return personagensDisponiveis;
	}

}
