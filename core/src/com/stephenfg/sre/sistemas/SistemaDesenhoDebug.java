package com.stephenfg.sre.sistemas;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.stephenfg.sre.componentes.ComponenteAABB;
import com.stephenfg.sre.componentes.ComponenteCorpoRigido;
import com.stephenfg.sre.componentes.ComponenteDebug;
import com.stephenfg.sre.componentes.ComponenteEstado;
import com.stephenfg.sre.componentes.ComponenteSpritesheet;
import com.stephenfg.sre.componentes.ComponenteTransformacao;

//Ref https://github.com/libgdx/ashley/blob/caac1ff50cb30d67be8469a7fae7579fd549fd07/tests/src/com/badlogic/ashley/tests/systems/RenderSystem.java#L30
public class SistemaDesenhoDebug extends EntitySystem {
    private ImmutableArray<Entity> entidades;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont font;
    ShapeRenderer shape;

    private ComponentMapper<ComponenteTransformacao> mapeadorTransformacao = ComponentMapper.getFor(ComponenteTransformacao.class);
    private ComponentMapper<ComponenteSpritesheet> mapeadorSprite = ComponentMapper.getFor(ComponenteSpritesheet.class);

    private ComponentMapper<ComponenteEstado> mapeadorEstado = ComponentMapper.getFor(ComponenteEstado.class);
    private ComponentMapper<ComponenteCorpoRigido> mapeadorCorpoRigido = ComponentMapper.getFor(ComponenteCorpoRigido.class);
    private ComponentMapper<ComponenteAABB> mapeadorAABB = ComponentMapper.getFor(ComponenteAABB.class);
    public SistemaDesenhoDebug(OrthographicCamera camera, BitmapFont font){
        batch = new SpriteBatch();
        this.camera = camera;
        this.font = font;
        this.shape = new ShapeRenderer();
    }

    @Override
    public void addedToEngine(Engine engine){
        entidades = engine.getEntitiesFor(Family.all(ComponenteDebug.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine){

    }

    @Override
    public void update(float deltaTime){
        ComponenteTransformacao transformacao;
        ComponenteSpritesheet sprite;
        ComponenteEstado estado;
        ComponenteCorpoRigido corpoRigido;
        ComponenteAABB aabb;

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);

        for (int i = 0; i < entidades.size(); ++i){
            Entity e = entidades.get(i);

            transformacao = mapeadorTransformacao.get(e);
            sprite = mapeadorSprite.get(e);

            estado = mapeadorEstado.get(e);
            corpoRigido = mapeadorCorpoRigido.get(e);

            aabb = mapeadorAABB.get(e);

            //DBG
            if (estado != null) {
                font.draw(batch, "FPS=" + Gdx.graphics.getFramesPerSecond(), 0, 480);
                font.draw(batch, "ESTADO = " + estado.tipoEstado.toString(), 0, 460);
                font.draw(batch, "VELOCIDADE = " + corpoRigido.velocidade.toString(), 0, 440);
                font.draw(batch, "ORIENTAÇÃO = " + sprite.espelharX, 0, 420);
            }

            if (aabb != null && transformacao != null) {
                shape.begin(ShapeRenderer.ShapeType.Line);

                Color cor = aabb.colidindo ? Color.RED : Color.GREEN;
                shape.setColor(cor);

                // Calcula o canto inferior esquerdo da AABB
                float extensaoAABBx = aabb.extensao.x * transformacao.escala.x;
                float extensaoAABBy = aabb.extensao.y * transformacao.escala.y;

                float cantoInferiorEsquerdoX = transformacao.centro.x - extensaoAABBx;
                float cantoInferiorEsquerdoY = transformacao.centro.y - extensaoAABBy;

                shape.rect(
                        cantoInferiorEsquerdoX,
                        cantoInferiorEsquerdoY,
                        extensaoAABBx * 2,
                        extensaoAABBy * 2
                );
                shape.end();
            }

        }
        batch.end();
        camera.update();
    }
}
