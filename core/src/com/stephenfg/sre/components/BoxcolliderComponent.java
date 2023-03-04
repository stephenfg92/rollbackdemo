package com.stephenfg.sre.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class BoxcolliderComponent  implements Component {
    public int width;
    public int height;
    public Vector2 offset;

    public BoxcolliderComponent(int width, int height){
        this.width = width;
        this.height = height;
        this.offset = new Vector2();
    }

    public BoxcolliderComponent(int width, int height, Vector2 offset){
        this.width = width;
        this.height = height;
        this.offset = offset;
    }
}
