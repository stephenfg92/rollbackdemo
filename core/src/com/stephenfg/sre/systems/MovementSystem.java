package com.stephenfg.sre.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.stephenfg.sre.components.InputComponent;
import com.stephenfg.sre.components.RigidbodyComponent;
import com.stephenfg.sre.components.TransformComponent;
import com.stephenfg.sre.data.hero.HeroData;
import com.stephenfg.sre.data.InputActions;

public class MovementSystem  extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<InputComponent> im = ComponentMapper.getFor(InputComponent.class);
    private ComponentMapper<RigidbodyComponent> rm = ComponentMapper.getFor(RigidbodyComponent.class);
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    public MovementSystem(){

    }

    @Override
    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(InputComponent.class, RigidbodyComponent.class, TransformComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine){

    }

    private boolean isBitTrue(Byte b, int index){
        return (b.byteValue() >> index) % 2 == 1;
    }

    private strictfp Vector2 getVelocity(Byte b){
        Vector2 velocity = new Vector2();

        if (isBitTrue(b, InputActions.RIGHT))
            velocity.x += HeroData.hSpeed;
        if (isBitTrue(b, InputActions.LEFT))
            velocity.x -= HeroData.hSpeed;
        //if (!isBitTrue(b, InputActions.LEFT) && !isBitTrue(b, InputActions.RIGHT))
        //    velocity.x = 0;

        return velocity;
    }

    private strictfp void applyVelocity(Vector2 position, Vector2 velocity, Float deltaTime){
        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;
    }

    @Override
    public void update(float deltaTime){
        InputComponent input;
        RigidbodyComponent rigidbody;
        TransformComponent transform;

        for (int i = 0; i < entities.size(); ++i){
            Entity e = entities.get(i);
            input = im.get(e);
            rigidbody = rm.get(e);
            transform = tm.get(e);

            rigidbody.previousVelocity = rigidbody.velocity;
            rigidbody.velocity = getVelocity(input.bits);
            applyVelocity(transform.position, rigidbody.velocity, deltaTime);
        }
    }

}
