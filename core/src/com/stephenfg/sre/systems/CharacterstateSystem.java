package com.stephenfg.sre.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.stephenfg.sre.components.CharacterstateComponent;
import com.stephenfg.sre.data.CharacterState;
import com.stephenfg.sre.events.novo.Event;
import com.stephenfg.sre.events.novo.EventBus;
import com.stephenfg.sre.events.statechange.StatechangeEvent;
import com.stephenfg.sre.events.statechange.StatechangePublisher;

import java.lang.reflect.InvocationTargetException;


public class CharacterstateSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<CharacterstateComponent> csm = ComponentMapper.getFor(CharacterstateComponent.class);
    private StatechangePublisher statechangePublisher;
    private EventBus eventBus;

    public CharacterstateSystem(EventBus eventBus){
        this.eventBus = eventBus;
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
                    //statechangePublisher.notifySubscribers(new StatechangeEvent(e, CharacterState.NONE, CharacterState.IDLE));
                    StatechangeEvent evt = new StatechangeEvent(e, CharacterState.NONE, CharacterState.IDLE);
                    try {
                        eventBus.emitEvent(evt);
                    } catch (NoSuchMethodException ex) {
                        throw new RuntimeException(ex);
                    } catch (InvocationTargetException ex) {
                        throw new RuntimeException(ex);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
            }
        }
    }
}
