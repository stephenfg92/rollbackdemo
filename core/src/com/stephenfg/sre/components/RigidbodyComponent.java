package com.stephenfg.sre.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class RigidbodyComponent implements Component {
    public Vector2 velocity;

    public RigidbodyComponent(Vector2 velocity){
        this.velocity = velocity;
    }

    public RigidbodyComponent(){
        this.velocity = new Vector2(0, 0);
    }
}
