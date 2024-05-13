package com.stephenfg.sre.eventos;

import com.stephenfg.sre.utilidades.Par;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarramentoDeEventos {
    private Map<Class<? extends Evento>, List<Par<Object, Method>>> tratativas = new HashMap<>();

    public void registrarAssinante(Object assinante) {
        Method[] metodos = assinante.getClass().getDeclaredMethods();
        for (Method m : metodos) {
            if (m.isAnnotationPresent(TratativaDeEvento.class)) {
                Class<? extends Evento> evento = m.getAnnotation(TratativaDeEvento.class).value();

                List<Par<Object, Method>> listaMetodos = tratativas.get(evento);
                if (listaMetodos == null) {
                    listaMetodos = new ArrayList<>();
                    tratativas.put(evento, listaMetodos);
                }

                listaMetodos.add(new Par<>(assinante, m));
            }
        }
    }

    public void emitirEvento(Evento evento) {
        List<Par<Object, Method>> listaTratativas = tratativas.get(evento.getClass());
        if (listaTratativas != null) {
            for (Par<Object, Method> tratativa : listaTratativas) {
                try {
                    Method method = tratativa.getValor();
                    method.setAccessible(true);
                    method.invoke(tratativa.getChave(), evento);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
