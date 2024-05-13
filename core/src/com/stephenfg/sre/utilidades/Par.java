package com.stephenfg.sre.utilidades;

public class Par<Chave, Valor> {
    private Chave chave;
    private Valor valor;

    public Par(Chave chave, Valor valor) {
        this.chave = chave;
        this.valor = valor;
    }

    public Chave getChave() { return chave; }
    public Valor getValor() { return valor; }
}
