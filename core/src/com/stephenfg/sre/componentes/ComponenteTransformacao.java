package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ComponenteTransformacao implements Component {
    public Vector2 centro;
    public Vector2 escala;

    public float rotacao = 0.f;

    public ComponenteTransformacao(Vector2 centro){
        this.centro = centro;
        this.escala = new Vector2(1, 1);
    }
    public ComponenteTransformacao(Vector2 centro, Vector2 escala){
        this.centro = centro;
        this.escala = escala;
    }
}
