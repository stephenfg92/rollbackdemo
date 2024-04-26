package com.stephenfg.sre.sistemas;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.stephenfg.sre.colisao.Colisao;
import com.stephenfg.sre.componentes.ComponenteColisorCaixa;
import com.stephenfg.sre.componentes.ComponenteCorpoRigido;
import com.stephenfg.sre.componentes.ComponenteTransformacao;
import com.stephenfg.sre.data.Marcador;
import com.stephenfg.sre.eventos.Assinante;
import com.stephenfg.sre.eventos.BarramentoDeEventos;
import com.stephenfg.sre.eventos.Evento;
import com.stephenfg.sre.eventos.EventoColisao;
import com.stephenfg.sre.eventos.NomeDeRetorno;
import com.stephenfg.sre.eventos.Retorno;

import java.util.ArrayList;
import java.util.List;

public class SistemaDeResolucaoDeColisoes extends EntitySystem implements Assinante{
    private BarramentoDeEventos barramento;
    private List<EventoColisao> colisoes = new ArrayList<>();
    private ComponentMapper<ComponenteCorpoRigido> mapeadorCorpoRigido = ComponentMapper.getFor(ComponenteCorpoRigido.class);
    private ComponentMapper<ComponenteTransformacao> mapeadorTransformacao = ComponentMapper.getFor(ComponenteTransformacao.class);
    private ComponentMapper<ComponenteColisorCaixa> mapeadorColisorCaixa = ComponentMapper.getFor(ComponenteColisorCaixa.class);

    public SistemaDeResolucaoDeColisoes(int prioridade, BarramentoDeEventos barramento){
        this.priority = prioridade;
        this.barramento = barramento;
    }
    @Override
    public void update(float deltaTime) {
        for (EventoColisao e : colisoes)
            resolverColisao(e);

        colisoes.clear();
    }

    private void resolverColisao(EventoColisao e) {
        Entity entidadeA = e.a;
        Entity entidadeB = e.b;
        Colisao colisao = e.colisao;


        if (eJogador(entidadeA))
            resolverColisao(entidadeA, colisao);

        if (eJogador(entidadeB))
            resolverColisao(entidadeB, colisao);
    }

    private void resolverColisao(Entity e, Colisao c) {
        ComponenteTransformacao transformacao = mapeadorTransformacao.get(e);
        ComponenteCorpoRigido cRigido = mapeadorCorpoRigido.get(e);
        ComponenteColisorCaixa colisor = mapeadorColisorCaixa.get(e);

        // Redefinindo a velocidade baseada na normal da colisão
        if (c.normalColisao.x != 0) {
            cRigido.velocidade.x = 0;
            float correcaoX = c.normalColisao.x > 0 ? -c.penetracao.x : c.penetracao.x;
            transformacao.posicao.x += correcaoX;
        }

        /*if (c.normalColisao.y != 0) {
            cRigido.velocidade.y = 0;
            float correcaoY = c.normalColisao.y > 0 ? -c.penetracao.y : c.penetracao.y;
            transformacao.posicao.y += correcaoY;
        }*/
    }

    private boolean eJogador(Entity e){
        return Marcador.possuiMarcador(e.flags, Marcador.JOGADOR);
    }

    @Override
    public Assinante assinarEvento(Class<? extends Evento> event) {
        String nomeRetorno = NomeDeRetorno.obterNome(event);
        barramento.assinarEvento(event, new Retorno(this, nomeRetorno));
        return this;
    }

    public void aoHaverColisao(EventoColisao evt){
        this.colisoes.add(evt);
    }
}
