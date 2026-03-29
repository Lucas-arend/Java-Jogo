package com.game;

import java.util.*;
import com.ia.IA;

public class Batalha implements CombatListener{

    private IA ia = new IA();

    public List<Personagem> time1;
    public List<Personagem> time2;

    private Personagem ativo1;
    private Personagem ativo2;

    private Conta conta;

    public Conta getConta() {
    	return conta;
    }
    private List<String> log = new ArrayList<>();

    public Batalha(List<Personagem> time1, List<Personagem> time2, Conta conta) {
        this.conta = conta;
        this.time1 = time1;
        this.time2 = time2;

        this.ativo1 = getProximoVivo(time1);
        this.ativo2 = getProximoVivo(time2);
        
        for(Personagem p : time1) p.setCombatListener(this);
        for(Personagem p : time2) p.setCombatListener(this);

        aplicarStatusIniciais(time1);
        aplicarStatusIniciais(time2);
    }

    // =====================
    // LOG & EVENTOS
    // =====================

    public void registrar(String msg) {
        log.add(msg);
        System.out.println(msg);
    }

    public List<String> getLog() {
        return log;
    }
    
    public void limparLog() {
    	this.log.clear();;
    }


    // =====================
    // INICIALIZAÇÃO
    // =====================

    private void aplicarStatusIniciais(List<Personagem> time) {
        for (Personagem p : time) {
            p.StatusNivel();
            //p.StatusGrau();
        }
    }

    // =====================
    // CONTROLE DE TIMES
    // =====================

    public boolean timeTemVivos(List<Personagem> time) {
        return time.stream().anyMatch(Personagem::isVivo);
    }

    private Personagem getProximoVivo(List<Personagem> time) {
        return time.stream()
                .filter(Personagem::isVivo)
                .findFirst()
                .orElse(null);
    }

    private void verificarTrocaAutomatica() {
        if (ativo1 != null && !ativo1.isVivo()) {
            onDeath(ativo1);
            ativo1 = getProximoVivo(time1);
        }

        if (ativo2 != null && !ativo2.isVivo()) {
            onDeath(ativo2);
            ativo2 = getProximoVivo(time2);
        }
    }

    // =====================
    // CICLO DE TURNO
    // =====================

    public void batalha(int habilidadeIndex) {
        if (batalhaEncerrada()) return;

        verificarTrocaAutomatica();
        if (ativo1 == null || ativo2 == null) return;

        processarInicioTurno();

        verificarTrocaAutomatica();
        if (ativo1 == null || ativo2 == null) return;

        if (habilidadeIndex == 6) {
            desistir();
            return;
        }

        executarTurnosComPrioridade(habilidadeIndex);

        executarFimTurno();
        verificarTrocaAutomatica();
    }

    // =====================
    // ORDEM COM PRIORIDADE
    // =====================

    private void executarTurnosComPrioridade(int habilidadeIndex) {

        int prioridadeJogador = ativo1.getHabilidade(habilidadeIndex - 1).getPrioridade();
        int habilidadeIA = ia.escolherIndiceHabilidadeIA(ativo2, ativo1);
        if (habilidadeIA == -1) habilidadeIA = 1;

        int prioridadeIA = ativo2.getHabilidade(habilidadeIA - 1).getPrioridade();
        if (prioridadeJogador > prioridadeIA ||
           (prioridadeJogador == prioridadeIA &&
            ativo1.getVelocidadeFinal() >= ativo2.getVelocidadeFinal())) {

        	
            turnoJogador(habilidadeIndex);
            if (ativo2.isVivo()) turnoIA(habilidadeIA);

        } else {

            turnoIA(habilidadeIA);
            if (ativo1.isVivo()) turnoJogador(habilidadeIndex);
        }
    }

    // =====================
    // TURNOS
    // =====================

