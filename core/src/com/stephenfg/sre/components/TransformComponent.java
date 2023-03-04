package com.stephenfg.sre.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class TransformComponent implements Component {
    public Vector2 position;
    public Vector2 scale;

    public TransformComponent(Vector2 position){
        this.position = position;
        this.scale = new Vector2(1, 1);
    }
    public TransformComponent(Vector2 position, Vector2 scale){
        this.position = position;
        this.scale = scale;
    }
}
