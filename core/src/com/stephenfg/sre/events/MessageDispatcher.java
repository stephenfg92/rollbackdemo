package com.stephenfg.sre.events;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;

public class MessageDispatcher {
    public Array<StatechangeEvent> stateChangeQ = new Array<StatechangeEvent>(true, 32);

    MessageDispatcher(){};

    void DispatchStateChangeMessage(StatechangeEvent evt){
        stateChangeQ.add(evt);
    }
}
