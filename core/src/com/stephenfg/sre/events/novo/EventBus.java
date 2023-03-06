package com.stephenfg.sre.events.novo;

import com.badlogic.ashley.core.EntitySystem;
import com.stephenfg.sre.events.statechange.StatechangeEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.nimbus.State;

public class EventBus {
    private Map<Class<? extends Event>, List<Callback>> eventToCallback = new HashMap<>();

    public EventBus(){
    }

    public void subscribeToEvent(Class<? extends Event> event, Callback callback){
        if (!eventToCallback.containsKey(event))
            eventToCallback.put(event, new ArrayList<Callback>());
        eventToCallback.get(event).add(callback);
    }

    public void emitEvent(Event event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Callback> subscribers = eventToCallback.get(event.getClass());
        for (Callback c : subscribers){
            EntitySystem callbackOwner = c.owner;
            Method callback = callbackOwner.getClass().getMethod(c.methodName, StatechangeEvent.class);
            callback.invoke(callbackOwner, event);
        }
    }
}
