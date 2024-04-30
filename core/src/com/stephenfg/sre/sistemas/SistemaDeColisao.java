package com.stephenfg.sre.sistemas;

import static com.stephenfg.sre.utilidades.StrictfpMath.strictSign;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.stephenfg.sre.colisao.Colisao;
import com.stephenfg.sre.colisao.SegmentoDeReta;
import com.stephenfg.sre.colisao.Varredura;
import com.stephenfg.sre.componentes.ComponenteAABB;
import com.stephenfg.sre.componentes.ComponenteColisorCaixa;
import com.stephenfg.sre.componentes.ComponenteCorpoRigido;
import com.stephenfg.sre.componentes.ComponenteTransformacao;
import com.stephenfg.sre.data.Marcador;
import com.stephenfg.sre.eventos.BarramentoDeEventos;
import com.stephenfg.sre.eventos.EventoColisao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SistemaDeColisao extends EntitySystem {
    private BarramentoDeEventos barramento;
    private ImmutableArray<Entity> entidades;
    private List<EventoColisao> colisoes;
    private ComponentMapper<ComponenteTransformacao> mapeadorTransformacao = ComponentMapper.getFor(ComponenteTransformacao.class);
    private ComponentMapper<ComponenteAABB> mapeadorAABB = ComponentMapper.getFor(ComponenteAABB.class);
    private ComponentMapper<ComponenteCorpoRigido> mapeadorCorpoRigido = ComponentMapper.getFor(ComponenteCorpoRigido.class);
    public SistemaDeColisao(int prioridade, BarramentoDeEventos eventos){
        this.colisoes = colisoes;
        this.priority = prioridade;
        this.barramento = eventos;
    }
    public void addedToEngine(Engine engine){
        entidades = engine.getEntitiesFor(Family.all(ComponenteTransformacao.class, ComponenteAABB.class).get());
    }

    @Override
    public void update(float deltaTime){

        ComponenteTransformacao transformacaoDinamica;
        ComponenteAABB colisorDinamico;
        ComponenteCorpoRigido corpoRigidoDinamico;

        ComponenteTransformacao transformacaoEstatica;
        ComponenteAABB colisorEstatico;
        for (int i = 0; i < entidades.size(); i++){
            Entity a = entidades.get(i);

            if (!Marcador.possuiMarcador(a.flags, Marcador.DINAMICO)) {
                continue;
            }

            transformacaoDinamica = mapeadorTransformacao.get(a);
            colisorDinamico = mapeadorAABB.get(a);
            corpoRigidoDinamico = mapeadorCorpoRigido.get(a);

            colisorDinamico.colidindo = false;

            for (int j = 0; j < entidades.size(); j++) {
                Entity b = entidades.get(j);

                if (!Marcador.possuiMarcador(b.flags, Marcador.ESTATICO))
                    continue;

                transformacaoEstatica = mapeadorTransformacao.get(b);
                colisorEstatico = mapeadorAABB.get(b);

                Varredura varredura = varrerAABB(transformacaoDinamica, colisorDinamico, transformacaoEstatica, colisorEstatico, corpoRigidoDinamico.deltaMovimento);

                colisorDinamico.colidindo = varredura.colisao != null;

                /*try {
                    if (varredura.colisao != null)
                        emitirEventoDeColisao(a, b, varredura.colisao);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }*/
            }
        }
    }

    private void emitirEventoDeColisao(Entity a, Entity b, Colisao colisao) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        EventoColisao evento = new EventoColisao(a, b, colisao);
        barramento.emitirEvento(evento);
    }

    private Colisao AABBvsAABB(ComponenteTransformacao transformacaoA, ComponenteAABB colisorA, ComponenteTransformacao transformacaoB, ComponenteAABB colisorB) {
        // Cálculo da diferença de posição e a penetração em x
        float dx = transformacaoB.centro.x - transformacaoA.centro.x;
        float px = colisorB.extensao.x + colisorA.extensao.x - Math.abs(dx);
        if (px <= 0) {
            return null; // Não há colisão se a penetração em x é zero ou negativa
        }

        // Cálculo da diferença de posição e a penetração em y
        float dy = transformacaoB.centro.y - transformacaoA.centro.y;
        float py = colisorB.extensao.y + colisorA.extensao.y - Math.abs(dy);
        if (py <= 0) {
            return null; // Não há colisão se a penetração em y é zero ou negativa
        }

        // Criação do objeto Colisao para registrar o ponto de contato, penetração e a normal da colisão
        Colisao colisao = new Colisao();
        if (px < py) {
            float sx = Math.signum(dx);
            colisao.penetracao = new Vector2(px * sx, 0);
            colisao.normalColisao = new Vector2(sx, 0);
            colisao.pontoDeContato = new Vector2(transformacaoA.centro.x + colisorA.extensao.x * sx, transformacaoB.centro.y);
        } else {
            float sy = Math.signum(dy);
            colisao.penetracao = new Vector2(0, py * sy);
            colisao.normalColisao = new Vector2(0, sy);
            colisao.pontoDeContato = new Vector2(transformacaoB.centro.x, transformacaoA.centro.y + colisorA.extensao.y * sy);
        }

        return colisao;
    }


    private Colisao AABBvsSegmentoDeReta(ComponenteTransformacao transformacao, ComponenteAABB colisor, SegmentoDeReta reta, Vector2 modificadorExtensaoAABB) {
        float escalaX = 1.0f / reta.delta.x;
        float escalaY = 1.0f / reta.delta.y;
        int sinalX = (int) Math.signum(escalaX);
        int sinalY = (int) Math.signum(escalaY);


        float extensaoX = colisor.extensao.x;
        float extensaoY = colisor.extensao.y;

        if (modificadorExtensaoAABB != null){
            extensaoX += modificadorExtensaoAABB.x;
            extensaoY += modificadorExtensaoAABB.y;
        }

        float tempoProximoX = (transformacao.centro.x - sinalX * extensaoX - reta.origem.x) * escalaX;
        float tempoProximoY = (transformacao.centro.y - sinalY * extensaoY - reta.origem.y) * escalaY;
        float tempoDistanteX = (transformacao.centro.x + sinalX * extensaoX - reta.origem.x) * escalaX;
        float tempoDistanteY = (transformacao.centro.y + sinalY * extensaoY - reta.origem.y) * escalaY;

        if (tempoProximoX > tempoDistanteY || tempoProximoY > tempoDistanteX) {
            return null;
        }

        float tempoProximo = tempoProximoX > tempoProximoY ? tempoProximoX : tempoProximoY;
        float tempoDistante = tempoDistanteX < tempoDistanteY ? tempoDistanteX : tempoDistanteY;

        if (tempoProximo >= 1.0f || tempoDistante <= 0.0f) {
            return null;
        }

        Colisao colisao = new Colisao();
        colisao.tempo = MathUtils.clamp(tempoProximo, 0.0f, 1.0f);
        if (tempoProximoX > tempoProximoY) {
            colisao.normalColisao = new Vector2(-sinalX, 0);
        } else {
            colisao.normalColisao = new Vector2(0, -sinalY);
        }

        colisao.penetracao = new Vector2((1.0f - colisao.tempo) * -reta.delta.x, (1.0f - colisao.tempo) * -reta.delta.y);
        colisao.pontoDeContato = new Vector2(
                transformacao.centro.x + reta.delta.x * colisao.tempo,
                transformacao.centro.y + reta.delta.y * colisao.tempo);

        return colisao;
    }

    public Varredura varrerAABB(ComponenteTransformacao transformacaoDinamica, ComponenteAABB colisorDinamico, ComponenteTransformacao transformacaoEstatica, ComponenteAABB colisorEstatico, Vector2 deltaMovimento) {
        Varredura varredura = new Varredura();

        if (deltaMovimento.x == 0 && deltaMovimento.y == 0) {
            varredura.colisao = AABBvsAABB(transformacaoDinamica, colisorDinamico, transformacaoEstatica, colisorEstatico);
            varredura.pontoMaximo = new Vector2(transformacaoDinamica.centro.x, transformacaoDinamica.centro.y);
            varredura.tempo = varredura.colisao != null ? 0 : 1;
            return varredura;
        }

        SegmentoDeReta retaMovimentoDinamico = new SegmentoDeReta();
        retaMovimentoDinamico.origem = new Vector2(transformacaoDinamica.centro.x, transformacaoDinamica.centro.y);
        retaMovimentoDinamico.delta = new Vector2(deltaMovimento.x, deltaMovimento.y);

        varredura.colisao = AABBvsSegmentoDeReta(transformacaoEstatica, colisorEstatico, retaMovimentoDinamico, colisorDinamico.extensao);
        if (varredura.colisao != null) {
            varredura.tempo = Math.max(varredura.colisao.tempo - 0.00000001f, 0); // EPSILON ajustado para Java
            varredura.tempo = Math.min(varredura.tempo, 1);
            varredura.pontoMaximo = new Vector2(
                    transformacaoDinamica.centro.x + deltaMovimento.x * varredura.tempo,
                    transformacaoDinamica.centro.y + deltaMovimento.y * varredura.tempo
            );

            // Ajustando posição de colisão considerando a extensão
            Vector2 direcao = new Vector2(deltaMovimento.x, deltaMovimento.y);
            direcao = direcao.nor();
            varredura.colisao.pontoDeContato.x = Math.max(
                    Math.min(
                            varredura.colisao.pontoDeContato.x + direcao.x * colisorDinamico.extensao.x,
                            transformacaoEstatica.centro.x + colisorEstatico.extensao.x
                    ),
                    transformacaoEstatica.centro.x - colisorEstatico.extensao.x
            );
            varredura.colisao.pontoDeContato.y = Math.max(
                    Math.min(
                            varredura.colisao.pontoDeContato.y + direcao.y * colisorDinamico.extensao.y,
                            transformacaoEstatica.centro.y + colisorEstatico.extensao.y
                    ),
                    transformacaoEstatica.centro.y - colisorEstatico.extensao.y
            );
        } else {
            varredura.pontoMaximo = new Vector2(
                    transformacaoDinamica.centro.x + deltaMovimento.x,
                    transformacaoDinamica.centro.y + deltaMovimento.y
            );
            varredura.tempo = 1;
        }

        return varredura;
    }

     public float clampFloat(float valor, float min, float max) {
        if (valor < min){
            return min;
        } else if (valor > max){
            return max;
        } else {
            return valor;
        }
     }

}
