package com.stephenfg.sre.eventos;

import com.badlogic.ashley.core.Entity;
import com.stephenfg.sre.colisao.Colisao;
import com.stephenfg.sre.data.EstadoDoPersonagem;

public class EventoColisao implements Evento {
    public Entity a;
    public Entity b;
    public Colisao colisao;

    public EventoColisao(Entity a, Entity b, Colisao colisao) {
        this.a = a;
        this.b = b;
        this.colisao = colisao;
    }
}
