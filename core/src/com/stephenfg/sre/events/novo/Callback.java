package com.stephenfg.sre.events.novo;

import com.badlogic.ashley.core.EntitySystem;

public class Callback {
    public EntitySystem owner;
    public String methodName;

    public Callback(EntitySystem owner, String methodName){
        this.owner = owner;
        this.methodName = methodName;
    }
}
