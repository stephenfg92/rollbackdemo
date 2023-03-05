package com.stephenfg.sre.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpritesheetComponent implements Component {
    public TextureRegion[] regions;
    public int frameRate;
    public int numFrames;
    public int currentFrame;
    public float lastUpdate;
    public int startingRegion;
    public int endingRegion;


    public SpritesheetComponent(TextureRegion[] regions, int frameRate) {
        this.regions = regions;
        this.numFrames = 0;
        this.currentFrame = 0;
        this.frameRate = frameRate;
        this.lastUpdate = 0;
    }



}
