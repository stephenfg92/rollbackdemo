package com.stephenfg.sre.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MakeTextureRegionArray {
    public static TextureRegion[] make(TextureDisposer texHandler, String texturePath, int frameHeight, int frameWidth){
        Texture texture = new Texture(texturePath);
        int texW = texture.getWidth();
        int texH = texture.getHeight();
        TextureRegion[] regions = new TextureRegion[frameHeight * frameWidth];

        for (int i = 0, h = 0; h < texH; h += frameHeight) {
            for (int w = 0; w < texW; w += frameWidth) {
                regions[i] = new TextureRegion(texture, w, h, frameWidth, frameHeight);
                w += frameWidth;
                i++;
            }
            h += frameHeight;
        }

        texHandler.addTexture(texture);

        return regions;
    }
}
