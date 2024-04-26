package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stephenfg.sre.data.EstadoDoPersonagem;
import com.stephenfg.sre.utilidades.Intervalo;

import java.util.Map;

public class ComponenteAnimacao implements Component {
    public int taxaDeQuadros;
    public int qtdQuadros;
    public int quadroAtual;
    public float ultimaAtualizacao;
    public int regiaoInicial;
    public int regiaoFinal;
    public Map<EstadoDoPersonagem, Intervalo> animacoes;

    public ComponenteAnimacao(Map<EstadoDoPersonagem, Intervalo> animacoes, int taxaDeQuadros) {
        this.animacoes = animacoes;
        this.qtdQuadros = 0;
        this.quadroAtual = 0;
        this.taxaDeQuadros = taxaDeQuadros;
        this.ultimaAtualizacao = 0;
    }
}
