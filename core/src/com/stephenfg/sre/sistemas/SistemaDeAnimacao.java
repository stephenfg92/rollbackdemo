package com.stephenfg.sre.sistemas;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.stephenfg.sre.componentes.ComponenteAnimacao;
import com.stephenfg.sre.componentes.ComponenteEstado;
import com.stephenfg.sre.componentes.ComponenteSpritesheet;
import com.stephenfg.sre.eventos.BarramentoDeEventos;
import com.stephenfg.sre.eventos.EventoMudancaDeEstado;
import com.stephenfg.sre.eventos.TratativaDeEvento;
import com.stephenfg.sre.recursos.GerenciadorDeRecursos;
import com.stephenfg.sre.recursos.Textura;
import com.stephenfg.sre.utilidades.Intervalo;
import com.stephenfg.sre.utilidades.StrictfpMath;


public class SistemaDeAnimacao extends EntitySystem {
    private ImmutableArray<Entity> entidades;
    private ComponentMapper<ComponenteSpritesheet> mapeadorSprites = ComponentMapper.getFor(ComponenteSpritesheet.class);
    private ComponentMapper<ComponenteAnimacao> mapeadorAnimacao = ComponentMapper.getFor(ComponenteAnimacao.class);
    private float tempoAcumulado = 0.0f;
    private GerenciadorDeRecursos recursos;

    public SistemaDeAnimacao(GerenciadorDeRecursos recursos){
        this.recursos = recursos;
    }

    @Override
    public void addedToEngine(Engine engine){
        entidades = engine.getEntitiesFor(Family.all(ComponenteEstado.class, ComponenteSpritesheet.class, ComponenteAnimacao.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine){

    }

    @Override
    public void update(float deltaTime){
        tempoAcumulado = StrictfpMath.strictSum(tempoAcumulado, Gdx.graphics.getDeltaTime());
        ComponenteSpritesheet sprite;
        ComponenteAnimacao animacao;

        for (int i = 0; i < entidades.size(); ++i){
            Entity e = entidades.get(i);
            sprite = mapeadorSprites.get(e);
            animacao = mapeadorAnimacao.get(e);

            atualizarSprite(sprite, animacao);
        }
    }

    private strictfp void atualizarSprite(ComponenteSpritesheet sprite, ComponenteAnimacao animacao){
        float dt = (tempoAcumulado - animacao.ultimaAtualizacao);
        int quadrosParaAtualizar = (int)(dt / (1.0f/animacao.taxaDeQuadros));
        if (quadrosParaAtualizar > 0){
            animacao.quadroAtual += quadrosParaAtualizar;
            animacao.quadroAtual %= animacao.qtdQuadros + animacao.regiaoInicial;
            if(animacao.quadroAtual == 0)
                animacao.quadroAtual = animacao.regiaoInicial;

            animacao.ultimaAtualizacao = tempoAcumulado;
        }

        sprite.regiaoAtual = animacao.quadroAtual;
    }

    @TratativaDeEvento(EventoMudancaDeEstado.class)
    public void aoMudarEstado(EventoMudancaDeEstado evt) {
        ComponenteAnimacao animacao = mapeadorAnimacao.get(evt.destinatario);
        ComponenteSpritesheet sprite = mapeadorSprites.get(evt.destinatario);
        Textura textura = recursos.obterTextura(animacao.id);
        Intervalo intervalo = textura.animacoes.get(evt.novoEstado);

        animacao.qtdQuadros = intervalo.tamanho;
        animacao.taxaDeQuadros = textura.taxaDeQuadros;
        animacao.regiaoInicial = intervalo.inicio;
        animacao.regiaoFinal = intervalo.fim;
        animacao.quadroAtual = animacao.regiaoInicial;
        sprite.regiaoAtual = animacao.regiaoInicial;
    }

}
