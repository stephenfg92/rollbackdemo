package com.stephenfg.sre.colisao;

import com.badlogic.gdx.math.Vector2;

public class Colisao {
    public Vector2 diferencaPosicao;
    public Vector2 penetracao;
    public Vector2 normalColisao;

    public Colisao(Vector2 diferencaPosicao, Vector2 penetracao, Vector2 normalColisao) {
        this.diferencaPosicao = diferencaPosicao;
        this.penetracao = penetracao;
        this.normalColisao = normalColisao;
    }
}
