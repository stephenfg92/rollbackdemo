package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ComponenteAnimacao implements Component {
    public int taxaDeQuadros;
    public int qtdQuadros;
    public int quadroAtual;
    public float ultimaAtualizacao;
    public int regiaoInicial;
    public int regiaoFinal;

    public ComponenteAnimacao(int taxaDeQuadros) {
        this.qtdQuadros = 0;
        this.quadroAtual = 0;
        this.taxaDeQuadros = taxaDeQuadros;
        this.ultimaAtualizacao = 0;
    }
}
