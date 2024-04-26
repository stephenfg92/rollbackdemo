package com.stephenfg.sre.sistemas;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.stephenfg.sre.colisao.Colisao;
import com.stephenfg.sre.componentes.ComponenteColisorCaixa;
import com.stephenfg.sre.componentes.ComponenteTransformacao;
import com.stephenfg.sre.eventos.BarramentoDeEventos;
import com.stephenfg.sre.eventos.EventoColisao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SistemaDeColisao extends EntitySystem {
    private BarramentoDeEventos barramento;
    private ImmutableArray<Entity> entidades;
    private List<EventoColisao> colisoes;
    private ComponentMapper<ComponenteTransformacao> mapeadorTransformacao = ComponentMapper.getFor(ComponenteTransformacao.class);
    private ComponentMapper<ComponenteColisorCaixa> mapeadorColisorCaixa = ComponentMapper.getFor(ComponenteColisorCaixa.class);

    public SistemaDeColisao(int prioridade, BarramentoDeEventos eventos){
        this.colisoes = colisoes;
        this.priority = prioridade;
        this.barramento = eventos;
    }
    public void addedToEngine(Engine engine){
        entidades = engine.getEntitiesFor(Family.all(ComponenteTransformacao.class, ComponenteColisorCaixa.class).get());
    }

    @Override
    public void update(float deltaTime){
        ComponenteTransformacao transformacaoA;
        ComponenteColisorCaixa colisorA;
        ComponenteTransformacao transformacaoB;
        ComponenteColisorCaixa colisorB;
        for (int i = 0; i < entidades.size(); i++){
            Entity a = entidades.get(i);
            transformacaoA = mapeadorTransformacao.get(a);
            colisorA = mapeadorColisorCaixa.get(a);

            for (int j = i + 1; j < entidades.size(); j++) {
                Entity b = entidades.get(j);

                transformacaoB = mapeadorTransformacao.get(b);
                colisorB = mapeadorColisorCaixa.get(b);

                Colisao colisao = checarColisao(transformacaoA, colisorA, transformacaoB, colisorB);

                try {
                    if (colisao != null)
                        emitirEventoDeColisao(a, b, colisao);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void emitirEventoDeColisao(Entity a, Entity b, Colisao colisao) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        EventoColisao evento = new EventoColisao(a, b, colisao);
        barramento.emitirEvento(evento);
    }

    private Colisao checarColisao(ComponenteTransformacao transformacaoA, ComponenteColisorCaixa colisorA, ComponenteTransformacao transformacaoB, ComponenteColisorCaixa colisorB) {
        Vector2 posicaoA = transformacaoA.posicao;
        Vector2 escalaA = transformacaoA.escala;
        Vector2 deslocamentoA = colisorA.deslocamento;
        Vector2 dimensoesA = colisorA.dimensoes;

        Vector2 posicaoB = transformacaoB.posicao;
        Vector2 escalaB = transformacaoB.escala;
        Vector2 deslocamentoB = colisorB.deslocamento;
        Vector2 dimensoesB = colisorB.dimensoes;

        float minAx = posicaoA.x + deslocamentoA.x * escalaA.x;
        float maxAx = minAx + dimensoesA.x * escalaA.x;
        float minAy = posicaoA.y + deslocamentoA.y * escalaA.y;
        float maxAy = minAy + dimensoesA.y * escalaA.y;

        float minBx = posicaoB.x + deslocamentoB.x * escalaB.x;
        float maxBx = minBx + dimensoesB.x * escalaB.x;
        float minBy = posicaoB.y + deslocamentoB.y * escalaB.y;
        float maxBy = minBy + dimensoesB.y * escalaB.y;

        if (maxAx < minBx || maxBx < minAx || maxAy < minBy || maxBy < minAy)
           return null;

        Vector2 diferencaPosicao = new Vector2(minAx - minBx, minAy - minBy);
        Vector2 penetracao = new Vector2(Math.abs(maxAx - minBx), Math.abs(maxAy - minBy));
        Vector2 normalColisao = new Vector2((minAx > minBx) ? 1 : -1, (minAy > minBy) ? 1 : -1);

        return new Colisao(diferencaPosicao, penetracao, normalColisao);
    }

}
