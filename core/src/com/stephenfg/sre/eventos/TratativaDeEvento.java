package com.stephenfg.sre.eventos;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TratativaDeEvento {
    Class<? extends Evento> value();
}
