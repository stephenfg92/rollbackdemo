package com.stephenfg.sre.sistemas;

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
import com.stephenfg.sre.componentes.ComponenteComando;
import com.stephenfg.sre.componentes.ComponenteCorpoRigido;
import com.stephenfg.sre.componentes.ComponenteTransformacao;
import com.stephenfg.sre.data.Marcador;
import com.stephenfg.sre.data.hero.HeroData;
import com.stephenfg.sre.data.AcoesDeEntrada;

public class SistemaMovimento extends EntitySystem {
    private ImmutableArray<Entity> entidades;
    private ImmutableArray<Entity> entidadesComColisor;
    private ComponentMapper<ComponenteComando> mapeadorEntrada = ComponentMapper.getFor(ComponenteComando.class);
    private ComponentMapper<ComponenteCorpoRigido> mapeadorCorpoRigido = ComponentMapper.getFor(ComponenteCorpoRigido.class);
    private ComponentMapper<ComponenteTransformacao> mapeadorTransformacao = ComponentMapper.getFor(ComponenteTransformacao.class);
    private ComponentMapper<ComponenteAABB> mapeadorAABB = ComponentMapper.getFor(ComponenteAABB.class);

    public SistemaMovimento(){

    }

    @Override
    public void addedToEngine(Engine engine){
        entidades = engine.getEntitiesFor(Family.all(ComponenteComando.class, ComponenteCorpoRigido.class, ComponenteTransformacao.class, ComponenteCorpoRigido.class).get());
        entidadesComColisor = engine.getEntitiesFor(Family.all(ComponenteTransformacao.class, ComponenteAABB.class).get());
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

        return velocidade;
    }

    private strictfp Vector2 calcularDeltaMovimento(Vector2 velocidade, Float deltaTempo){
        return new Vector2(velocidade.x * deltaTempo, velocidade.y * deltaTempo);
    }

    @Override
    public void update(float deltaTime){
        if (deltaTime > 0.016f)
            deltaTime = 0.016f;
        ComponenteComando entrada;
        ComponenteCorpoRigido corpoRigido;
        ComponenteTransformacao transformacao;
        ComponenteAABB colisor;

        for (int i = 0; i < entidades.size(); ++i){
            Entity e = entidades.get(i);
            entrada = mapeadorEntrada.get(e);
            corpoRigido = mapeadorCorpoRigido.get(e);
            transformacao = mapeadorTransformacao.get(e);
            colisor = mapeadorAABB.get(e);

            corpoRigido.velocidadeAnterior = corpoRigido.velocidade;

            Vector2 novaVelocidade = obterVelocidade(entrada.bits);
            Vector2 deltaMovimento = calcularDeltaMovimento(novaVelocidade, deltaTime);
            Vector2 novaPosicao = new Vector2(transformacao.centro.x + deltaMovimento.x, transformacao.centro.y + deltaMovimento.y);

            boolean ok = procurarColisoes(transformacao, colisor, deltaMovimento);
            colisor.colidindo = !ok;

            if (ok) {
                corpoRigido.velocidade = novaVelocidade;
                corpoRigido.deltaMovimento = deltaMovimento;
                transformacao.centro = novaPosicao;
            }
        }
    }

    private boolean procurarColisoes(ComponenteTransformacao tDinamica, ComponenteAABB cDinamico, Vector2 deltaMovimento) {
        for (int i = 0; i < entidadesComColisor.size(); i++){
            Entity e = entidadesComColisor.get(i);
            if (Marcador.possuiMarcador(e.flags, Marcador.DINAMICO))
                continue;

            ComponenteTransformacao tEstatica = mapeadorTransformacao.get(e);
            ComponenteAABB cEstatico = mapeadorAABB.get(e);

            Varredura varredura = varrerAABB(tDinamica, cDinamico, tEstatica, cEstatico, deltaMovimento);

            if (varredura.colisao != null)
                return false;
        }

        return true;
    }

