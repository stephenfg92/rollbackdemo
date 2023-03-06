package com.stephenfg.sre.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.stephenfg.sre.components.CharacterstateComponent;
import com.stephenfg.sre.components.SpritesheetComponent;
import com.stephenfg.sre.data.hero.HeroData;
import com.stephenfg.sre.events.statechange.StatechangeEvent;
import com.stephenfg.sre.events.statechange.StatechangeSubscriber;
import com.stephenfg.sre.util.Range;
import com.stephenfg.sre.util.StrictfpMath;


public class AnimationSystem extends EntitySystem implements StatechangeSubscriber {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<SpritesheetComponent> sm = ComponentMapper.getFor(SpritesheetComponent.class);
    private float accumulatedTime = 0.0f;

    public AnimationSystem(){
    }

    @Override
    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(CharacterstateComponent.class, SpritesheetComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine){

    }

    @Override
    public void update(float deltaTime){
        accumulatedTime = StrictfpMath.strictSum(accumulatedTime, Gdx.graphics.getDeltaTime());
        SpritesheetComponent sprite;

        for (int i = 0; i < entities.size(); ++i){
            Entity e = entities.get(i);
            sprite = sm.get(e);

            updateSprite(sprite);
        }
    }

    private strictfp void updateSprite(SpritesheetComponent sprite){
        float dt = (accumulatedTime - sprite.lastUpdate);
        int framesToUpdate = (int)(dt / (1.0f/sprite.frameRate));
        if (framesToUpdate > 0){
            sprite.currentFrame += framesToUpdate;
            sprite.currentFrame %= sprite.numFrames;
            sprite.lastUpdate = accumulatedTime;
        }
    }

    @Override
    public void onStateChange(StatechangeEvent evt) {
        SpritesheetComponent sprite = sm.get(evt.receiver);
        Range range = HeroData.heroAnims.get(evt.newState);

        sprite.startingRegion = range.start;
        sprite.endingRegion = range.end;
        sprite.lastUpdate = accumulatedTime;
        sprite.currentFrame = sprite.startingRegion;
        sprite.numFrames = range.size;
    }
}
