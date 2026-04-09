package com.game;

public enum Raridade {
    COMUM(1, 1),
    RARO(3, 2),
    ÉPICO(5, 3),
    MÍTICO(7,4),
    LENDÁRIO(9, 5);

    private final int grauInicial;
    private final int preco;

    Raridade(int GrauInicial, int preco) {
        this.grauInicial = GrauInicial;
        this.preco = preco;
    }

    public int getGrauInicial() {
        return grauInicial;
    }

    public int getPreco() {
        return preco;
    }
}
