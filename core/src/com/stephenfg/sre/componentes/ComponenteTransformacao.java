package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ComponenteTransformacao implements Component {
    public Vector2 posicao;
    public Vector2 escala;

    public double rotacao = 0.f;

    public ComponenteTransformacao(Vector2 posicao){
        this.posicao = posicao;
        this.escala = new Vector2(1, 1);
    }
    public ComponenteTransformacao(Vector2 posicao, Vector2 escala){
        this.posicao = posicao;
        this.escala = escala;
    }
}
