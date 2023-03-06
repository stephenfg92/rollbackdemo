package com.stephenfg.sre.events;

import com.badlogic.ashley.core.Entity;
import com.stephenfg.sre.data.CharacterState;

public class StatechangeEvent implements Event {
    public Entity receiver;
    public CharacterState previewState;
    public CharacterState newState;

    public StatechangeEvent(Entity receiver, CharacterState prevState, CharacterState newState){
        this.receiver = receiver;
        this.previewState = prevState;
        this.newState = newState;
    }

}
