package com.game.Log;

import java.util.*;

public class BattleLog {

    public enum Tipo {
        DANO,
        CURA,
        CRITICO,
        MORTE,
        EFEITO,
        TROCA,
        HABILIDADE,
        TEXTO
    }

    public static class Evento {
        public Tipo tipo;
        public String mensagem;

        public Evento(Tipo tipo, String mensagem) {
            this.tipo = tipo;
            this.mensagem = mensagem;
        }
    }

    private List<Evento> eventos = new ArrayList<>();

    public void registrar(Tipo tipo, String msg) {
        eventos.add(new Evento(tipo, msg));
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void limpar() {
        eventos.clear();
    }
}