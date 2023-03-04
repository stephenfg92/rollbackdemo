package com.stephenfg.sre.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class TextureDisposer {
    Array<Texture> textures = new Array<Texture>();

    public void addTexture(Texture texture){
        textures.add(texture);
    }

    public void disposeTextures(){
        for (Texture t : textures){
            t.dispose();
        }
    }
}
