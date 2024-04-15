package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;

public class ComponenteOrientacao implements Component {
    public boolean aEsquerda;

    public ComponenteOrientacao(){
        aEsquerda = false;
    }

    public ComponenteOrientacao(Boolean aEsquerda){
        this.aEsquerda = aEsquerda;
    }
}
