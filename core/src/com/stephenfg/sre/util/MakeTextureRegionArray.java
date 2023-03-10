package com.stephenfg.sre.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stephenfg.sre.data.hero.HeroData;

public class MakeTextureRegionArray {
    public static TextureRegion[] make(TextureDisposer texHandler, String texturePath, int frameHeight, int frameWidth){
        Texture texture = new Texture(texturePath);
        TextureRegion[] regions = new TextureRegion[HeroData.spriteSheetCols * HeroData.spriteSheetRows];
        for (int i = 0, currentRow = 0; currentRow < HeroData.spriteSheetRows; ++currentRow) {
            for (int currentCol = 0; currentCol < HeroData.spriteSheetCols; ++currentCol, ++i){
                regions[i] = new TextureRegion(texture, frameWidth * currentCol, frameHeight * currentRow, frameWidth, frameHeight);
            }
        }

        texHandler.addTexture(texture);

        return regions;
    }
}
