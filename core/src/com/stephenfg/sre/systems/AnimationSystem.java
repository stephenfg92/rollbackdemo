package com.stephenfg.sre.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.stephenfg.sre.components.CharacterstateComponent;
import com.stephenfg.sre.components.SpritesheetComponent;
import com.stephenfg.sre.data.hero.HeroData;
import com.stephenfg.sre.events.EventManager;
import com.stephenfg.sre.events.StatechangeEvent;
import com.stephenfg.sre.util.Range;
import com.stephenfg.sre.util.StrictfpMath;

import javax.swing.plaf.nimbus.State;

import jdk.jfr.Event;


public class AnimationSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<CharacterstateComponent> csm = ComponentMapper.getFor(CharacterstateComponent.class);
    private ComponentMapper<SpritesheetComponent> sm = ComponentMapper.getFor(SpritesheetComponent.class);
    EventManager evtManager;
    Listener<StatechangeEvent> stateChangeListener;
    private float accumulatedTime = 0.0f;

    public AnimationSystem(EventManager evtManager){
        this.evtManager = evtManager;
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

        CharacterstateComponent state;
        SpritesheetComponent sprite;

        for (int i = 0; i < entities.size(); ++i){
            Entity e = entities.get(i);
            state = csm.get(e);
            sprite = sm.get(e);

            Array<StatechangeEvent> arrevt = lookForStateChangeEvents(e);
            applyEventToSprite(arrevt, sprite);

            sprite.currentFrame = getCurrentFrame(sprite, accumulatedTime, sprite.startTime, sprite.frameRate, sprite.numFrames);

        }
    }

    private strictfp int getCurrentFrame(SpritesheetComponent sprite, float accumulatedTime, float startTime, int frameRate, int numFrames){
        //...
    }


    private Array<StatechangeEvent> lookForStateChangeEvents(Entity e){
        return evtManager.getEventsFor(e);
    }

    private void applyEventToSprite(Array<StatechangeEvent> stateChangeEventsQ, SpritesheetComponent sprite){
        if (stateChangeEventsQ.isEmpty()){
            return;
        }

        for (StatechangeEvent evt : stateChangeEventsQ){
            Range range = HeroData.heroAnims.get(evt.newState);
            sprite.startingRegion = range.start;
            sprite.endingRegion = range.end;
            sprite.startTime = accumulatedTime;
            sprite.currentFrame = sprite.startingRegion;
            sprite.numFrames = range.size;
        }
    }

}
