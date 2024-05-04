package com.stephenfg.sre.sistemas;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stephenfg.sre.componentes.ComponenteSpritesheet;
import com.stephenfg.sre.componentes.ComponenteTransformacao;
import com.stephenfg.sre.recursos.GerenciadorDeRecursos;

//Ref https://github.com/libgdx/ashley/blob/caac1ff50cb30d67be8469a7fae7579fd549fd07/tests/src/com/badlogic/ashley/tests/systems/RenderSystem.java#L30
public class SistemaDesenho extends EntitySystem {
    private ImmutableArray<Entity> entidades;
    GerenciadorDeRecursos recursos;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont font;

    private ComponentMapper<ComponenteTransformacao> mapeadorTransformacao = ComponentMapper.getFor(ComponenteTransformacao.class);
    private ComponentMapper<ComponenteSpritesheet> mapeadorSprite = ComponentMapper.getFor(ComponenteSpritesheet.class);

    public SistemaDesenho(OrthographicCamera camera, BitmapFont font, GerenciadorDeRecursos recursos){
        batch = new SpriteBatch();
        this.camera = camera;
        this.font = font;
        this.recursos = recursos;
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

            TextureRegion tr = recursos.obterTextura(sprite.id).regioes[sprite.regiaoAtual];

            float largura = tr.getRegionWidth() * transformacao.escala.x;
            float altura = tr.getRegionHeight() * transformacao.escala.y;

            float posicaoX = transformacao.centro.x - (largura / 2);
            float posicaoY = transformacao.centro.y - (altura / 2);

            float origemX = sprite.origemX;
            float origemY = sprite.origemY;

            float rotacao = transformacao.rotacao;

            if (sprite.espelharX) {
                origemX = largura - origemX;  // Ajusta a origem X quando a sprite é espelhada
                posicaoX += largura;  // Ajusta a posição X devido à inversão do espelhamento
                largura *= -1;
            }

            batch.draw(
                    tr,
                    posicaoX,
                    posicaoY,
                    origemX,
                    origemY,
                    largura,  // Usa o valor absoluto para evitar distorções no tamanho
                    altura,
                    1,  // A escala já foi aplicada ao calcular largura e altura
                    1,  // A escala já foi aplicada ao calcular largura e altura
                    rotacao
            );

        }
        batch.end();
    }
}
