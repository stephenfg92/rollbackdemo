package com.stephenfg.sre;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.stephenfg.sre.components.InputComponent;
import com.stephenfg.sre.components.SpriteComponent;
import com.stephenfg.sre.components.TransformComponent;
import com.stephenfg.sre.systems.InputSystem;
import com.stephenfg.sre.systems.RenderSystem;

public class SREGame extends ApplicationAdapter {
	PooledEngine engine;

	@Override
	public void create () {
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, 640, 480);

		Texture heroTexture = new Texture("warrior/individual/idle/Warrior_Idle_1.png");

		engine = new PooledEngine();
		engine.addSystem(new InputSystem());
		engine.addSystem(new RenderSystem(camera));

		Entity hero = engine.createEntity();
		hero.add(new InputComponent());
		hero.add(new TransformComponent(new Vector2(50, 50), new Vector2(1, 1)));
		hero.add(new SpriteComponent(new TextureRegion(heroTexture)));

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
	}
}
