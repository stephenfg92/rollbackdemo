package com.stephenfg.sre.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SpriteComponent implements Component {
    public TextureRegion region;
    public float originX = 0;
    public float originY = 0;
    public float width;
    public float height;

    public SpriteComponent(TextureRegion region){
        this.region = region;
        this.width = region.getRegionWidth();
        this.height = region.getRegionHeight();
    }

    public SpriteComponent(TextureRegion region, float originX, float originY, float width, float height){
        this.region = region;
        this.originX = originX;
        this.originY = originY;
        this.width = width;
        this.height = height;
    }
}
