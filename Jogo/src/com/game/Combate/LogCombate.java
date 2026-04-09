package com.game.Combate;

import java.util.ArrayList;
import java.util.List;

public class LogCombate {

    private static List<String> logs = new ArrayList<>();

    public static void adicionar(String msg) {
        logs.add(msg);
        System.out.println(msg);
    }

    public static List<String> obterLogs() {
        return new ArrayList<>(logs);
    }

    public static void limpar() {
        logs.clear();
    }
}