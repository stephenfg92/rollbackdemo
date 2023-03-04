package com.stephenfg.sre.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.stephenfg.sre.components.InputComponent;
import com.stephenfg.sre.data.InputActions;

public class InputSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<InputComponent> im = ComponentMapper.getFor(InputComponent.class);

    public InputSystem(int priority){
        this.priority = priority;
    }

    @Override
    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(InputComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine){

    }

    private byte getBits(){
        byte b = 0b00000000;

        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            b |= (1 << InputActions.UP);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            b |= (1 << InputActions.DOWN);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            b |= (1 << InputActions.LEFT);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            b |= (1 << InputActions.RIGHT);

        return b;
    }

    @Override
    public void update(float deltaTime){
        InputComponent input;

        for (int i = 0; i < entities.size(); ++i){
            Entity e = entities.get(i);
            input = im.get(e);

            input.bits = getBits();
        }
    }
}
