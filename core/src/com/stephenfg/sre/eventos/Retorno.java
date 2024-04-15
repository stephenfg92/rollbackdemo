package com.stephenfg.sre.eventos;

public class Retorno {
    public Assinante dono;
    public String nomeDoMetodo;

    public Retorno(Assinante dono, String nomeDoMetodo){
        this.dono = dono;
        this.nomeDoMetodo = nomeDoMetodo;
    }
}