    public void turnoJogador(int habilidadeIndex) {
        
        if (!ativo1.isVivo()) return;

        if (ativo1.temEfeito(Efeito.TagEfeito.STUN)) {
            onStun(ativo1);
            return;
        }

        if (ativo1.temEfeito(Efeito.TagEfeito.SILENCIO) && habilidadeIndex > 1) {
            registrar("Silenciado! Apenas ataque básico disponível.");
            habilidadeIndex = 1;
        }

        Personagem alvo = ativo2;

        if (ativo1.temEfeito(Efeito.TagEfeito.PROVOCADO)) {
            alvo = ativo1.getultimoAtacante();
        }

        boolean usou = ativo1.usarHabilidades(alvo, habilidadeIndex, ativo1, time1, time2);

        if (usou) ativo1.reduzirCooldowns();
    }

    private void turnoIA(int habilidadeIndex) {

        if (!ativo2.isVivo()) return;

        if (ativo2.temEfeito(Efeito.TagEfeito.STUN)) {
            onStun(ativo2);
            return;
        }

        if (ativo2.temEfeito(Efeito.TagEfeito.SILENCIO) && habilidadeIndex > 1)
            habilidadeIndex = 1;

        Personagem alvo = ativo1;

        boolean usou = ativo2.usarHabilidades(alvo, habilidadeIndex, ativo2, time2, time1);

        if (usou) ativo2.reduzirCooldowns();
    }

    // =====================
    // PROCESSAMENTO DE TURNO
    // =====================

    private void processarInicioTurno() {
        for (Personagem p : time1) {
            p.processarEfeitosInicioTurno(ativo2, time1, time2);
            p.inicioDoTurno(ativo2, ativo1, time1, time2);
        }

        for (Personagem p : time2) {
            p.processarEfeitosInicioTurno(ativo1, time2, time1);
            p.inicioDoTurno(ativo1, ativo2, time2, time1);
        }
    }

    private void executarFimTurno() {
        for (Personagem p : time1)
            p.fimDoTurno(ativo2, ativo1, time1, time2);

        for (Personagem p : time2)
            p.fimDoTurno(ativo1, ativo2, time2, time1);
    }

    // =====================
    // TROCA
    // =====================

    public void trocarPersonagem(Personagem novo) {

        if (novo == null || !novo.isVivo() || novo == ativo1) {
            registrar("Troca inválida.");
            return;
        }

        ativo1 = novo;
        ativo1.resetarCooldowns();
        registrar("Jogador trocou para " + ativo1.getNome());
    }
    
    public void desistir() {
    	registrar("Jogador desistiu!");
    	time1.forEach(p -> p.setVivo(false));
    }

    // =====================
    // RESULTADO
    // =====================

    public boolean batalhaEncerrada() {
        return !timeTemVivos(time1) || !timeTemVivos(time2);
    }

    public String getResultado() {

        if (timeTemVivos(time1)) {

            for (Personagem p : conta.deck) {
                p.ganharExperiencia(150);
                p.resetarStatus();
            }

            conta.ganharMoedas(conta.getNivel() * 50);
            conta.ganharExperiencia(conta.getNivel() * 100);

            return "Você venceu!";
        }

        if (timeTemVivos(time2)) {
            for (Personagem p : conta.deck)
                p.resetarStatus();

            return "Você perdeu!";
        }

        return "Empate!";
    }

    public Personagem getAtivo1() { return ativo1; }
    public Personagem getAtivo2() { return ativo2; }
    
    
    
    @Override
    public void onHit(Personagem alvo, int dano) {
        registrar(alvo.getNome() + " sofreu " + dano + " dano!");
    }

    @Override
    public void onCrit(Personagem atacante) {
        registrar("💥 CRÍTICO de " + atacante.getNome() + "!");
    }

    @Override
    public void onDeath(Personagem morto) {
        registrar("☠ " + morto.getNome() + " foi derrotado!");
    }

    @Override
    public void onStun(Personagem personagem) {
        registrar(personagem.getNome() + " ficou atordoado!");
    }

    @Override
    public void onEffectApplied(Personagem alvo, String efeito) {
        registrar(alvo.getNome() + " recebeu efeito: " + efeito);
    }

    @Override
    public void onHeal(Personagem alvo, int valor) {
        registrar(alvo.getNome() + " recuperou " + valor + " HP");
    }

	@Override
	public void reviver(Personagem alvo, int valor) {
		registrar(alvo.getNome() + " reviveu com " + valor + " de HP");
	}
}