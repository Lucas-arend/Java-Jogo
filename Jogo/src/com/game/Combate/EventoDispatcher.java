package com.game.Combate;

import java.util.ArrayList;
import java.util.List;

public class EventoDispatcher {

    private static List<Evento> eventos = new ArrayList<>();

    public static void disparar(Evento e) {
        eventos.add(e);
    }

    public static List<Evento> consumir() {
        List<Evento> copia = new ArrayList<>(eventos);
        eventos.clear();
        return copia;
    }
}