package com.game;

import java.util.List;
import com.ia.IA;

public class Batalha {

	IA ia = new IA();
    public List<Personagem> time1;
    public List<Personagem> time2;

    private Personagem ativo1;
    private Personagem ativo2;

    private boolean houveNocaute;

    private Conta conta;


    public Batalha(List<Personagem> time1, List<Personagem> time2, Conta conta) {
        this.conta = conta;
    	
    	this.time1 = time1;
        this.time2 = time2;

        this.ativo1 = getProximoVivo(time1);
        this.ativo2 = getProximoVivo(time2);    
        int size1 = time1.size();
        for(int i = 0; i < size1; i++) {
        	time1.get(i).StatusNivel();
        	time1.get(i).StatusGrau();
        }
        
        int size2 = time2.size();
        for(int i = 0; i < size2; i++) {
        	time2.get(i).StatusNivel();
        	time2.get(i).StatusGrau();
        }
    }

    // =====================
    // CONTROLE DE TIMES
    // =====================

    public boolean timeTemVivos(List<Personagem> time) {
        return time.stream().anyMatch(Personagem::isVivo);
    }

    public Personagem getProximoVivo(List<Personagem> time) {
        return time.stream()
                .filter(Personagem::isVivo)
                .findFirst()
                .orElse(null);
    }

    private void verificarTrocaAutomatica() {
        houveNocaute = false;
        if (ativo1 != null && !ativo1.isVivo()) {
            ativo1 = getProximoVivo(time1);
            houveNocaute = true;
        }
        if (ativo2 != null && !ativo2.isVivo()) {
            ativo2 = getProximoVivo(time2);
            houveNocaute = true;
        }
    }


    // =====================
    // TURNO (GUI)
    // =====================

    public void turnoJogador(int habilidadeIndex) {

        if (ativo1 == null || ativo2 == null) return;

        if(ativo1.estaVivo() == false) this.ativo1 = getProximoVivo(time1);
        if(ativo2.estaVivo() == false) this.ativo2 = getProximoVivo(time2);

        int tim1 = time1.size();
        int tim2 = time2.size();
        for (int i = 0; i < tim1; i++) {
        	if (time1.get(i) != null) time1.get(i).processarEfeitosPorTurno();
        }
        
        for(int i = 0; i < tim2; i++) {
        	if (time2.get(i) != null) time2.get(i).processarEfeitosPorTurno();
        }
        
        for (int i = 0; i < tim1; i++) {
        	time1.get(i).inicioDoTurno(ativo2, ativo1, time1, time2);
        }
        for (int i = 0; i < tim2; i++) {
        	time2.get(i).inicioDoTurno(ativo1, ativo2, time2, time1);
        }
        
        verificarTrocaAutomatica();
        // Jogador ataca

        

        System.out.println("\n\n\n##Jogador##");
        boolean usou = ativo1.usarHabilidades(ativo2, habilidadeIndex, ativo1, time1, time2);
        if (!usou) return;

        ativo1.reduzirCooldowns();
        //verificarTrocaAutomatica();


        if (!timeTemVivos(time2)) return;

        // IA ataca
     // IA ataca
        if (ativo1 == null || ativo2 == null) return;
        if (!ativo1.isVivo() || !ativo2.isVivo()) return;

        turnoIA();
  

        for(int i = 0; i < tim1; i++) {
        	time1.get(i).fimDoTurno(ativo2, ativo1, time1, time2);
        }
        for(int i = 0; i < tim2; i++) {
        	time2.get(i).fimDoTurno(ativo1, ativo2, time2, time1);
        }
        verificarTrocaAutomatica();
    }

    private void turnoIA() {

        int indice = ia.escolherIndiceHabilidadeIA(ativo2, ativo1);

        System.out.print("\n\n\n##IA## ");

        if (indice == -1) {
            ativo2.usarHabilidades(ativo1, 1, ativo2, time2, time1);
            ativo2.reduzirCooldowns();
            return;
        }

        ativo2.usarHabilidades(ativo1, indice, ativo2, time2, time1);
        ativo2.reduzirCooldowns();
    }

    
    // =====================
    // GETTERS
    // =====================

    public Personagem getAtivo1() {
        return ativo1;
    }

    public Personagem getAtivo2() {
        return ativo2;
    }

    public boolean batalhaEncerrada() {
        return !timeTemVivos(time1) || !timeTemVivos(time2);
    }

    public String getResultado() {
        if (timeTemVivos(time1)) {

            for (Personagem p : conta.deck) {
                p.ganharExperiencia(500);
                p.resetarStatus();
                System.out.println(p.getNome() + " ganhou 500 de experiencia e alcançou o nível " + p.getNivel());
            }

            return "Você venceu!";
        }

        if (timeTemVivos(time2)) {
            for (Personagem p : conta.deck) {
                p.resetarStatus();
                }
        	return "Você perdeu!";
        }
        for (Personagem p : conta.deck) {
            p.resetarStatus();
        }
        return "Empate!";
    }


	public Conta getConta() {
		// TODO Auto-generated method stub
		return conta;
	}
}
