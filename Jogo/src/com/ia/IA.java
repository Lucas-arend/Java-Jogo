package com.ia;

import java.util.Comparator;
import java.util.List;

import com.game.CaracteristicasHabilidade;
import com.game.Habilidade;
import com.game.Personagem;

public class IA {

    /* ================= API ================= */

    public int escolherIndiceHabilidadeIA(Personagem ia, Personagem alvo) {

        List<Habilidade> habilidades = ia.getHabilidadesLista().stream()
                .filter(Habilidade::estaDisponivel)
                .filter(h -> habilidadeEhUtil(h, ia, alvo))
                .toList();

        if (habilidades.isEmpty()) return -1;

        EstadoIA estado = definirEstado(ia, alvo);

        // ⚠️ FINALIZAÇÃO ABSOLUTA
        if (estado == EstadoIA.FINALIZADOR) {
            Habilidade finalizar = buscarFinalizacao(habilidades, ia, alvo);
            if (finalizar != null)
                return ia.getIndiceHabilidade(finalizar) + 1;
        }

        // ⚠️ SOBREVIVÊNCIA ABSOLUTA
        if (estado == EstadoIA.SOBREVIVENCIA) {
            Habilidade cura = buscarCura(habilidades, ia);
            if (cura != null)
                return ia.getIndiceHabilidade(cura) + 1;
        }

        Habilidade melhor = habilidades.stream()
                .max(Comparator.comparingDouble(h ->
                        pontuarHabilidade(h, ia, alvo, estado)))
                .orElse(null);

        return melhor == null ? -1 : ia.getIndiceHabilidade(melhor) + 1;
    }

    /* ================= ESTADO ================= */

    private EstadoIA definirEstado(Personagem ia, Personagem alvo) {

        double vidaIA = ia.getVida() / (double) ia.getVidaMaxima();
        double vidaAlvo = alvo.getVida() / (double) alvo.getVidaMaxima();

        if (vidaIA < 0.30 || ia.estaSofrendoDOT())
            return EstadoIA.SOBREVIVENCIA;

        if (alvo.previsaoMortePorDOT() || vidaAlvo < 0.30)
            return EstadoIA.FINALIZADOR;

        if (ia.getAtaque() > alvo.getDefesa() &&
            ia.getVelocidade() >= alvo.getVelocidade())
            return EstadoIA.AGRESSIVO;

        return EstadoIA.CONTROLE;
    }

    /* ================= LEITURA DO ALVO ================= */

    private boolean alvoTemCuraDisponivel(Personagem alvo) {
        return alvo.getHabilidadesLista().stream()
                .filter(Habilidade::estaDisponivel)
                .anyMatch(h -> h.getCaracteristicasHabilidade().stream().anyMatch(c ->
                        c == CaracteristicasHabilidade.CURA ||
                        c == CaracteristicasHabilidade.CURA_MEDIANA ||
                        c == CaracteristicasHabilidade.CURA_ALTA));
    }

    private boolean alvoTemDefesaDisponivel(Personagem alvo) {
        return alvo.getHabilidadesLista().stream()
                .filter(Habilidade::estaDisponivel)
                .anyMatch(h -> h.getCaracteristicasHabilidade().stream().anyMatch(c ->
                        c == CaracteristicasHabilidade.DEFESA ||
                        c == CaracteristicasHabilidade.PROTECAO));
    }

    private boolean alvoTemBuffAtaque(Personagem alvo) {
        return alvo.getHabilidadesLista().stream()
                .filter(Habilidade::estaDisponivel)
                .anyMatch(h -> h.getCaracteristicasHabilidade()
                        .contains(CaracteristicasHabilidade.AUMENTO_DE_ATAQUE));
    }

    private boolean alvoDefesaAlta(Personagem alvo, Personagem ia) {
        return alvo.getDefesa() > ia.getAtaque() * 0.7;
    }

    /* ================= BUSCAS ================= */

    private Habilidade buscarFinalizacao(
            List<Habilidade> habilidades,
            Personagem ia,
            Personagem alvo
    ) {
        for (Habilidade h : habilidades) {

            if (h.getCaracteristicasHabilidade()
                    .contains(CaracteristicasHabilidade.DANO_LETAL))
                return h;

            if (h.getCaracteristicasHabilidade()
                    .contains(CaracteristicasHabilidade.DANO_ALTO)
                    && alvo.getVida() <= ia.getAtaque())
                return h;
        }
        return null;
    }

    private Habilidade buscarCura(List<Habilidade> habilidades, Personagem ia) {
        double vidaPct = ia.getVida() / (double) ia.getVidaMaxima();
        if (vidaPct > 0.60) return null;

        return habilidades.stream()
                .filter(h -> h.getCaracteristicasHabilidade().stream().anyMatch(c ->
                        c == CaracteristicasHabilidade.CURA ||
                        c == CaracteristicasHabilidade.CURA_MEDIANA ||
                        c == CaracteristicasHabilidade.CURA_ALTA))
                .findFirst()
                .orElse(null);
    }

    /* ================= SCORE ================= */

