package com.stephenfg.sre.eventos;

import com.badlogic.ashley.core.Entity;
import com.stephenfg.sre.data.Estado;

public class EventoMudancaDeEstado implements Evento {
    public Entity destinatario;
    public Estado estadoAnterior;
    public Estado novoEstado;

    public EventoMudancaDeEstado(Entity destinatario, Estado estadoAnterior, Estado novoEstado){
        this.destinatario = destinatario;
        this.estadoAnterior = estadoAnterior;
        this.novoEstado = novoEstado;
    }

}
