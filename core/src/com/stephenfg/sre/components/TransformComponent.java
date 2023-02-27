package com.stephenfg.sre.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class TransformComponent implements Component {
    public Vector2 position;
    public Vector2 scale;
    public float rotation;
    public boolean rotateClockwise;

    public TransformComponent(Vector2 position, Vector2 scale){
        this.position = position;
        this.scale = scale;
        this.rotation = 0;
        this.rotateClockwise = true;
    }

    public TransformComponent(Vector2 position, Vector2 scale, float rotation, boolean rotateClockwise){
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        this.rotateClockwise = rotateClockwise;
    }
}
