package com.stephenfg.sre.events;

import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;

public class EventManager {
    public Map<Signal<?>, Listener<?>> signalToListener;
    private Map<Listener<?>, Signal<?>> listenerToSignal;

    public EventManager(){
        signalToListener = new HashMap<Signal<?>, Listener<?>>();
    }

    public void addSignalListenerPair(Signal<?> s, Listener<?> l){
        if (!signalToListener.containsKey(s)){
            signalToListener.put(s, l);
        }

        if (!listenerToSignal.containsKey(l)){
            listenerToSignal.put(l, s);
        }
    }

    public Listener<?> getListenerFor(Signal<?> s){
        try {
            return signalToListener.get(s);
        } catch (NullPointerException exception){
            exception.printStackTrace();
            Gdx.app.log("ERROR", "Signal not found.");
        }
        return null;
    }

    public Signal<?> getSignalFor(Listener<?> l){
        try{

        }catch (NullPointerException exception){
            exception.printStackTrace();
            Gdx.app.log("ERROR", "Listener not found.");
        }
    }


}
