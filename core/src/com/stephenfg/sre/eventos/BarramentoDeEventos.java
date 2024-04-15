package com.stephenfg.sre.eventos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarramentoDeEventos {
    private Map<Class<? extends Evento>, List<Retorno>> eventoParaRetorno = new HashMap<>();

    public BarramentoDeEventos(){
    }

    public void assinarEvento(Class<? extends Evento> evento, Retorno retorno){
        if (!eventoParaRetorno.containsKey(evento))
            eventoParaRetorno.put(evento, new ArrayList<Retorno>());
        eventoParaRetorno.get(evento).add(retorno);
    }

    public void emitirEvento(Evento evento) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Retorno> assinantes = eventoParaRetorno.get(evento.getClass());
        for (Retorno r : assinantes){
            Assinante donoDoRetorno = r.dono;
            Method retorno = donoDoRetorno.getClass().getMethod(r.nomeDoMetodo, evento.getClass());
            retorno.invoke(donoDoRetorno, evento);
        }
    }
}
