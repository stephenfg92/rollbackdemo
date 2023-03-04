package com.stephenfg.sre.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stephenfg.sre.util.Range;

public class SpritesheetComponent implements Component {
    public TextureRegion[] regions;
    public int frameRate;
    public int numFrames;
    public int startingRegion;
    public int endingRegion;
    public int currentFrame;
    public float startTime;


    public SpritesheetComponent(TextureRegion[] regions, int frameRate) {
        this.regions = regions;
        this.numFrames = 0;
        this.currentFrame = 0;
        this.frameRate = frameRate;
        this.startTime = 0;
    }



}
