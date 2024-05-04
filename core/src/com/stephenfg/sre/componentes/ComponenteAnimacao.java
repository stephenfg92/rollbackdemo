package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;
import com.stephenfg.sre.data.Estado;
import com.stephenfg.sre.utilidades.Intervalo;

import java.util.Map;

public class ComponenteAnimacao implements Component {
    public String id;
    public int taxaDeQuadros;
    public int qtdQuadros;
    public int quadroAtual;
    public float ultimaAtualizacao;
    public int regiaoInicial;
    public int regiaoFinal;

    public ComponenteAnimacao(String id) {
        this.id = id;
        this.qtdQuadros = 0;
        this.quadroAtual = 0;
        this.ultimaAtualizacao = 0;
    }
}
