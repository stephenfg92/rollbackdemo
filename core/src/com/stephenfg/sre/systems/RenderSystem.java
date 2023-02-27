package com.stephenfg.sre.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stephenfg.sre.components.SpriteComponent;
import com.stephenfg.sre.components.TransformComponent;

//Ref https://github.com/libgdx/ashley/blob/caac1ff50cb30d67be8469a7fae7579fd549fd07/tests/src/com/badlogic/ashley/tests/systems/RenderSystem.java#L30
public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);

    public RenderSystem (OrthographicCamera camera){
        batch = new SpriteBatch();
        this.camera = camera;
    }

    @Override
    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, SpriteComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine){

    }

    @Override
    public void update(float deltaTime){
        TransformComponent transform;
        SpriteComponent sprite;

        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        for (int i = 0; i < entities.size(); ++i){
            Entity e = entities.get(i);

            transform = tm.get(e);
            sprite = sm.get(e);

            //public void draw (TextureRegion region, float x, float y, float originX, float originY, float width, float height,
            //float scaleX, float scaleY, float rotation, boolean clockwise);
            /*batch.draw(
                    sprite.region,
                    transform.position.x, transform.position.y,
                    sprite.originX, sprite.originY,
                    sprite.width, sprite.height,
                    transform.scale.x, transform.scale.y,
                    transform.rotation,
                    transform.rotateClockwise
            );*/

            batch.draw(sprite.region, transform.position.x, transform.position.y);

        }
        batch.end();
    }
}
