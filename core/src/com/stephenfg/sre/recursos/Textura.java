package com.stephenfg.sre.recursos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stephenfg.sre.data.Estado;
import com.stephenfg.sre.utilidades.Intervalo;

import java.util.Map;

public class Textura {
    public String id;
    public Texture t;
    public TextureRegion[] regioes;
    public int larguraQuadro;
    public int alturaQuadro;
    public int numeroLinhas;
    public int numeroColunas;
    public Map<Estado, Intervalo> animacoes;
    public int taxaDeQuadros;

    public Textura(String id, Texture t, int larguraQuadro, int alturaQuadro) {
        this.id = id;
        this.t = t;
        this.larguraQuadro = larguraQuadro;
        this.alturaQuadro = alturaQuadro;
        this.regioes = CriarArrayTextureRegion.criar(t, larguraQuadro, alturaQuadro);
    }
    public Textura(String id, Texture t, int larguraQuadro, int alturaQuadro, int numeroLinhas, int numeroColunas, Map<Estado, Intervalo> animacoes, int taxaDeQuadros) {
        this.id = id;
        this.t = t;
        this.larguraQuadro = larguraQuadro;
        this.alturaQuadro = alturaQuadro;
        this.numeroLinhas = numeroLinhas;
        this.numeroColunas = numeroColunas;
        this.animacoes = animacoes;
        this.taxaDeQuadros = taxaDeQuadros;
        this.regioes = CriarArrayTextureRegion.criar(t, larguraQuadro, alturaQuadro, numeroLinhas, numeroColunas);
    }
}
