package com.stephenfg.sre.estados;

import com.badlogic.ashley.core.Entity;
import com.stephenfg.sre.componentes.ComponenteEstado;
import com.stephenfg.sre.eventos.EventoMudancaDeEstado;

public interface TratativaDeEstado {
    EventoMudancaDeEstado tratar(ArgumentosDeTratativa args);
}