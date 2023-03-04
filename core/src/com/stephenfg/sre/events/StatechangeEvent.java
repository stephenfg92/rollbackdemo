package com.stephenfg.sre.events;

import com.badlogic.ashley.core.Entity;
import com.stephenfg.sre.data.CharacterState;

public class StatechangeEvent implements IEvent {
    public Entity e;
    public CharacterState newState;

    public StatechangeEvent(Entity e, CharacterState state){
        this.e = e;
        this.newState = state;
    }
}
