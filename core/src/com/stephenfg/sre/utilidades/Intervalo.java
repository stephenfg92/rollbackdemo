package com.stephenfg.sre.utilidades;

public class Intervalo {
    public int inicio;
    public int fim;
    public int tamanho;

    public Intervalo(int inicio, int fim){
        this.inicio = inicio;
        this.fim = fim;
        this.tamanho = (fim - inicio) + 1;
    }
}
