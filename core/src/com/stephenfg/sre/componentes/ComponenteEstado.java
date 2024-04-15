package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;
import com.stephenfg.sre.data.EstadoDoPersonagem;

public class ComponenteEstado implements Component{
    public EstadoDoPersonagem tipoEstado;

    public ComponenteEstado(EstadoDoPersonagem tipoEstado){
        this.tipoEstado = tipoEstado;
    }
}
