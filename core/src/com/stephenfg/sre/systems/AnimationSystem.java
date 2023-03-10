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
import com.stephenfg.sre.events.CallbackName;
import com.stephenfg.sre.data.hero.HeroData;
import com.stephenfg.sre.events.Callback;
import com.stephenfg.sre.events.Event;
import com.stephenfg.sre.events.EventBus;
import com.stephenfg.sre.events.StatechangeEvent;
import com.stephenfg.sre.events.Subscriber;
import com.stephenfg.sre.util.Range;
import com.stephenfg.sre.util.StrictfpMath;


public class AnimationSystem extends EntitySystem implements Subscriber {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<SpritesheetComponent> sm = ComponentMapper.getFor(SpritesheetComponent.class);
    private float accumulatedTime = 0.0f;
    private EventBus eventBus;

    public AnimationSystem(EventBus eventBus){
        this.eventBus = eventBus;
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
            sprite.currentFrame %= sprite.numFrames + sprite.startingRegion;
            if(sprite.currentFrame == 0)
                sprite.currentFrame = sprite.startingRegion;

            sprite.lastUpdate = accumulatedTime;
        }
    }

    public void onStateChange(StatechangeEvent evt) {
        SpritesheetComponent sprite = sm.get(evt.receiver);
        Range range = HeroData.heroAnims.get(evt.newState);

        sprite.numFrames = range.size;
        sprite.startingRegion = range.start;
        sprite.endingRegion = range.end;
        sprite.currentFrame = sprite.startingRegion;
    }

    @Override
    public Subscriber subscribeToEvent(Class<? extends Event> event) {
        String callbackName = CallbackName.getName(event);
        eventBus.subscribeToEvent(event, new Callback(this, callbackName));
        return this;
    }
}
