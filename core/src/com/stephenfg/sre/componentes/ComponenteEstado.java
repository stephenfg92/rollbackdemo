package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;
import com.stephenfg.sre.data.Estado;

public class ComponenteEstado implements Component{
    public Estado tipoEstado;

    public ComponenteEstado(Estado tipoEstado){
        this.tipoEstado = tipoEstado;
    }
}
