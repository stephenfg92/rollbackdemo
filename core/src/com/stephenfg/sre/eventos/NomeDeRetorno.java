package com.stephenfg.sre.eventos;

import java.util.HashMap;
import java.util.Map;

public class NomeDeRetorno {
    private static Map<Class<? extends Evento>, String> nome = new HashMap<Class<? extends Evento>, String>() {{
        put(EventoMudancaDeEstado.class, "aoMudarEstado");
    }};

    public static String obterNome(Class<? extends Evento> eventType){
        return nome.get(eventType);
    }
}
