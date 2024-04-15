package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ComponenteCorpoRigido implements Component {
    public Vector2 velocidade;
    public Vector2 velocidadeAnterior;

    public ComponenteCorpoRigido(Vector2 velocidade){
        this.velocidade = velocidade;
        this.velocidadeAnterior = velocidade;
    }

    public ComponenteCorpoRigido(){
        this.velocidade = new Vector2(0, 0);
        this.velocidadeAnterior = this.velocidade;
    }
}
