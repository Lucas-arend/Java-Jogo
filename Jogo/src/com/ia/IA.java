package com.ia;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.game.CaracteristicasHabilidade;
import com.game.Habilidade;
import com.game.Personagem;

public class IA {

    private Random rng = new Random();

    /* ================= API ================= */

    public int escolherIndiceHabilidadeIA(Personagem ia, Personagem alvo) {

        if (ia.isStun())
            return -1;

        List<Habilidade> habilidades = ia.getHabilidadesLista().stream()
                .filter(Habilidade::estaDisponivel)
                .filter(h -> habilidadePermitida(h, ia))
                .toList();

        if (habilidades.isEmpty()) return -1;

        EstadoIA estado = definirEstado(ia, alvo);

        // ⭐ PRIORIDADE ABSOLUTA
        Habilidade prioridadeAlta = habilidades.stream()
                .filter(h -> h.getPrioridade() > 0)
                .max(Comparator.comparingInt(Habilidade::getPrioridade))
                .orElse(null);

        if (prioridadeAlta != null && prioridadeAlta.podeUsar())
            return ia.getIndiceHabilidade(prioridadeAlta) + 1;

        // FINALIZAÇÃO
        if (estado == EstadoIA.FINALIZADOR) {
            Habilidade finalizar = buscarFinalizacao(habilidades, ia, alvo);
            if (finalizar != null)
                return ia.getIndiceHabilidade(finalizar) + 1;
        }

        // SOBREVIVÊNCIA
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

    /* ================= LEITURA TÁTICA ================= */

    private boolean alvoJaControlado(Personagem alvo) {
        return alvo.isStun() || alvo.isSilenciado() || alvo.isCongelado();
    }

    private boolean inimigoBuffado(Personagem alvo) {
        return alvo.temBuffAtaque() ||
               alvo.temBuffDefesa() ||
               alvo.temBuffVelocidade();
    }

    private double calcularAmeaca(Personagem alvo) {
        double ameaca = alvo.getAtaqueFinal() * 0.6;

        if (alvo.getVelocidade() > 90)
            ameaca += 15;

        if (alvo.temBuffAtaque())
            ameaca += 20;

        if (alvo.temDOTAlto())
            ameaca += 10;

        return ameaca;
    }

    private boolean danoExcessivo(Habilidade h, Personagem alvo, Personagem ia) {
        if (!h.causaDano()) return false;

        double danoEstimado = ia.getAtaqueFinal();
        return danoEstimado > alvo.getVida() * 1.8;
    }

    /* ================= BUSCAS ================= */

    private Habilidade buscarFinalizacao(List<Habilidade> habilidades, Personagem ia, Personagem alvo) {
        return habilidades.stream()
                .filter(Habilidade::causaDano)
                .filter(h -> alvo.getVida() < ia.getAtaqueFinal())
                .max(Comparator.comparingInt(Habilidade::getPesoIA))
                .orElse(null);
    }

    private Habilidade buscarCura(List<Habilidade> habilidades, Personagem ia) {
        double vidaPct = ia.getVida() / (double) ia.getVidaMaxima();
        if (vidaPct > 0.60) return null;

        return habilidades.stream()
                .filter(Habilidade::causaCura)
                .max(Comparator.comparingInt(Habilidade::getPesoIA))
                .orElse(null);
    }

    /* ================= SCORE ================= */

    private double pontuarHabilidade(
            Habilidade h,
            Personagem ia,
            Personagem alvo,
            EstadoIA estado
    ) {

        double score = 0;

        boolean alvoMaisRapido = alvo.getVelocidade() > ia.getVelocidade();

        // combo: alvo controlado → dano pesado
        if (alvoJaControlado(alvo) &&
            h.getCaracteristicasHabilidade().contains(CaracteristicasHabilidade.DANO_ALTO))
            score += 12;

        for (CaracteristicasHabilidade c : h.getCaracteristicasHabilidade()) {

            switch (c) {

                case DOT -> score += 6;
                case DOT_ALTO -> score += 10;

                case ATORDOAR -> {
                    if (!alvoJaControlado(alvo)) {
                        score += alvoMaisRapido ? 20 : 10;
                        if (estado == EstadoIA.CONTROLE)
                            score += 12;
                    } else score -= 8;
                }

                /*case SILENCIO -> {
                    if (alvo.temBuffAtaque() || alvo.temCuraDisponivel())
                        score += 18;
                    else score += 6;
                }

                case REMOVER_BUFF -> {
                    if (inimigoBuffado(alvo))
                        score += 20;
                    else score += 5;
                }*/

                case DEFESA, PROTECAO ->
                        score += estado == EstadoIA.SOBREVIVENCIA ? 18 : 4;

                case CURA, CURA_MEDIANA, CURA_ALTA ->
                        score += estado == EstadoIA.SOBREVIVENCIA ? 22 : 5;

                case DANO_ALTO -> score += 15;
                case DANO_LETAL -> score += 25;
            }
        }

        if (danoExcessivo(h, alvo, ia))
            score -= 10;

        score += calcularAmeaca(alvo) * 0.15;

        score -= h.getCooldownAtual() * 0.8;

        // variação humana
        score *= (0.92 + rng.nextDouble() * 0.16);

        return score;
    }

    /* ================= ESTADO ================= */

    private EstadoIA definirEstado(Personagem ia, Personagem alvo) {

        double vidaIA = ia.getVida() / (double) ia.getVidaMaxima();
        double vidaAlvo = alvo.getVida() / (double) alvo.getVidaMaxima();

        if (vidaIA < 0.35 || calcularAmeaca(alvo) > 60)
            return EstadoIA.SOBREVIVENCIA;

        if (vidaAlvo < 0.25)
            return EstadoIA.FINALIZADOR;

        if (alvo.isStun() || alvo.isSilenciado())
            return EstadoIA.AGRESSIVO;

        if (ia.getVelocidade() < alvo.getVelocidade())
            return EstadoIA.CONTROLE;

        return EstadoIA.AGRESSIVO;
    }

    /* ================= FILTROS ================= */

    private boolean habilidadePermitida(Habilidade h, Personagem ia) {
        if (ia.isSilenciado() && !h.ignoraSilencio())
            return false;
        return true;
    }

    /* ================= ENUM ================= */

    private enum EstadoIA {
        AGRESSIVO,
        CONTROLE,
        FINALIZADOR,
        SOBREVIVENCIA
    }
}