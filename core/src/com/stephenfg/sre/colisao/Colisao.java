package com.stephenfg.sre.colisao;

import com.badlogic.gdx.math.Vector2;

public class Colisao {
    public Vector2 pontoDeContato;
    public Vector2 penetracao;
    public Vector2 normalColisao;
    public float tempo;

    public Colisao(Vector2 pontoDeContato, Vector2 penetracao, Vector2 normalColisao, float tempo) {
        this.pontoDeContato = pontoDeContato;
        this.penetracao = penetracao;
        this.normalColisao = normalColisao;
        this.tempo = tempo;
    }

    public Colisao() {};
}
