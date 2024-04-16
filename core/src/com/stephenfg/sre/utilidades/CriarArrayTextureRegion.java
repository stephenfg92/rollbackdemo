package com.stephenfg.sre.utilidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stephenfg.sre.data.hero.HeroData;

public class CriarArrayTextureRegion {
    public static TextureRegion[] criar(DespachanteDeTexturas despachanteDeTexturas, String caminhoTextura, int larguraQuadro, int alturaQuadro, int numeroLinhas, int numeroColunas){
        Texture texture = new Texture(caminhoTextura);
        TextureRegion[] regions = new TextureRegion[numeroColunas * numeroLinhas];
        for (int i = 0, currentRow = 0; currentRow < numeroLinhas; ++currentRow) {
            for (int currentCol = 0; currentCol < numeroColunas; ++currentCol, ++i){
                regions[i] = new TextureRegion(texture, alturaQuadro * currentCol, larguraQuadro * currentRow, alturaQuadro, larguraQuadro);
            }
        }

        despachanteDeTexturas.adicionarTextura(texture);

        return regions;
    }

    public static TextureRegion[] criar(DespachanteDeTexturas despachanteDeTexturas, String caminhoTextura, int larguraQuadro, int alturaQuadro){
        Texture texture = new Texture(caminhoTextura);
        TextureRegion[] regions = new TextureRegion[1];
        regions[0] = new TextureRegion(texture, larguraQuadro, alturaQuadro);
        despachanteDeTexturas.adicionarTextura(texture);

        return regions;
    }

}
