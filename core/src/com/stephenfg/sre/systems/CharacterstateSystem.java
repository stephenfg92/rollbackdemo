package com.stephenfg.sre.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.ashley.utils.ImmutableArray;
import com.stephenfg.sre.components.CharacterstateComponent;
import com.stephenfg.sre.components.InputComponent;
import com.stephenfg.sre.components.SpritesheetComponent;
import com.stephenfg.sre.data.CharacterState;
import com.stephenfg.sre.data.hero.HeroData;
import com.stephenfg.sre.events.EventManager;
import com.stephenfg.sre.events.StatechangeEvent;
import com.stephenfg.sre.util.Range;


public class CharacterstateSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<CharacterstateComponent> csm = ComponentMapper.getFor(CharacterstateComponent.class);
    private EventManager evtManager;

    public CharacterstateSystem(EventManager evtManager){
        this.evtManager = evtManager;
    }

    @Override
    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(CharacterstateComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine){

    }

    @Override
    public void update(float deltaTime){
        CharacterstateComponent state;

        for (int i = 0; i < entities.size(); ++i){
            Entity e = entities.get(i);
            state = csm.get(e);

            switch (state.state){
                case NONE:
                    state.state = CharacterState.IDLE;
                    evtManager.DispatchStateChangeEvent(e, new StatechangeEvent(e, CharacterState.NONE, CharacterState.IDLE));
            }
        }
    }
}