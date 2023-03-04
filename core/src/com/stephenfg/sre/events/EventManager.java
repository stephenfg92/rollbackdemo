package com.stephenfg.sre.events;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

import javax.swing.plaf.nimbus.State;

public class EventManager {
    Map<Entity, Array<StatechangeEvent>> stateChangeQsPerReceiver;
    public Array<StatechangeEvent> stateChangeQ;


    public EventManager(){
        stateChangeQsPerReceiver = new HashMap<>();
    };

    public void DispatchStateChangeEvent(Entity receiver, StatechangeEvent evt){
        if (!stateChangeQsPerReceiver.containsKey(receiver)){
            stateChangeQsPerReceiver.put(receiver, new Array<StatechangeEvent>(true, 32));
        }

        stateChangeQsPerReceiver.get(receiver).add(evt);
    }

    public void clearQs(){
        for (Entity e : stateChangeQsPerReceiver.keySet()){
            stateChangeQsPerReceiver.get(e).clear();
        }
    }

    public Array<StatechangeEvent> getEventsFor(Entity receiver){
        if (stateChangeQsPerReceiver.containsKey(receiver)){
            return stateChangeQsPerReceiver.get(receiver);
        }
        return new Array<StatechangeEvent>();
    }


}
