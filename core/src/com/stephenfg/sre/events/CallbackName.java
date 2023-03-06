package com.stephenfg.sre.events;

import java.util.HashMap;
import java.util.Map;

public class CallbackName {
    private static Map<Class<? extends Event>, String> name = new HashMap<Class<? extends Event>, String>() {{
        put(StatechangeEvent.class, "onStateChange");
    }};

    public static String getName(Class<? extends Event> eventType){
        return name.get(eventType);
    }
}
