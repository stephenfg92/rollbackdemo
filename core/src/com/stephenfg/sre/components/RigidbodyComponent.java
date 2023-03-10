package com.stephenfg.sre.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class RigidbodyComponent implements Component {
    public Vector2 velocity;
    public Vector2 previousVelocity;

    public RigidbodyComponent(Vector2 velocity){
        this.velocity = velocity;
        this.previousVelocity = velocity;
    }

    public RigidbodyComponent(){
        this.velocity = new Vector2(0, 0);
        this.previousVelocity = this.velocity;
    }
}
