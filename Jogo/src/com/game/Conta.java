package com.game;

import java.awt.Component;
import java.util.*;

public class Conta {

    private String usuario;
    private String senha;
    private int vitorias;
    private int derrotas;
    private int empates;
    private int nivel = 1;
    private int experiencia;
    private int metaExp = 100;
    private int moedas;
    private int gemas;
    private long idConta;

    private static final int LIMITE_DECK = 4;

    List<Personagem> deck = new ArrayList<>();

    BancoDePersonagens bancoDePersonagens = new BancoDePersonagens();

    // Construtor
    public Conta(String usuario, String senha) {
        this.usuario = usuario;
        this.setSenha(senha);
        
        this.desbloquearPersonagem(1);
        this.desbloquearPersonagem(7);
        this.desbloquearPersonagem(4);
        this.desbloquearPersonagem(2);
        this.desbloquearPersonagem(5);
        
        this.moedas = 1000;
        this.gemas = 50;
    }

    // =========================
    // DECK
    // =========================

    public boolean adicionarAoDeck(long idPersonagem) {

        if (deck.size() >= LIMITE_DECK) return false;

        Personagem modelo = bancoDePersonagens.getPersonagemModeloPorId(idPersonagem);
        if (modelo == null) return false;

        deck.add(modelo); // adiciona referência ao modelo
        return true;
    }

    public void limparDeck() {
        deck.clear();
    }

    public List<Personagem> getDeck()  {
    	return deck;
    }

    

    public boolean temPersonagensVivos(List<Personagem> personagensBatalha) {
        return personagensBatalha.stream().anyMatch(Personagem::isVivo);
    }

    public Personagem getProximoVivo(List<Personagem> personagensBatalha) {
        return personagensBatalha.stream()
                .filter(Personagem::isVivo)
                .findFirst()
                .orElse(null);
    }

    // =========================
    // BATALHA
    // =========================

    public List<Personagem> criarDeckParaBatalha() {
        List<Personagem> copia = new ArrayList<>();
        for (Personagem p : deck) {
            copia.add(p.copiar());
        }
        return copia;
    }

    // =========================
    // PROGRESSÃO
    // =========================

    public String ganharExperiencia(int xp) {
        this.experiencia += xp;

        if (this.experiencia >= metaExp) {
            this.nivel++;
            this.experiencia -= metaExp;
            this.metaExp = (int) (getMetaExp() * 1.75);
            this.moedas += 100 * nivel;
            this.gemas += 10 * nivel;
            System.out.println("Parabéns! Você subiu para o nível " + this.nivel);
            return "Parabéns! Você subiu para o nível " + this.nivel + "\nVocê recebeu:\n+" + 100*nivel + " moedas.\n+" + 10+nivel + " gemas.";
        }
        return "";
    }

    // =========================
    // BANCO
    // =========================

    public List<Personagem> getPersonagensDisponiveis() {
        return bancoDePersonagens.getPersonagensDisponiveis();
    }

    public boolean removerDoDeck(long id) {
        return deck.removeIf(p -> p.getId() == id);
    }

    
    @SuppressWarnings("null")
	public String desbloquearPersonagem(long id) {
        Personagem personagem = bancoDePersonagens.getPersonagemPorId(id);
        if (personagem.getDesbloqueado() == false) {
            personagem.desbloquear();
            System.out.println("Novo personagem " + personagem.getRaridade() + " desbloqueado: " + personagem.getNome());
            return "Novo personagem " + personagem.getRaridade() + " desbloqueado: " + personagem.getNome();
        }
        personagem.ganharCopias(1);
        System.out.println("Personagem " + personagem.getRaridade() +" repitido: " + personagem.getNome());
        return "Personagem " + personagem.getRaridade() +" repitido: " + personagem.getNome();
    }
    
    public String desbloquearPersonagemAleatorio(
            int comum,
            int raro,
            int epico,
            int lendario
    ) {

        int total = comum + raro + epico + lendario;
        if (total <= 0) {
            return "Probabilidades inválidas";
        }

        Random random = new Random();
        int sorteio = random.nextInt(total); // 0 até total-1

        Raridade raridadeSorteada;

        if (sorteio < comum) {
            raridadeSorteada = Raridade.COMUM;
        } else if (sorteio < comum + raro) {
            raridadeSorteada = Raridade.RARO;
        } else if (sorteio < comum + raro + epico) {
            raridadeSorteada = Raridade.EPICO;
        } else {
            raridadeSorteada = Raridade.LENDARIO;
        }

        // Filtra personagens dessa raridade
        List<Personagem> candidatos = bancoDePersonagens.getPersonagensDisponiveis()
                .stream()
                .filter(p -> p.getRaridade() == raridadeSorteada)
                .toList();

        if (candidatos.isEmpty()) {
            return "Nenhum personagem disponível da raridade " + raridadeSorteada;
        }

        // Sorteia um personagem da raridade
        Personagem sorteado = candidatos.get(random.nextInt(candidatos.size()));

        return desbloquearPersonagem(sorteado.getId());
    }


    // Getters básicos omitidos (iguais aos teus)
    public String getUsuario() {
		return usuario;
	}
    public int getVitorias() {
    	return vitorias;
    }
    public int getDerrotas() {
		return derrotas;
		}
	public int getEmpates() {
		return empates;
	}
    
	public int getNivel() {
		return nivel;
	}
    
    public int getExperiencia() {
    	return experiencia;
    }
    
    public int getMoedas() {
    	return moedas;
    }
    public int getGemas() {
    	return gemas;
    }
    public long getIdConta() {
    	return idConta;
    }
    public int getMetaExp() {
    	return metaExp;
    }
    public List<Personagem> getTodosPersonagens() {
		return bancoDePersonagens.getTodosPersonagens();
	}
    public BancoDePersonagens getBancoDePersonagens() {
    	return bancoDePersonagens;
    }
	public Personagem getPersonagemPorId(long id) {
		for (Personagem p : bancoDePersonagens.personagensDisponiveis) {
			if (p.getId() == id) return p;
		}
		return null; // Retorna null se o personagem não for encontrado
	}
    public void setNomeUsuario(String usuario) {
    			this.usuario = usuario;
    }

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void ganharMoedas(int val) {
		this.moedas += val;
	}
	public boolean gastarMoedas(int val) {
		if (this.getMoedas() < val) return false;
		
		this.moedas -= val;
		return true;
	}
    
	public void ganharGemas(int val) {
		this.gemas += val;
	}
	
	public boolean gastarGemas(int val) {
		if (this.getGemas() < val) return false;
		
		this.moedas -= val;
		return true;
	}
	
}
