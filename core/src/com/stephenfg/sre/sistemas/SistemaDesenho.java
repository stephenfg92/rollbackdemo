package com.stephenfg.sre.sistemas;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stephenfg.sre.componentes.ComponenteAnimacao;
import com.stephenfg.sre.componentes.ComponenteEstado;
import com.stephenfg.sre.componentes.ComponenteOrientacao;
import com.stephenfg.sre.componentes.ComponenteCorpoRigido;
import com.stephenfg.sre.componentes.ComponenteSpritesheet;
import com.stephenfg.sre.componentes.ComponenteTransformacao;

//Ref https://github.com/libgdx/ashley/blob/caac1ff50cb30d67be8469a7fae7579fd549fd07/tests/src/com/badlogic/ashley/tests/systems/RenderSystem.java#L30
public class SistemaDesenho extends EntitySystem {
    private ImmutableArray<Entity> entidades;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont font;

    private ComponentMapper<ComponenteTransformacao> mapeadorTransformacao = ComponentMapper.getFor(ComponenteTransformacao.class);
    private ComponentMapper<ComponenteSpritesheet> mapeadorSprite = ComponentMapper.getFor(ComponenteSpritesheet.class);

    //DBG
    private ComponentMapper<ComponenteEstado> mapeadorEstado = ComponentMapper.getFor(ComponenteEstado.class);
    private ComponentMapper<ComponenteCorpoRigido> mapeadorCorpoRigido = ComponentMapper.getFor(ComponenteCorpoRigido.class);

    public SistemaDesenho(OrthographicCamera camera, BitmapFont font){
        batch = new SpriteBatch();
        this.camera = camera;
        this.font = font;
    }

    @Override
    public void addedToEngine(Engine engine){
        entidades = engine.getEntitiesFor(Family.all(ComponenteTransformacao.class, ComponenteSpritesheet.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine){

    }

    @Override
    public void update(float deltaTime){
        ComponenteTransformacao transformacao;
        ComponenteSpritesheet sprite;

        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        for (int i = 0; i < entidades.size(); ++i){
            Entity e = entidades.get(i);

            transformacao = mapeadorTransformacao.get(e);
            sprite = mapeadorSprite.get(e);

            TextureRegion tr = sprite.regioes[sprite.regiaoAtual];

            float largura = tr.getRegionWidth();
            float altura = tr.getRegionHeight();

            float escalaX = transformacao.escala.x;
            float escalaY = transformacao.escala.y;

            float x = transformacao.posicao.x;
            float y = transformacao.posicao.y;

            float origemX = largura / 2;
            float origemY = 0;

            float rotacao = transformacao.rotacao;

            if (sprite.espelharX == true) {
                x += largura * escalaX;
                largura *= -1;
            }

            batch.draw(
                    tr,
                    x,
                    y,
                    origemX,
                    origemY,
                    largura,
                    altura,
                    escalaX,
                    escalaY,
                    rotacao
            );

        }
        batch.end();
    }
}
