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

        this.ativo1 = getProximoVivo(time1, ativo1);
        this.ativo2 = getProximoVivo(time2, ativo2);    
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

    public Personagem getProximoVivo(List<Personagem> time, Personagem ativo) {
        return time.stream()
                .filter(Personagem::isVivo)
                .findFirst()
                .orElse(null);
    }

    private void verificarTrocaAutomatica() {
        houveNocaute = false;
        if (ativo1 != null && !ativo1.isVivo()) {
            ativo1 = getProximoVivo(time1, ativo1);
            houveNocaute = true;
        }
        if (ativo2 != null && !ativo2.isVivo()) {
            ativo2 = getProximoVivo(time2, ativo2);
            houveNocaute = true;
        }
    }

    private void trocarPersonagemJogador() {
        Personagem novo = getProximoVivoDiferente(time1, ativo1);

        if (novo == ativo1) {
            System.out.println("Não há outro personagem vivo para trocar.");
            return;
        }

        ativo1 = novo;
        ativo1.resetarCooldowns(); // como você comentou antes
        System.out.println("Jogador trocou para: " + ativo1.getNome());
    }
    public void trocarPersonagem() {
        trocarPersonagemJogador();

        // troca consome o turno
        for (Personagem p : time1) {
            p.fimDoTurno(ativo2, ativo1, time1, time2);
        }
        for (Personagem p : time2) {
            p.fimDoTurno(ativo1, ativo2, time2, time1);
        }
    }


    private Personagem getProximoVivoDiferente(List<Personagem> time, Personagem atual) {
        for (Personagem p : time) {
            if (p.isVivo() && p != atual) {
                return p;
            }
        }
        return atual; // se não tiver outro, mantém
    }





    
    
    public void batalha(int indice) { 
    	if (batalhaEncerrada()) {
    
            System.out.println("🏁 Batalha encerrada.");
            return;
        }

        if (ativo1 == null || ativo2 == null) {
            System.out.println("⚠️ Personagem ativo inexistente.");
            return;
        }

        verificarTrocaAutomatica();

        if (ativo1 == null || ativo2 == null) {
            System.out.println("⚠️ Não há personagens suficientes para continuar.");
            return;
        }

        // === PROCESSA INÍCIO DO TURNO ===
        for (Personagem p : time1) {
            p.processarEfeitosPorTurno();
            p.inicioDoTurno(ativo2, ativo1, time1, time2);
        }
        for (Personagem p : time2) {
            p.processarEfeitosPorTurno();
            p.inicioDoTurno(ativo1, ativo2, time2, time1);
        }

        verificarTrocaAutomatica();

        if (ativo1 == null || ativo2 == null) {
            System.out.println("⚠️ Não há personagens suficientes para continuar.");
            return;
        }

        // === TROCA DE PERSONAGEM ===
        if (indice == 5) {
            trocarPersonagem();
            return; // IA NÃO ATACA
        }
        if (ativo2 == null) {
            System.out.println("⚠️ Nenhum inimigo ativo disponível.");
            return; // ou encerra a rodada
        }

        if (!ativo2.isVivo()) {
            // lógica normal
        	return;

        }


        // === ORDEM DE VELOCIDADE ===
        if (ativo1.getVelocidadeFinal() >= ativo2.getVelocidadeFinal()) {
            turnoJogador(indice);
            if (ativo2.isVivo()) turnoIA();
        } else {
            turnoIA();
            if (ativo1.isVivo()) turnoJogador(indice);
        }

        // === FIM DO TURNO ===
        for (Personagem p : time1) {
            p.fimDoTurno(ativo2, ativo1, time1, time2);
        }
        for (Personagem p : time2) {
            p.fimDoTurno(ativo1, ativo2, time2, time1);
        }

        verificarTrocaAutomatica();

        if (ativo1 == null || ativo2 == null) {
            System.out.println("⚠️ Não há personagens suficientes para continuar.");
            return;
        }
    }

    // =====================
    // TURNO (GUI)
    // =====================

    public void turnoJogador(int habilidadeIndex) {
        if (!ativo1.isVivo() || !ativo2.isVivo()) return;

        boolean usou = ativo1.usarHabilidades(
            ativo2, habilidadeIndex, ativo1, time1, time2
        );

        if (!usou) return;

        ativo1.reduzirCooldowns();
    }

        



    private void turnoIA() {
        if (ativo1 == null || ativo2 == null) return;
        if (!ativo1.isVivo() || !ativo2.isVivo()) return;

        int indice = ia.escolherIndiceHabilidadeIA(ativo2, ativo1);

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
                conta.ganharMoedas((int)(conta.getNivel() * 50));
                conta.ganharExperiencia((int)(conta.getNivel() * 100));
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
