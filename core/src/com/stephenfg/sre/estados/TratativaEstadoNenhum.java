package com.stephenfg.sre.estados;

import com.stephenfg.sre.data.Estado;
import com.stephenfg.sre.eventos.EventoMudancaDeEstado;

public class TratativaEstadoNenhum implements TratativaDeEstado{

    @Override
    public EventoMudancaDeEstado tratar(ArgumentosDeTratativa args) {
        args.estado.tipoEstado = Estado.PARADO;
        return new EventoMudancaDeEstado(args.entidade, Estado.NENHUM, Estado.PARADO);
    }
}
