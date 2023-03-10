package com.stephenfg.sre.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stephenfg.sre.components.CharacterstateComponent;
import com.stephenfg.sre.components.FacingComponent;
import com.stephenfg.sre.components.RigidbodyComponent;
import com.stephenfg.sre.components.SpritesheetComponent;
import com.stephenfg.sre.components.TransformComponent;

//Ref https://github.com/libgdx/ashley/blob/caac1ff50cb30d67be8469a7fae7579fd549fd07/tests/src/com/badlogic/ashley/tests/systems/RenderSystem.java#L30
public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont font;

    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<SpritesheetComponent> sm = ComponentMapper.getFor(SpritesheetComponent.class);
    private ComponentMapper<FacingComponent> fm = ComponentMapper.getFor(FacingComponent.class);

    //DBG
    private ComponentMapper<CharacterstateComponent> csm = ComponentMapper.getFor(CharacterstateComponent.class);
    private ComponentMapper<RigidbodyComponent> rm = ComponentMapper.getFor(RigidbodyComponent.class);

    public RenderSystem (OrthographicCamera camera, BitmapFont font){
        batch = new SpriteBatch();
        this.camera = camera;
        this.font = font;
    }

    @Override
    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, SpritesheetComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine){

    }

    @Override
    public void update(float deltaTime){
        TransformComponent transform;
        SpritesheetComponent sprite;
        FacingComponent facing;

        //DBG
        CharacterstateComponent state;
        RigidbodyComponent rb;

        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        for (int i = 0; i < entities.size(); ++i){
            Entity e = entities.get(i);

            transform = tm.get(e);
            sprite = sm.get(e);
            facing = fm.get(e);

            //DBG
            state = csm.get(e);
            rb = rm.get(e);

            TextureRegion tr = sprite.regions[sprite.currentFrame];
            float width = tr.getRegionWidth() * transform.scale.x;
            float height = tr.getRegionHeight() * transform.scale.y;
            float x = transform.position.x;
            float y = transform.position.y;
            boolean flip = (facing.facingLeft == true);
            batch.draw(tr, flip ? x+width : x, y, flip ? -width : width, height);
            //batch.draw(tr, flip ? x+width : x, y, 0, 0, flip ? -width : width, height, transform.scale.x,  transform.scale.y, 0);

            //batch.draw(tr, transform.position.x, transform.position.y);


            //DBG
            font.draw(batch, "FPS=" + Gdx.graphics.getFramesPerSecond(), 0, 480);
            font.draw(batch, "STATE = " + state.state.toString(), 0, 460);
            font.draw(batch, "VELOCITY = " + rb.velocity.toString(), 0, 440);
            font.draw(batch, "FACING = " + facing.facingLeft, 0, 420);

        }
        batch.end();
    }
}
