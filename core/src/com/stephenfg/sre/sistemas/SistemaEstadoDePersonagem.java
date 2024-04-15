package com.stephenfg.sre.sistemas;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.stephenfg.sre.componentes.ComponenteEstado;
import com.stephenfg.sre.componentes.ComponenteOrientacao;
import com.stephenfg.sre.componentes.ComponenteCorpoRigido;
import com.stephenfg.sre.data.EstadoDoPersonagem;
import com.stephenfg.sre.estados.ArgumentosDeTratativa;
import com.stephenfg.sre.estados.TratativaDeEstado;
import com.stephenfg.sre.estados.TratativaEstadoCorrendo;
import com.stephenfg.sre.estados.TratativaEstadoNenhum;
import com.stephenfg.sre.estados.TratativaEstadoParado;
import com.stephenfg.sre.eventos.BarramentoDeEventos;
import com.stephenfg.sre.eventos.EventoMudancaDeEstado;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


public class SistemaEstadoDePersonagem extends EntitySystem {
    private ImmutableArray<Entity> entidades;
    private ComponentMapper<ComponenteEstado> mapeadorEstado = ComponentMapper.getFor(ComponenteEstado.class);
    private ComponentMapper<ComponenteCorpoRigido> mapeadorCorpoRigido = ComponentMapper.getFor(ComponenteCorpoRigido.class);
    private ComponentMapper<ComponenteOrientacao> mapeadorOrientacao = ComponentMapper.getFor(ComponenteOrientacao.class);
    private BarramentoDeEventos barramentoDeEventos;
    private Map<EstadoDoPersonagem, TratativaDeEstado> tratativasDeEstado;
    private ArgumentosDeTratativa argsTratativa;

    public SistemaEstadoDePersonagem(BarramentoDeEventos barramentoDeEventos) {
        this.barramentoDeEventos = barramentoDeEventos;

        this.argsTratativa = new ArgumentosDeTratativa();
        argsTratativa.mapeadorEstado = this.mapeadorEstado;
        argsTratativa.mapeadorCorpoRigido = this.mapeadorCorpoRigido;
        argsTratativa.mapeadorOrientacao = this.mapeadorOrientacao;

        this.tratativasDeEstado = new HashMap<>();
        this.tratativasDeEstado.put(EstadoDoPersonagem.NENHUM, new TratativaEstadoNenhum());
        this.tratativasDeEstado.put(EstadoDoPersonagem.PARADO, new TratativaEstadoParado());
        this.tratativasDeEstado.put(EstadoDoPersonagem.CORRENDO, new TratativaEstadoCorrendo());
    }

    @Override
    public void addedToEngine(Engine engine){
        entidades = engine.getEntitiesFor(Family.all(ComponenteEstado.class).get());

    }

    @Override
    public void removedFromEngine(Engine engine){

    }


    @Override

    public void update(float deltaTime){
        ComponenteEstado estado;

        for (int i = 0; i < entidades.size(); ++i){
            Entity e = entidades.get(i);
            estado = mapeadorEstado.get(e);

            argsTratativa.entidade = e;
            argsTratativa.estado = estado;

            EventoMudancaDeEstado evento = null;

            TratativaDeEstado tratativa = tratativasDeEstado.get(estado.tipoEstado);

            if (tratativa != null) {
                evento = tratativa.tratar(argsTratativa);
            }

            if (evento != null){
                try {
                    emitirEvento(evento);
                } catch (InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchMethodException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private EventoMudancaDeEstado tratarEstadoCorrendo(Entity e, ComponenteEstado componenteEstado) {
        ComponenteCorpoRigido corpoRigido = mapeadorCorpoRigido.get(e);
        if (corpoRigido.velocidade.x == 0.0 && corpoRigido.velocidadeAnterior.x == 0.0){
            componenteEstado.tipoEstado = EstadoDoPersonagem.PARADO;
            return new EventoMudancaDeEstado(e, EstadoDoPersonagem.CORRENDO, EstadoDoPersonagem.PARADO);
        }
        return null;
    }

    private EventoMudancaDeEstado tratarEstadoParado(Entity e, ComponenteEstado componenteEstado) {
        ComponenteCorpoRigido corpoRigido = mapeadorCorpoRigido.get(e);
        if (corpoRigido.velocidadeAnterior.x == 0 && corpoRigido.velocidade.x != 0){
            componenteEstado.tipoEstado = EstadoDoPersonagem.CORRENDO;
            return new EventoMudancaDeEstado(e, EstadoDoPersonagem.PARADO, EstadoDoPersonagem.CORRENDO);
        }
        return null;
    }

    private EventoMudancaDeEstado nenhumEstado(Entity e, ComponenteEstado componenteEstado) {
        componenteEstado.tipoEstado = EstadoDoPersonagem.PARADO;
        return new EventoMudancaDeEstado(e, EstadoDoPersonagem.NENHUM, EstadoDoPersonagem.PARADO);
    }
    
    private void emitirEvento(EventoMudancaDeEstado event) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        barramentoDeEventos.emitirEvento(event);
    }
    
    
}
