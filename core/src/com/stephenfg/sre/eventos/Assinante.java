package com.stephenfg.sre.eventos;

public interface Assinante {
    Assinante assinarEvento(Class<? extends Evento> event);
}
