package com.stephenfg.sre.estados;

import com.stephenfg.sre.componentes.ComponenteCorpoRigido;
import com.stephenfg.sre.data.EstadoDoPersonagem;
import com.stephenfg.sre.eventos.EventoMudancaDeEstado;

public class TratativaEstadoParado implements TratativaDeEstado{
    @Override
    public EventoMudancaDeEstado tratar(ArgumentosDeTratativa args) {
        ComponenteCorpoRigido corpoRigido = args.mapeadorCorpoRigido.get(args.entidade);
        if (corpoRigido.velocidadeAnterior.x == 0 && corpoRigido.velocidade.x != 0){
            args.estado.tipoEstado = EstadoDoPersonagem.CORRENDO;
            return new EventoMudancaDeEstado(args.entidade, EstadoDoPersonagem.PARADO, EstadoDoPersonagem.CORRENDO);
        }
        return null;
    }
}
