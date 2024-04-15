package com.stephenfg.sre.sistemas;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.stephenfg.sre.componentes.ComponenteComando;
import com.stephenfg.sre.componentes.ComponenteCorpoRigido;
import com.stephenfg.sre.componentes.ComponenteOrientacao;
import com.stephenfg.sre.componentes.ComponenteTransformacao;
import com.stephenfg.sre.data.hero.HeroData;
import com.stephenfg.sre.data.AcoesDeEntrada;

public class SistemaMovimento extends EntitySystem {
    private ImmutableArray<Entity> entidades;
    private ComponentMapper<ComponenteComando> mapeadorEntrada = ComponentMapper.getFor(ComponenteComando.class);
    private ComponentMapper<ComponenteCorpoRigido> mapeadorCorpoRigido = ComponentMapper.getFor(ComponenteCorpoRigido.class);
    private ComponentMapper<ComponenteTransformacao> mapeadorTransformacao = ComponentMapper.getFor(ComponenteTransformacao.class);

    public SistemaMovimento(){

    }

    @Override
    public void addedToEngine(Engine engine){
        entidades = engine.getEntitiesFor(Family.all(ComponenteComando.class, ComponenteCorpoRigido.class, ComponenteTransformacao.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine){

    }

    private boolean bitVerdadeiro(Byte b, int index){
        return (b.byteValue() >> index) % 2 == 1;
    }

    private strictfp Vector2 obterVelocidade(Byte b){
        Vector2 velocidade = new Vector2();

        if (bitVerdadeiro(b, AcoesDeEntrada.DIREITA))
            velocidade.x += HeroData.velocidadeHorizontal;
        if (bitVerdadeiro(b, AcoesDeEntrada.ESQUERDA))
            velocidade.x -= HeroData.velocidadeHorizontal;
        //if (!isBitTrue(b, InputActions.LEFT) && !isBitTrue(b, InputActions.RIGHT))
        //    velocity.x = 0;

        return velocidade;
    }

    private strictfp void aplicarVelocidade(Vector2 posicao, Vector2 velocidade, Float deltaTempo){
        posicao.x += velocidade.x * deltaTempo;
        posicao.y += velocidade.y * deltaTempo;
    }

    @Override
    public void update(float deltaTime){
        ComponenteComando entrada;
        ComponenteCorpoRigido corpoRigido;
        ComponenteTransformacao transformacao;
        ComponenteOrientacao orientacao;

        for (int i = 0; i < entidades.size(); ++i){
            Entity e = entidades.get(i);
            entrada = mapeadorEntrada.get(e);
            corpoRigido = mapeadorCorpoRigido.get(e);
            transformacao = mapeadorTransformacao.get(e);

            corpoRigido.velocidadeAnterior = corpoRigido.velocidade;
            corpoRigido.velocidade = obterVelocidade(entrada.bits);
            aplicarVelocidade(transformacao.posicao, corpoRigido.velocidade, deltaTime);
        }
    }

}