    private Colisao AABBvsAABB(ComponenteTransformacao transformacaoA, ComponenteAABB colisorA, ComponenteTransformacao transformacaoB, ComponenteAABB colisorB) {
        // Cálculo da diferença de posição e a penetração em x
        float dx = transformacaoB.centro.x - transformacaoA.centro.x;
        float px = ((colisorB.extensao.x * transformacaoB.escala.x) + (colisorA.extensao.x * transformacaoA.escala.x)) - Math.abs(dx);
        if (px <= .0f) {
            return null; // Não há colisão se a penetração em x é zero ou negativa
        }

        // Cálculo da diferença de posição e a penetração em y
        float dy = transformacaoB.centro.y - transformacaoA.centro.y;
        float py = ((colisorB.extensao.y * transformacaoB.escala.y) + (colisorA.extensao.y * transformacaoB.escala.y)) - Math.abs(dy);
        if (py <= .0f) {
            return null; // Não há colisão se a penetração em y é zero ou negativa
        }

        // Criação do objeto Colisao para registrar o ponto de contato, penetração e a normal da colisão
        Colisao colisao = new Colisao();
        if (px < py) {
            float sx = Math.signum(dx);
            colisao.penetracao = new Vector2(px * sx, 0);
            colisao.normalColisao = new Vector2(sx, 0);
            colisao.pontoDeContato = new Vector2(
                    transformacaoA.centro.x + ((colisorA.extensao.x * transformacaoA.escala.x) * sx),
                    transformacaoB.centro.y);
        } else {
            float sy = Math.signum(dy);
            colisao.penetracao = new Vector2(0, py * sy);
            colisao.normalColisao = new Vector2(0, sy);
            colisao.pontoDeContato = new Vector2(
                    transformacaoB.centro.x,
                    transformacaoA.centro.y + (colisorA.extensao.y * transformacaoA.escala.y) * sy);
        }

        return colisao;
    }
    private Colisao AABBvsSegmentoDeReta(ComponenteTransformacao transformacao, ComponenteAABB colisor, SegmentoDeReta reta, Vector2 modificadorExtensaoAABB) {
        float escalaX = 1.0f / reta.delta.x;
        float escalaY = 1.0f / reta.delta.y;
        int sinalX = (int) Math.signum(escalaX);
        int sinalY = (int) Math.signum(escalaY);


        float extensaoX = colisor.extensao.x * transformacao.escala.x;
        float extensaoY = colisor.extensao.y * transformacao.escala.y;

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

        colisao.penetracao = new Vector2((
                1.0f - colisao.tempo) * -reta.delta.x,
                (1.0f - colisao.tempo) * -reta.delta.y);
        colisao.pontoDeContato = new Vector2(
                reta.origem.x + reta.delta.x * colisao.tempo,
                reta.origem.y + reta.delta.y * colisao.tempo);

        return colisao;
    }

    public Varredura varrerAABB(ComponenteTransformacao transformacaoDinamica, ComponenteAABB colisorDinamico, ComponenteTransformacao transformacaoEstatica, ComponenteAABB colisorEstatico, Vector2 deltaMovimento) {
        Varredura varredura = new Varredura();

        if (deltaMovimento.x == 0 && deltaMovimento.y == 0) {
            varredura.pontoMaximo = new Vector2(
                    transformacaoDinamica.centro.x,
                    transformacaoDinamica.centro.y
            );
            varredura.colisao = AABBvsAABB(transformacaoEstatica, colisorEstatico, transformacaoDinamica, colisorDinamico);
            varredura.tempo = varredura.colisao != null ? 0 : 1;
            return varredura;
        }

        SegmentoDeReta retaMovimento = new SegmentoDeReta();
        retaMovimento.origem = new Vector2(transformacaoDinamica.centro.x, transformacaoDinamica.centro.y);
        retaMovimento.delta = new Vector2(deltaMovimento.x, deltaMovimento.y);

        Vector2 modificadorColisorEstatico = new Vector2(
                colisorDinamico.extensao.x * transformacaoDinamica.escala.x,
                colisorDinamico.extensao.y * transformacaoDinamica.escala.y
        );

        varredura.colisao = AABBvsSegmentoDeReta(transformacaoEstatica, colisorEstatico, retaMovimento, modificadorColisorEstatico);
        if (varredura.colisao != null) {
            float epsilon = 0.00000001f; // Para evitar divisões por zero.
            varredura.tempo = Math.max(varredura.colisao.tempo - epsilon, 0);
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

}
