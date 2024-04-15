package com.stephenfg.sre.sistemas;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.stephenfg.sre.componentes.ComponenteComando;
import com.stephenfg.sre.data.AcoesDeEntrada;

public class SistemaComando extends EntitySystem {
    private ImmutableArray<Entity> entidades;
    private ComponentMapper<ComponenteComando> mapeadorComando = ComponentMapper.getFor(ComponenteComando.class);

    public SistemaComando(int priority){
        this.priority = priority;
    }

    @Override
    public void addedToEngine(Engine engine){
        entidades = engine.getEntitiesFor(Family.all(ComponenteComando.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine){

    }

    private byte obterBits(){
        byte b = 0b00000000;

        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            b |= (1 << AcoesDeEntrada.CIMA);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            b |= (1 << AcoesDeEntrada.BAIXO);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            b |= (1 << AcoesDeEntrada.ESQUERDA);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            b |= (1 << AcoesDeEntrada.DIREITA);

        //b |= (1 << InputActions.RIGHT);

        return b;
    }

    @Override
    public void update(float deltaTempo){
        ComponenteComando comando;

        for (int i = 0; i < entidades.size(); ++i){
            Entity e = entidades.get(i);
            comando = mapeadorComando.get(e);

            comando.bits = obterBits();
        }
    }
}
