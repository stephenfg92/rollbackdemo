package com.stephenfg.sre.events;

import com.badlogic.ashley.core.Entity;
import com.stephenfg.sre.data.CharacterState;

public class StatechangeEvent implements IEvent {
    public Entity sender;
    public CharacterState previewState;
    public CharacterState newState;

    public StatechangeEvent(Entity sender, CharacterState prevState, CharacterState newState){
        this.sender = sender;
        this.previewState = prevState;
        this.newState = newState;
    }
}
