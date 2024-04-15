package com.stephenfg.sre.utilidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stephenfg.sre.data.hero.HeroData;

public class CriarArrayTextureRegion {
    public static TextureRegion[] criar(DespachanteDeTexturas texHandler, String texturePath, int frameHeight, int frameWidth, int numeroLinhas, int numeroColunas){
        Texture texture = new Texture(texturePath);
        TextureRegion[] regions = new TextureRegion[numeroColunas * numeroLinhas];
        for (int i = 0, currentRow = 0; currentRow < numeroLinhas; ++currentRow) {
            for (int currentCol = 0; currentCol < numeroColunas; ++currentCol, ++i){
                regions[i] = new TextureRegion(texture, frameWidth * currentCol, frameHeight * currentRow, frameWidth, frameHeight);
            }
        }

        texHandler.adicionarTextura(texture);

        return regions;
    }
}
