package com.stephenfg.sre.sistemas;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.stephenfg.sre.componentes.ComponenteComando;
import com.stephenfg.sre.componentes.ComponenteCorpoRigido;
import com.stephenfg.sre.componentes.ComponenteOrientacao;
import com.stephenfg.sre.componentes.ComponenteSpritesheet;
import com.stephenfg.sre.componentes.ComponenteTransformacao;

public class SistemaDeOrientacao extends EntitySystem {
    private ImmutableArray<Entity> entidades;
    private ComponentMapper<ComponenteCorpoRigido> mapeadorCorpoRigido = ComponentMapper.getFor(ComponenteCorpoRigido.class);
    private ComponentMapper<ComponenteSpritesheet> mapeadorSpritesheet = ComponentMapper.getFor(ComponenteSpritesheet.class);
    @Override
    public void addedToEngine(Engine engine){
        entidades = engine.getEntitiesFor(Family.all(ComponenteCorpoRigido.class, ComponenteSpritesheet.class).get());
    }

    public SistemaDeOrientacao() {

    }

    @Override
    public void update(float deltaTime){
        for (Entity e : entidades){
            ComponenteCorpoRigido cr = mapeadorCorpoRigido.get(e);

            if ( (cr.velocidadeAnterior.x * cr.velocidade.x) < 0) {
                ComponenteSpritesheet cs = mapeadorSpritesheet.get(e);
                cs.espelharX = !cs.espelharX;
                return;
            }

            if (cr.velocidadeAnterior.x == 0 && cr.velocidade.x > 0){
                ComponenteSpritesheet cs = mapeadorSpritesheet.get(e);
                cs.espelharX = false;
                return;
            }

            if (cr.velocidadeAnterior.x == 0 && cr.velocidade.x < 0){
                ComponenteSpritesheet cs = mapeadorSpritesheet.get(e);
                cs.espelharX = true;
                return;
            }

        }
    }
}
