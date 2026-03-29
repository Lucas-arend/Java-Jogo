package com.game;

public enum Raridade {
    COMUM(1, 1),
    RARO(2, 2),
    ÉPICO(3, 3),
    LENDÁRIO(5, 4);

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
