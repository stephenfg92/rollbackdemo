package com.stephenfg.sre.recursos;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stephenfg.sre.componentes.ComponenteSpritesheet;
import com.stephenfg.sre.data.hero.HeroData;

public class CriarArrayTextureRegion {
    public static TextureRegion[] criar(Texture t, int larguraQuadro, int alturaQuadro, int numeroLinhas, int numeroColunas){
        TextureRegion[] regions = new TextureRegion[numeroColunas * numeroLinhas];
        for (int i = 0, currentRow = 0; currentRow < numeroLinhas; ++currentRow) {
            for (int currentCol = 0; currentCol < numeroColunas; ++currentCol, ++i){
                TextureRegion tr = new TextureRegion(t, larguraQuadro * currentCol, alturaQuadro * currentRow, larguraQuadro, alturaQuadro);
                regions[i] = tr;
            }
        }

        return regions;
    }

    public static TextureRegion[] criar(Texture t, int larguraQuadro, int alturaQuadro){
        TextureRegion[] regions = new TextureRegion[1];
        regions[0] = new TextureRegion(t, larguraQuadro, alturaQuadro);

        return regions;
    }
}
