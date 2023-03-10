package com.stephenfg.sre;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.stephenfg.sre.components.CharacterstateComponent;
import com.stephenfg.sre.components.FacingComponent;
import com.stephenfg.sre.components.InputComponent;
import com.stephenfg.sre.components.RigidbodyComponent;
import com.stephenfg.sre.components.SpritesheetComponent;
import com.stephenfg.sre.components.TransformComponent;
import com.stephenfg.sre.data.CharacterState;
import com.stephenfg.sre.data.hero.HeroData;
import com.stephenfg.sre.events.EventBus;
import com.stephenfg.sre.events.StatechangeEvent;
import com.stephenfg.sre.systems.AnimationSystem;
import com.stephenfg.sre.systems.CharacterstateSystem;
import com.stephenfg.sre.systems.InputSystem;
import com.stephenfg.sre.systems.MovementSystem;
import com.stephenfg.sre.systems.RenderSystem;
import com.stephenfg.sre.util.MakeTextureRegionArray;
import com.stephenfg.sre.util.TextureDisposer;

public class SREGame extends ApplicationAdapter {
	PooledEngine engine;
	TextureDisposer texDisposer;

	@Override
	public void create () {
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, 640, 480);
		BitmapFont font = new BitmapFont();
		texDisposer = new TextureDisposer();
		EventBus eventBus = new EventBus();

		engine = new PooledEngine();
		engine.addSystem(new InputSystem(0));
		engine.addSystem(new CharacterstateSystem(eventBus));
		engine.addSystem(new MovementSystem());
		engine.addSystem((EntitySystem) new AnimationSystem(eventBus).subscribeToEvent(StatechangeEvent.class));
		engine.addSystem(new RenderSystem(camera, font));

		Entity hero = engine.createEntity();
		hero.add(new InputComponent());
		hero.add(new CharacterstateComponent(CharacterState.NONE));
		hero.add(new FacingComponent());
		hero.add(new TransformComponent(new Vector2(50, 50), new Vector2(3, 3)));
		hero.add(new RigidbodyComponent());
		hero.add(new SpritesheetComponent(MakeTextureRegionArray.make(texDisposer, HeroData.spritePath, HeroData.spriteFrameW, HeroData.spriteFrameH), HeroData.animFps));

		engine.addEntity(hero);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		engine.update(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		texDisposer.disposeTextures();
		//this.dispose();
	}
}
