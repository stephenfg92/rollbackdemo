package com.stephenfg.sre.data;

public enum Marcador {
    JOGADOR(1),
    CENARIO(2);

    private final int mascara;

    Marcador(int mascara) {
        this.mascara = mascara;
    }

    public int obterMascara() {
        return mascara;
    }

    public static int adicionarMarcador(int flags, Marcador marcador) {
        return flags | marcador.obterMascara();
    }

    public static int removerMarcador(int flags, Marcador marcador) {
        return flags & ~marcador.obterMascara();
    }

    public static boolean possuiMarcador(int flags, Marcador marcador) {
        return (flags & marcador.obterMascara()) != 0;
    }
    public static int criarMascara(Marcador... marcadores) {
        int mascara = 0;
        for (Marcador marcador : marcadores) {
            mascara |= marcador.obterMascara();
        }
        return mascara;
    }
}

