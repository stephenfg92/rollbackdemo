package com.stephenfg.sre.estados;

import com.stephenfg.sre.data.EstadoDoPersonagem;
import com.stephenfg.sre.eventos.EventoMudancaDeEstado;

public class TratativaEstadoNenhum implements TratativaDeEstado{

    @Override
    public EventoMudancaDeEstado tratar(ArgumentosDeTratativa args) {
        args.estado.tipoEstado = EstadoDoPersonagem.PARADO;
        return new EventoMudancaDeEstado(args.entidade, EstadoDoPersonagem.NENHUM, EstadoDoPersonagem.PARADO);
    }
}
