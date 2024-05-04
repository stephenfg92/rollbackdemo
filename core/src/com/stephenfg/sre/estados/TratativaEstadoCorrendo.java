package com.stephenfg.sre.estados;

import com.stephenfg.sre.componentes.ComponenteCorpoRigido;
import com.stephenfg.sre.data.Estado;
import com.stephenfg.sre.eventos.EventoMudancaDeEstado;

public class TratativaEstadoCorrendo implements TratativaDeEstado {
    @Override
    public EventoMudancaDeEstado tratar(ArgumentosDeTratativa args) {
        ComponenteCorpoRigido corpoRigido = args.mapeadorCorpoRigido.get(args.entidade);
        if (corpoRigido.velocidade.x == 0.0 && corpoRigido.velocidadeAnterior.x == 0.0){
            args.estado.tipoEstado = Estado.PARADO;
            return new EventoMudancaDeEstado(args.entidade, Estado.CORRENDO, Estado.PARADO);
        }
        return null;
    }
}
