package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ComponenteCorpoRigido implements Component {
    public Vector2 velocidade;
    public Vector2 velocidadeAnterior;
    public Vector2 deltaMovimento;

    public ComponenteCorpoRigido(Vector2 velocidade){
        this.velocidade = velocidade;
        this.velocidadeAnterior = velocidade;
        this.deltaMovimento = new Vector2(0, 0);
    }

    public ComponenteCorpoRigido(){
        this.velocidade = new Vector2(0, 0);
        this.velocidadeAnterior = this.velocidade;
        this.deltaMovimento = new Vector2(0, 0);
    }
    public ComponenteCorpoRigido(ComponenteCorpoRigido outro){
        this.velocidade = new Vector2(outro.velocidade);
        this.velocidadeAnterior = new Vector2(outro.velocidadeAnterior);
        this.deltaMovimento = new Vector2(outro.deltaMovimento);
    }
}