    @SuppressWarnings("incomplete-switch")
	private double pontuarHabilidade(
            Habilidade h,
            Personagem ia,
            Personagem alvo,
            EstadoIA estado
    ) {

        double score = 0;

        boolean alvoVaiMorrerPorDOT = alvo.previsaoMortePorDOT();
        boolean alvoPodeCura = alvoTemCuraDisponivel(alvo);
        @SuppressWarnings("unused")
		boolean alvoPodeDefender = alvoTemDefesaDisponivel(alvo);
        boolean alvoBuffaAtaque = alvoTemBuffAtaque(alvo);
        boolean defesaAlta = alvoDefesaAlta(alvo, ia);

        for (CaracteristicasHabilidade c : h.getCaracteristicasHabilidade()) {

            switch (c) {

                /* ===== DANO ===== */
                case DANO_BAIXO -> score += defesaAlta ? -5 : 2;
                case DANO -> score += defesaAlta ? -3 : (estado == EstadoIA.AGRESSIVO ? 8 : 3);
                case DANO_MEDIANO -> score += defesaAlta ? -1 : (estado == EstadoIA.AGRESSIVO ? 10 : 4);
                case DANO_ALTO -> {
                    score += estado == EstadoIA.AGRESSIVO ? 14 : 5;
                    if (alvoPodeCura && alvo.getVida() < alvo.getVidaMaxima() * 0.5)
                        score += 12; // matar antes da cura
                }
                case DANO_LETAL -> score += 100;

                /* ===== CURA ===== */
                case BAIXA_CURA -> score += curaPeso(ia, 3, estado);
                case CURA -> score += curaPeso(ia, 6, estado);
                case CURA_MEDIANA -> score += curaPeso(ia, 9, estado);
                case CURA_ALTA -> score += curaPeso(ia, 14, estado);

                /* ===== DEFESA ===== */
                case DEFESA, PROTECAO -> {
                    if (alvoBuffaAtaque)
                        score += 16;
                    else if (estado == EstadoIA.SOBREVIVENCIA)
                        score += defesaPeso(ia, 18);
                    else
                        score -= 5;
                }

                /* ===== BUFF ===== */
                case AUMENTO_DE_ATAQUE -> {
                    if (estado == EstadoIA.SOBREVIVENCIA || alvoVaiMorrerPorDOT)
                        score -= 60;
                    else
                        score += 6;
                }

                case AUMENTO_DE_VELOCIDADE ->
                        score += estado == EstadoIA.CONTROLE ? 8 : 3;

                /* ===== DEBUFF ===== */
                case REDUCAO_DE_ATAQUE -> {
                    if (alvo.getAtaque() > ia.getVida() * 0.25)
                        score += 20;
                    else
                        score += estado == EstadoIA.CONTROLE ? 12 : 4;
                }

                case REDUCAO_DE_VELOCIDADE ->
                        score += alvo.getVelocidade() > ia.getVelocidade() ? 10 : 3;

                case MENOS_DEFESA, MENOS_PROTECAO ->
                        score += defesaAlta ? 18 : (estado == EstadoIA.CONTROLE ? 10 : 3);

                /* ===== REVIVER ===== */
                case REVIVER ->
                        score += ia.getVida() <= 0 ? 100 : -300;
                        
                case REDUZIR_COOLDOWN -> 
                     score += estado == EstadoIA.CONTROLE ? 15 : 5;
                     
                case AUMENTAR_COOLDOWN ->
                     score += estado == EstadoIA.CONTROLE ? 16 : 6;
            }
        }

        score -= estado == EstadoIA.SOBREVIVENCIA
                ? h.getCooldownAtual() * 1.2
                : h.getCooldownAtual() * 0.8;

        if (ia.getVelocidade() > alvo.getVelocidade())
            score += 2;

        return score;
    }

    /* ================= FILTROS ================= */

    private boolean habilidadeEhUtil(Habilidade h, Personagem ia, Personagem alvo) {

        if (alvo.previsaoMortePorDOT() &&
            (h.getCaracteristicasHabilidade().contains(CaracteristicasHabilidade.DEFESA)
            || h.getCaracteristicasHabilidade().contains(CaracteristicasHabilidade.PROTECAO)))
            return false;

        if (alvoDefesaAlta(alvo, ia) &&
            h.getCaracteristicasHabilidade().contains(CaracteristicasHabilidade.DANO_BAIXO))
            return false;

        return true;
    }

    /* ================= PESOS ================= */

    private double curaPeso(Personagem ia, double base, EstadoIA estado) {
        double vidaPct = ia.getVida() / (double) ia.getVidaMaxima();
        double peso = base * (1.0 - vidaPct) * 2;
        return estado == EstadoIA.SOBREVIVENCIA ? peso * 2.5 : peso;
    }

    private double defesaPeso(Personagem ia, double base) {
        double vidaPct = ia.getVida() / (double) ia.getVidaMaxima();
        return base * (1.0 - vidaPct);
    }

    /* ================= ENUM ================= */

    private enum EstadoIA {
        AGRESSIVO,
        CONTROLE,
        FINALIZADOR,
        SOBREVIVENCIA
    }
}
