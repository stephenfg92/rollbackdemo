package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stephenfg.sre.utilidades.CriarArrayTextureRegion;

import java.util.Objects;

public class ComponenteSpritesheet implements Component {
    public String id;
    public int regiaoAtual = 0;
    public boolean espelharX = false;
    public boolean espelharY = false;
    public int largura;
    public int altura;
    public int origemX;
    public int origemY;
    public Integer numeroLinhas;
    public Integer numeroColunas;

    public ComponenteSpritesheet(String id, int larguraQuadro, int alturaQuadro, int numeroLinhas, int numeroColunas) {
        this.id = id;
        this.largura = larguraQuadro;
        this.altura = alturaQuadro;
        this.origemX = largura / 2;
        this.origemY = 0;
        this.numeroLinhas = numeroLinhas;
        this.numeroColunas = numeroColunas;
    }

    public ComponenteSpritesheet(String id, int larguraQuadro, int alturaQuadro) {
        this.id = id;
        this.largura = larguraQuadro;
        this.altura = alturaQuadro;
        this.origemX = largura / 2;
        this.origemY = 0;
    }
}
