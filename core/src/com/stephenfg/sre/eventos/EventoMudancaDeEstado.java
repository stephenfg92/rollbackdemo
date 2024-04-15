package com.stephenfg.sre.eventos;

import com.badlogic.ashley.core.Entity;
import com.stephenfg.sre.data.EstadoDoPersonagem;

public class EventoMudancaDeEstado implements Evento {
    public Entity destinatario;
    public EstadoDoPersonagem estadoAnterior;
    public EstadoDoPersonagem novoEstado;

    public EventoMudancaDeEstado(Entity destinatario, EstadoDoPersonagem estadoAnterior, EstadoDoPersonagem novoEstado){
        this.destinatario = destinatario;
        this.estadoAnterior = estadoAnterior;
        this.novoEstado = novoEstado;
    }

}
